package com.atcollabo.hackathon.classdojo.service;

import com.atcollabo.hackathon.classdojo.entity.Class;
import com.atcollabo.hackathon.classdojo.entity.StudentClass;

import java.util.List;

public interface TeacherService {

    List<Class> findAllClassesByTeacherId(Long teacherId);
    List<StudentClass> findAllStudentForClass(Long classId);
}
