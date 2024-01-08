package com.atcollabo.hackathon.classdojo.service;

import com.atcollabo.hackathon.classdojo.entity.Class;
import com.atcollabo.hackathon.classdojo.entity.StudentClass;

import java.util.List;

public interface TeacherService {

    List<Class> findAllClassesByTeacherId(Long teacherId);
    List<StudentClass> findAllStudentForClass(Long classId);

    boolean checkIfTeacherTeachesClass(Long teacherId, Long classId);

    int getTotalClassSessions(Long classId);
    void checkAttendance(Long classId, List<Long> presentStudentIds);
    void changeClassroomStatus(Long classId, String status);
}
