package com.atcollabo.hackathon.classdojo.repository;

import com.atcollabo.hackathon.classdojo.entity.Attendance;
import com.atcollabo.hackathon.classdojo.entity.Class;
import com.atcollabo.hackathon.classdojo.entity.StudentClass;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TeacherRepository {
    private final EntityManager em;

    public List<Class> findAllClassesByTeacherId(Long teacherId) {

        List<Class> resultList = em.createQuery("select c from Class c where c.teacher.id = :teacherId", Class.class)
                .setParameter("teacherId", teacherId)
                .getResultList();
        return resultList;
    }

    public List<StudentClass> findAllStudentInClass(Long classId) {

        // TODO: The teacher can only access the class which he/she is teaching

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
}
