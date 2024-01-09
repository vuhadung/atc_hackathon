package com.atcollabo.hackathon.classdojo.dao;

import com.atcollabo.hackathon.classdojo.entity.*;
import com.atcollabo.hackathon.classdojo.entity.Class;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class TeacherDao {
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

    public long getTotalClassSessions(Long classId) {
        return em.createQuery(
                        "SELECT count(a) " +
                                "FROM Attendance a " +
                                "WHERE a._class.id = :classId",
                        Long.class
                )
                .setParameter("classId", classId)
                .getSingleResult();
    }

    public Long checkAttendance(Long classId, List<Long> presentStudentIds) {
        Attendance attendance = new Attendance();
        attendance.set_class(em.find(Class.class, classId));
        attendance.setDatetime(LocalDateTime.now());
        em.persist(attendance);
        // check attendance for each student who is present in class
        for (Long studentId : presentStudentIds) {
            StudentAttendance studentAttendance = new StudentAttendance();
            User student = em.find(User.class, studentId);

            studentAttendance.setAttendanceActivity(attendance);
            studentAttendance.setStudent(student);
            studentAttendance.setPresent(true);

            em.persist(studentAttendance);
            StudentClass studentClass = em.createQuery("select sc from StudentClass sc where sc.student.id = :studentId and sc._class.id = :classId", StudentClass.class)
                    .setParameter("studentId", studentId)
                    .setParameter("classId", classId)
                    .getSingleResult();
            studentClass.setAttendance(studentClass.getAttendance() + 1);
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

    public void changeClassroomStatus(Long classId, ClassStatus status) {
        Class _class = em.find(Class.class, classId);
        _class.setStatus(status);
        em.merge(_class);
    }

    public Class findClassById(Long classId) {
        return em.find(Class.class, classId);
    }
}
