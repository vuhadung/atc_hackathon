package com.atcollabo.hackathon.classdojo.service;

import com.atcollabo.hackathon.classdojo.entity.Class;
import com.atcollabo.hackathon.classdojo.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class TeacherServiceTest {

    @Autowired private TeacherService teacherService;

    @Test
    void findAllClassesByTeacherId() {
        List<Class> allClassesByTeacherId = teacherService.findAllClassesByTeacherId(2L);
        int count = 2;
        assertEquals(count, allClassesByTeacherId.size());
    }
}