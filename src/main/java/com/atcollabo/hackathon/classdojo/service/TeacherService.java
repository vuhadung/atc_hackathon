package com.atcollabo.hackathon.classdojo.service;

import com.atcollabo.hackathon.classdojo.entity.Class;

import java.util.List;

public interface TeacherService {

    List<Class> findAllClassesByTeacherId(Long teacherId);
}
