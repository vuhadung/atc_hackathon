package com.atcollabo.hackathon.classdojo.service;

import com.atcollabo.hackathon.classdojo.dao.ClassDAO;
import com.atcollabo.hackathon.classdojo.dao.StudentClassDAO;
import com.atcollabo.hackathon.classdojo.dao.StudentDao;
import com.atcollabo.hackathon.classdojo.entity.Class;
import com.atcollabo.hackathon.classdojo.entity.StudentClass;
import com.atcollabo.hackathon.classdojo.entity.User;
import com.atcollabo.hackathon.classdojo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class StudentService {
    private UserService userService;
    private StudentDao studentDao;
    private StudentClassDAO studentClassDAO;
    private ClassDAO classDAO;

    @Autowired
    public StudentService(UserServiceImpl userService, StudentDao studentDao, StudentClassDAO studentClassDAO, ClassDAO classDAO) {
        this.userService = userService;
        this.studentDao = studentDao;
        this.studentClassDAO = studentClassDAO;
        this.classDAO = classDAO;
    }

    //? Join a class by class code
    @Transactional(readOnly = false)
    public StudentClass joinClass(Long studentID, String classCode) {
        // find the class by code
        Class foundClass =  classDAO.findByCode(classCode);

        // find the student and studentId
        /*
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User student = userService.findOne(userDetails.getUsername());
        */
        User student = studentDao.findOne(studentID);

        // add the found class and student to the student_class table
        StudentClass studentClass = new StudentClass();
        studentClass.setStudent(student);
        studentClass.set_class(foundClass);

        // add new joined class to the User joinedClasses
        student.getJoinedClasses().add(studentClass);

        // return the joined class
        return studentClass;
    }

    //? List all classes joined by a student
    public List<Class> listAllClassesJoinedByStudent(Long studentID) {
        List<StudentClass> studentClasses = studentClassDAO.findByStudentId(studentID);

        // Convert StudentClasses to StudentClass
        List<Class> classes = new ArrayList<>();
        for (StudentClass st : studentClasses){
            classes.add(st.get_class());
        }

        return classes;
    }

}
