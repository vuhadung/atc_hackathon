package com.atcollabo.hackathon.classdojo.service;

import com.atcollabo.hackathon.classdojo.dao.ClassDAO;
import com.atcollabo.hackathon.classdojo.dao.StudentClassDAO;
import com.atcollabo.hackathon.classdojo.dao.StudentDao;
import com.atcollabo.hackathon.classdojo.entity.Class;
import com.atcollabo.hackathon.classdojo.entity.StudentClass;
import com.atcollabo.hackathon.classdojo.entity.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StudentServiceTest {
    private StudentService studentService;
    private StudentDao studentDao;
    private ClassDAO classDAO;
    private StudentClassDAO studentClassDAO;
    // Constructor Injection
    @Autowired
    public StudentServiceTest(StudentService studentService, StudentDao studentDao, ClassDAO classDAO, StudentClassDAO studentClassDAO) {
        this.studentService = studentService;
        this.studentDao = studentDao;
        this.classDAO = classDAO;
        this.studentClassDAO = studentClassDAO;
    }

    @Test
    void joinClass() {
        // given
        User student = new User();
        student.setFullName("John Doe");
        student.setUsername("johndoe");
        studentDao.save(student);

        Class _class = new Class();
        _class.setTitle("Math");
        _class.setCode("MATH101");
        _class.setTeacher(null);
        classDAO.save(_class);

        // when
        StudentClass joinedClass = studentService.joinClass(student.getId(), _class.getCode());

        // then
        Assertions.assertEquals(student.getId(), joinedClass.getStudent().getId());
        Assertions.assertEquals(_class.getId(), joinedClass.get_class().getId());

    }

    @Test
    void listAllClassesJoinedByStudent() {
        // given
        User student = new User();
        student.setFullName("John Doe");
        student.setUsername("johndoe");
        studentDao.save(student);

        Class _class = new Class();
        _class.setTitle("Math");
        _class.setCode("MATH101");
        _class.setTeacher(null);
        classDAO.save(_class);

        Class _class2 = new Class();
        _class2.setTitle("English");
        _class2.setCode("ENG101");
        _class2.setTeacher(null);
        classDAO.save(_class2);
        // join the 2 classes
        studentService.joinClass(student.getId(), _class.getCode());
        studentService.joinClass(student.getId(), _class2.getCode());

        // when
        List<Class> joinedClasses = studentService.listAllClassesJoinedByStudent(student.getId());

        // then
        Assertions.assertEquals(2, joinedClasses.size());
    }
}