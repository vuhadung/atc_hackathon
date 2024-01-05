package com.atcollabo.hackathon.classdojo.repository;

import com.atcollabo.hackathon.classdojo.entity.Class;
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

}
