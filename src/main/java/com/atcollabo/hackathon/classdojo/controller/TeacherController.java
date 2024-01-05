package com.atcollabo.hackathon.classdojo.controller;

import com.atcollabo.hackathon.classdojo.dto.TeacherClassDto;
import com.atcollabo.hackathon.classdojo.entity.Class;
import com.atcollabo.hackathon.classdojo.entity.StudentClass;
import com.atcollabo.hackathon.classdojo.entity.User;
import com.atcollabo.hackathon.classdojo.service.TeacherService;
import com.atcollabo.hackathon.classdojo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    private final UserService userService;

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping(value="/teachers/classes")
    public ResponseEntity<List<TeacherClassDto>> getClassesForTeacher(@RequestParam(defaultValue = "10") int limit,
                                                  @RequestParam(defaultValue = "0") int offset){
        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String teacherUsername = authentication.getName();
        // Call the service method to get the classes for the teacher

        User teacher = userService.findOne(teacherUsername);
        List<Class> allClassesByTeacherId = teacherService.findAllClassesByTeacherId(teacher.getId());
        List<TeacherClassDto> teacherClassDtos = new ArrayList<>();

        for (Class c : allClassesByTeacherId) {
            teacherClassDtos.add(new TeacherClassDto(c));
        }

        return ResponseEntity.status(HttpStatus.OK).body(teacherClassDtos);
    }

    @GetMapping(value = "teacher/classes/{classId}/students")
    public ResponseEntity<List<StudentClass>> getStudentsInClass(@PathVariable("classId") Long classId) {
        List<StudentClass> studentClassList = teacherService.findAllStudentForClass(classId);
        return ResponseEntity.status(HttpStatus.OK).body(studentClassList);
    }
}
