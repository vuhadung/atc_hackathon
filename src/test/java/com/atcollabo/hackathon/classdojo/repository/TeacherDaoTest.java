package com.atcollabo.hackathon.classdojo.repository;

import com.atcollabo.hackathon.classdojo.dao.TeacherDao;
import com.atcollabo.hackathon.classdojo.entity.Class;
import com.atcollabo.hackathon.classdojo.entity.ClassStatus;
import com.atcollabo.hackathon.classdojo.entity.StudentAttendance;
import com.atcollabo.hackathon.classdojo.entity.StudentClass;
import com.atcollabo.hackathon.classdojo.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class TeacherDaoTest {

    @Autowired private TeacherDao teacherDao;
    @Autowired private StudentService studentService;


    @Test
    void getTotalClassSessions() {
        int totalClassSessions = teacherDao.getTotalClassSessions(5L);
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
        // Prepare the data for the test
        Long classId = 1L; // replace with the id of an existing class
        List<Long> presentStudentIds = List.of(1L, 2L); // replace with the ids of existing students
        List<Long> absentStudentIds = List.of(3L, 4L); // replace with the ids of existing students

        List<StudentClass> studentsInClass = new ArrayList<>();

        for (Long studentId = 1L; studentId < 5; studentId++) {
            StudentClass studentInClass = studentService.joinClass(studentId, "Uq3dT");
            studentsInClass.add(studentInClass);
        }


        // Call the checkAttendance method
        Long attendanceId = teacherDao.checkAttendance(classId, presentStudentIds);
        List<StudentAttendance> attendanceRecords = teacherDao.getAttendanceRecords(attendanceId);
        assertEquals(4, attendanceRecords.size(), "There should be 4 attendance records");


        // Verify that the attendance records were created correctly
        List<StudentClass> studentClasses = teacherDao.findAllStudentInClass(classId);
        assertEquals(4, studentClasses.size(), "The classroom should have 4 students");

        for (StudentAttendance attendanceRecord : attendanceRecords) {
            Long studentId = attendanceRecord.getStudent().getId();
            boolean isPresent = presentStudentIds.contains(studentId);
            boolean isAbsent = absentStudentIds.contains(studentId);
            StudentClass studentInClass = studentsInClass.get(studentId.intValue() - 1);
            assertTrue(isPresent || isAbsent, "Student should be either present or absent");
            if (isPresent) {
                assertTrue(attendanceRecord.isPresent(), "Present student should have present attendance record");
                assertEquals(1,studentInClass.getAttendance(), "Student should have 1 attendance");
            } else {
                assertFalse(attendanceRecord.isPresent(), "Absent student should have absent attendance record");
            }
        }
    }

    @Test
    void changeClassroomStatus() {
        Long classId = 1L; // replace with the id of an existing class
        ClassStatus status = ClassStatus.inactive; // replace with either "active" or "inactive"

        teacherDao.changeClassroomStatus(classId, status);
        Class _class = teacherDao.findClassById(classId);
        assertEquals(status, _class.getStatus());
    }
}