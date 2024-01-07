package com.atcollabo.hackathon.classdojo.repository;

import com.atcollabo.hackathon.classdojo.entity.*;
import com.atcollabo.hackathon.classdojo.entity.Class;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Repository
@RequiredArgsConstructor
@Transactional
public class TeacherRepository {
    private final EntityManager em;
    

    public List<Class> findAllClassesByTeacherId(Long teacherId) {

        return em.createQuery(
                "select c " +
                        "from Class c " +
                        "where c.teacher.id = :teacherId and c.status='active'",
                        Class.class
                )
                .setParameter("teacherId", teacherId)
                .getResultList();
    }

    public List<StudentClass> findAllStudentInClass(Long classId) {

        return em.createQuery(
                "SELECT sl " +
                        "FROM StudentClass sl " +
                        "WHERE sl._class.id = :id",
                        StudentClass.class
                )
                .setParameter("id", classId)
                .getResultList();
    }

    public boolean checkIfTeacherTeachesClass(Long teacherId, Long classId) {
        return em.createQuery(
                "SELECT c " +
                        "FROM Class c " +
                        "WHERE c.teacher.id = :teacherId " +
                        "AND c.id = :classId",
                        Class.class
                )
                .setParameter("teacherId", teacherId)
                .setParameter("classId", classId)
                .getResultList().size() > 0;
    }

    public int getTotalClassSessions(Long classId) {

        return em.createQuery(
                "SELECT FUNCTION('DATE', a.datetime) " +
                        "FROM Attendance a " +
                        "WHERE a._class.id = :classId " +
                        "GROUP BY FUNCTION('DATE', a.datetime)",
                        Attendance.class
                )
                .setParameter("classId", classId)
                .getResultList().size();
    }

    public Long checkAttendance(Long classId, List<Long> presentStudentIds) {
        Attendance attendance = new Attendance();
        attendance.set_class(em.find(Class.class, classId));
        attendance.setDatetime(LocalDateTime.now());
        em.persist(attendance);
        // check attendance for each student who is present in class
        for (Long studentId : presentStudentIds) {
            StudentAttendance studentAttendance = new StudentAttendance();
            studentAttendance.setAttendanceActivity(attendance);
            studentAttendance.setStudent(em.find(User.class, studentId));
            studentAttendance.setPresent(true);
            em.persist(studentAttendance);
        }

        // check attendance for each student who is absent in class
        List<StudentClass> studentClasses = em.createQuery(
                "SELECT sc " +
                        "FROM StudentClass sc " +
                        "WHERE sc._class.id = :classId",
                        StudentClass.class
                )
                .setParameter("classId", classId)
                .getResultList();
        // map each StudentClass to its student id
        List<Long> studentIds = studentClasses.stream().map(sc -> sc.getStudent().getId()).toList();
        // filter out students who are absent
        List<Long> absentStudentIds = studentIds.stream().filter(studentId -> !presentStudentIds.contains(studentId)).toList();
        // check attendance for each student who is absent in class
        for (Long studentId : absentStudentIds) {
            StudentAttendance studentAttendance = new StudentAttendance();
            studentAttendance.setAttendanceActivity(attendance);
            studentAttendance.setStudent(em.find(User.class, studentId));
            studentAttendance.setPresent(false);
            em.persist(studentAttendance);
        }
        return attendance.getId();
    }

    public List<StudentAttendance> getAttendanceRecords(Long attendanceId) {
        return em.createQuery(
                "SELECT sa " +
                        "FROM StudentAttendance sa " +
                        "WHERE sa.attendanceActivity.id = :attendanceId",
                        StudentAttendance.class
                )
                .setParameter("attendanceId", attendanceId)
                .getResultList();
    }
}
