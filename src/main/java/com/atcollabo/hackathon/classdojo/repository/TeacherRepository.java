package com.atcollabo.hackathon.classdojo.repository;

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

    public List<StudentClass> findAllStudentInClass(Long class_id) {
        return em.createQuery("SELECT sl FROM StudentClass sl WHERE sl._class.id = :id AND sl.student.id", StudentClass.class)
                .setParameter("id", class_id)
                .getResultList();
    }

}
