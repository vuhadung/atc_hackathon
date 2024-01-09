package com.atcollabo.hackathon.classdojo.service;

import com.atcollabo.hackathon.classdojo.entity.Class;
import com.atcollabo.hackathon.classdojo.entity.StudentClass;
import com.atcollabo.hackathon.classdojo.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

public interface StudentService {
    //? Join a class by class code
    @Transactional(readOnly = false)
    StudentClass joinClass(Long studentID, String classCode);

    User findOne(Long id);
    //? List all classes joined by a student
    List<Class> listAllClassesJoinedByStudent(Long studentID);
    HashMap<Class, Double> getClassesAttendanceRate(Long studentID);
}
