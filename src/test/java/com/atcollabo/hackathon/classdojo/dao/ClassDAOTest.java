package com.atcollabo.hackathon.classdojo.dao;

import com.atcollabo.hackathon.classdojo.entity.Class;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClassDAOTest {

    @Autowired
    private ClassDAO classDAO;

    @Test
    public void setInActive() {
        Long classId = 9L;
        classDAO.setInActive(classId);

        Class _class = classDAO.findOne(classId);
        System.out.println(_class.getStatus());
    }

}