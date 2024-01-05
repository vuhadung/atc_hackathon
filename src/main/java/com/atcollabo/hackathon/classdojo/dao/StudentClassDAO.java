package com.atcollabo.hackathon.classdojo.dao;

import com.atcollabo.hackathon.classdojo.entity.StudentClass;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudentClassDAO {
    private final EntityManager entityManager;
    private final StudentDao studentDao;
    private final ClassDAO classDAO;

    // save
    @Transactional
    public void save (StudentClass studentClass){
        if(studentClass.getId() == null){ // new studentClass not in database we need to persist/save/insert it
            entityManager.persist(studentClass);
        }
        else { // if the studentClass already exists in the database we need to update it
            entityManager.merge(studentClass);
        }
    }

    // findOne
    public StudentClass findOne(Long id){
        return entityManager.find(StudentClass.class, id); // find(entity type, primary key)
    }

    // findByStudentId
    public List<StudentClass> findByStudentId(Long studentId){
        return entityManager.createQuery("select sc from StudentClass sc where sc.student = :studentId", StudentClass.class)
                .setParameter("studentId",studentDao.findOne(studentId) )
                .getResultList();
    }

    // findByClassId
    public List<StudentClass> findByClassId(Long classId){
        return entityManager.createQuery("select sc from StudentClass sc where sc._class = :classId", StudentClass.class)
                .setParameter("classId", classDAO.findOne(classId))
                .getResultList();
    }

    // Delete
    @Transactional
    public void delete(Long id){
        StudentClass studentClass = entityManager.find(StudentClass.class, id);
        entityManager.remove(studentClass);
    }
}
