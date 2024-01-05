package com.atcollabo.hackathon.classdojo.controller;

import com.atcollabo.hackathon.classdojo.dto.StudentRequestDTO;
import com.atcollabo.hackathon.classdojo.dto.StudentResponseDTO;
import com.atcollabo.hackathon.classdojo.entity.Class;
import com.atcollabo.hackathon.classdojo.entity.User;
import com.atcollabo.hackathon.classdojo.service.StudentService;
import com.atcollabo.hackathon.classdojo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;

@RestController
public class StudentRestController {

    private StudentService studentService;

    private UserService userService;

    // Constructor Injection
    @Autowired
    public StudentRestController(StudentService studentService, UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/classes")
    public ResponseEntity<StudentResponseDTO> joinClass(@RequestBody StudentRequestDTO studentRequestDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User student = userService.findOne(userDetails.getUsername());
        // call the service to join the class
        Class joinedClass = studentService.joinClass(student.getId(), studentRequestDTO.getCode());

        // create a response DTO
        StudentResponseDTO studentResponseDTO = new StudentResponseDTO();
        studentResponseDTO.setClassId(joinedClass.getId());
        studentResponseDTO.setTeacherId(joinedClass.getTeacher().getId());
        studentResponseDTO.setTitle(joinedClass.getTitle());
        studentResponseDTO.setCode(joinedClass.getCode());
        studentResponseDTO.setTeacherFullName(joinedClass.getTeacher().getFullName());
        studentResponseDTO.setStatus(joinedClass.getStatus());

        // return the response DTO
        return ResponseEntity.ok(studentResponseDTO);

    }
}
