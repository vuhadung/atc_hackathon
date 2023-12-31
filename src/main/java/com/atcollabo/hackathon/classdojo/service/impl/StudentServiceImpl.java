package com.atcollabo.hackathon.classdojo.service.impl;

import com.atcollabo.hackathon.classdojo.dao.ClassDAO;
import com.atcollabo.hackathon.classdojo.dao.StudentClassDAO;
import com.atcollabo.hackathon.classdojo.dao.StudentDao;
import com.atcollabo.hackathon.classdojo.dao.TeacherDao;
import com.atcollabo.hackathon.classdojo.entity.Class;
import com.atcollabo.hackathon.classdojo.entity.StudentClass;
import com.atcollabo.hackathon.classdojo.entity.User;
import com.atcollabo.hackathon.classdojo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class StudentServiceImpl implements com.atcollabo.hackathon.classdojo.service.StudentService {
    private UserService userService;
    private StudentDao studentDao;
    private StudentClassDAO studentClassDAO;
    private ClassDAO classDAO;

    private TeacherDao teacherDao;

    @Autowired
    public StudentServiceImpl(UserService userService, StudentDao studentDao, StudentClassDAO studentClassDAO, ClassDAO classDAO, TeacherDao teacherDao) {
        this.userService = userService;
        this.studentDao = studentDao;
        this.studentClassDAO = studentClassDAO;
        this.classDAO = classDAO;
        this.teacherDao = teacherDao;
    }
    //? find a student by id
    public User findOne(Long id) {
        return studentDao.findOne(id);
    }

    //? Join a class by class code
    @Override
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
    @Override
    public List<Class> listAllClassesJoinedByStudent(Long studentID) {
        List<StudentClass> studentClasses = studentClassDAO.findByStudentId(studentID);

        // Convert StudentClasses to StudentClass
        List<Class> classes = new ArrayList<>();
        for (StudentClass st : studentClasses){
            classes.add(st.get_class());
        }

        return classes;
    }

    //? View student profile
    public HashMap<Class, Double> getClassesAttendanceRate(Long studentID) {
        HashMap<Class, Double> attendanceRates = new HashMap<>();
        User user = studentDao.findOne(studentID);
        for(StudentClass studentClass : user.getJoinedClasses()){
            long totalClassSessions = teacherDao.getTotalClassSessionsDW(studentClass.get_class().getId());
            int totalAttendance = studentClass.getAttendance();
            double attendanceRate = (double) totalAttendance / totalClassSessions;
            if (totalClassSessions != 0){
                attendanceRates.put(studentClass.get_class(), attendanceRate);
            }else {
                attendanceRates.put(studentClass.get_class(), 1.0);
            }

        }
        return attendanceRates;
    }

}
