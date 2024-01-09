package com.atcollabo.hackathon.classdojo.dao;

import com.atcollabo.hackathon.classdojo.entity.Class;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClassDAO {
    private final EntityManager entityManager;

    // save
    @Transactional
    public void save (Class _class){
        if(_class.getId() == null){ // new class not in database we need to persist/save/insert it
            entityManager.persist(_class);
        }
        else { // if the class already exists in the database we need to update it
            entityManager.merge(_class);
        }
    }


    // findOne
    public Class findOne(Long id){
        return entityManager.find(Class.class, id); // find(entity type, primary key)
    }


    // findAll
    public List<Class> findAll(){
        return entityManager.createQuery("select c from Class c", Class.class)
                .getResultList();
    }


    // findByCode
    public Class findByCode(String code){
        return entityManager.createQuery("select c from Class c where c.code = :code", Class.class)
                .setParameter("code", code)
                .getSingleResult();
    }


    // Delete
    @Transactional
    public void delete(Long id){
        Class _class = entityManager.find(Class.class, id);
        entityManager.remove(_class);
    }

}
