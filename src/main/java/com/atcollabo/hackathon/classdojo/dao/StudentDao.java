package com.atcollabo.hackathon.classdojo.dao;

import com.atcollabo.hackathon.classdojo.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudentDao {

    private final EntityManager entityManager;

    // save
    @Transactional
    public void save (User student){
        if(student.getId() == null){ // new student not in database we need to persist/save/insert it
            entityManager.persist(student);
        }
        else { // if the student already exists in the database we need to update it
            entityManager.merge(student);
        }

    }

    // findOne
    public User findOne(Long id){
        return entityManager.find(User.class, id); // find(entity type, primary key)
    }

    // findAll
    public List<User> findAll(){
        return entityManager.createQuery("select s from User s", User.class)
                .getResultList();
    }

    // findByName
    public List<User> findByUserName(String name){
        return entityManager.createQuery("select s from User s where s.username = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }

    // Delete
    @Transactional
    public void delete(Long id){
        User student = entityManager.find(User.class, id);
        entityManager.remove(student);
    }


}
