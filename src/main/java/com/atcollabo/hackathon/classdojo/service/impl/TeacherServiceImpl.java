package com.atcollabo.hackathon.classdojo.service.impl;

import com.atcollabo.hackathon.classdojo.entity.Class;
import com.atcollabo.hackathon.classdojo.repository.TeacherRepository;
import com.atcollabo.hackathon.classdojo.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "teacherService")
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Override
    public List<Class> findAllClassesByTeacherId(Long teacherId) {
        return teacherRepository.findAllClassesByTeacherId(teacherId);
    }
}
