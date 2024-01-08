package com.atcollabo.hackathon.classdojo.service.impl;

import com.atcollabo.hackathon.classdojo.entity.Class;
import com.atcollabo.hackathon.classdojo.entity.ClassStatus;
import com.atcollabo.hackathon.classdojo.entity.StudentClass;
import com.atcollabo.hackathon.classdojo.repository.TeacherRepository;
import com.atcollabo.hackathon.classdojo.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service(value = "teacherService")
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Override
    public List<Class> findAllClassesByTeacherId(Long teacherId) {
        return teacherRepository.findAllClassesByTeacherId(teacherId);
    }

    @Override
    public List<StudentClass> findAllStudentForClass(Long classId) {
        return teacherRepository.findAllStudentInClass(classId);
    }

    @Override
    public boolean checkIfTeacherTeachesClass(Long teacherId, Long classId) {
        return teacherRepository.checkIfTeacherTeachesClass(teacherId, classId);
    }

    @Override
    public int getTotalClassSessions(Long classId) {
        return teacherRepository.getTotalClassSessions(classId);
    }

    @Override
    public void checkAttendance(Long classId, List<Long> presentStudentIds) {
        teacherRepository.checkAttendance(classId, presentStudentIds);
    }

    @Override
    public void changeClassroomStatus(Long classId, String status) {
        ClassStatus classStatus = Objects.equals(status, "active") ? ClassStatus.active : ClassStatus.inactive;

        teacherRepository.changeClassroomStatus(classId, classStatus);
    }
}
