package com.atcollabo.hackathon.classdojo.repository;

import com.atcollabo.hackathon.classdojo.dao.StudentClassDAO;
import com.atcollabo.hackathon.classdojo.dao.StudentDao;
import com.atcollabo.hackathon.classdojo.entity.Class;
import com.atcollabo.hackathon.classdojo.entity.ClassStatus;
import com.atcollabo.hackathon.classdojo.entity.StudentAttendance;
import com.atcollabo.hackathon.classdojo.entity.StudentClass;
import com.atcollabo.hackathon.classdojo.service.StudentService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class TeacherRepositoryTest {

    @Autowired private TeacherRepository teacherRepository;
    @Autowired private StudentService studentService;


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
        // Prepare the data for the test
        Long classId = 1L; // replace with the id of an existing class
        List<Long> presentStudentIds = List.of(1L, 2L); // replace with the ids of existing students
        List<Long> absentStudentIds = List.of(3L, 4L); // replace with the ids of existing students

        studentService.joinClass(1L, "Uq3dT");
        studentService.joinClass(2L, "Uq3dT");
        studentService.joinClass(3L, "Uq3dT");
        studentService.joinClass(4L, "Uq3dT");

        // Call the checkAttendance method
        Long attendanceId = teacherRepository.checkAttendance(classId, presentStudentIds);
        List<StudentAttendance> attendanceRecords = teacherRepository.getAttendanceRecords(attendanceId);
        assertEquals(4, attendanceRecords.size(), "There should be 4 attendance records");


        // Verify that the attendance records were created correctly
        List<StudentClass> studentClasses = teacherRepository.findAllStudentInClass(classId);
        assertEquals(4, studentClasses.size(), "The classroom should have 4 students");

        for (StudentAttendance attendanceRecord : attendanceRecords) {
            Long studentId = attendanceRecord.getStudent().getId();
            boolean isPresent = presentStudentIds.contains(studentId);
            boolean isAbsent = absentStudentIds.contains(studentId);

            assertTrue(isPresent || isAbsent, "Student should be either present or absent");
            if (isPresent) {
                assertTrue(attendanceRecord.isPresent(), "Present student should have present attendance record");
            } else {
                assertFalse(attendanceRecord.isPresent(), "Absent student should have absent attendance record");
            }
        }
    }
}