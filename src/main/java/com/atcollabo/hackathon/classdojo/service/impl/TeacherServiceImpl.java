package com.atcollabo.hackathon.classdojo.service.impl;

import com.atcollabo.hackathon.classdojo.entity.Class;
import com.atcollabo.hackathon.classdojo.entity.ClassStatus;
import com.atcollabo.hackathon.classdojo.entity.StudentClass;
import com.atcollabo.hackathon.classdojo.dao.TeacherDao;
import com.atcollabo.hackathon.classdojo.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service(value = "teacherService")
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherDao teacherDao;

    @Override
    public List<Class> findAllClassesByTeacherId(Long teacherId) {
        return teacherDao.findAllClassesByTeacherId(teacherId);
    }

    @Override
    public List<StudentClass> findAllStudentForClass(Long classId) {
        return teacherDao.findAllStudentInClass(classId);
    }

    @Override
    public boolean checkIfTeacherTeachesClass(Long teacherId, Long classId) {
        return teacherDao.checkIfTeacherTeachesClass(teacherId, classId);
    }

    @Override
    public int getTotalClassSessions(Long classId) {
        return (int)teacherDao.getTotalClassSessions(classId);
    }

    @Override
    public void checkAttendance(Long classId, List<Long> presentStudentIds) {
        teacherDao.checkAttendance(classId, presentStudentIds);
    }

    @Override
    public void changeClassroomStatus(Long classId, String status) {
        ClassStatus classStatus = Objects.equals(status, "active") ? ClassStatus.active : ClassStatus.inactive;

        teacherDao.changeClassroomStatus(classId, classStatus);
    }
}
