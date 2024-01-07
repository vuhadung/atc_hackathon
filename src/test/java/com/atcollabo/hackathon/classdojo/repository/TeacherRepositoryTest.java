package com.atcollabo.hackathon.classdojo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class TeacherRepositoryTest {

    @Autowired private TeacherRepository teacherRepository;

    @Test
    void getTotalClassSessions() {
        int totalClassSessions = teacherRepository.getTotalClassSessions(5L);
        assertEquals(6, totalClassSessions);
    }

    @Test
    void findAllClassesByTeacherId() {
    }

    @Test
    void findAllStudentInClass() {
    }

    @Test
    void checkIfTeacherTeachesClass() {
    }

    @Test
    void checkAttendance() {
        teacherRepository.checkAttendance(5L, null);
    }
}