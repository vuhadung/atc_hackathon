package com.atcollabo.hackathon.classdojo.controller;

import com.atcollabo.hackathon.classdojo.dto.StudentRequestDTO;
import com.atcollabo.hackathon.classdojo.dto.StudentResponseDTO;
import com.atcollabo.hackathon.classdojo.entity.Class;
import com.atcollabo.hackathon.classdojo.entity.StudentClass;
import com.atcollabo.hackathon.classdojo.entity.User;
import com.atcollabo.hackathon.classdojo.service.ClassService;
import com.atcollabo.hackathon.classdojo.service.StudentService;
import com.atcollabo.hackathon.classdojo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentRestController {

    private StudentService studentService;

    private UserService userService;
    private ClassService classService;

    // Constructor Injection
    @Autowired
    public StudentRestController(StudentService studentService, UserService userService, ClassService classService) {
        this.studentService = studentService;
        this.userService = userService;
        this.classService = classService;
    }

    //? Join a class by class code
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/classes")
    public ResponseEntity<StudentResponseDTO> joinClass(@RequestBody StudentRequestDTO studentRequestDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User student = userService.findOne(userDetails.getUsername());
        // call the service to join the class
        StudentClass joinedClass = studentService.joinClass(student.getId(), studentRequestDTO.getCode());

        // create a response DTO

        StudentResponseDTO studentResponseDTO = mappedClassToDto(joinedClass.get_class());

        // return the response DTO
        return ResponseEntity.ok(studentResponseDTO);

    }

    //? Find all joined classes by student id
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/students/classes")
    public ResponseEntity<List<StudentResponseDTO>> findAllJoinedClasses(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User student = userService.findOne(userDetails.getUsername());
        // call the service to find all joined classes by student id
        List<Class> joinedClasses = studentService.listAllClassesJoinedByStudent(student.getId());

        // create a response DTO for each joined class
        List<StudentResponseDTO> studentResponseDTOs = new ArrayList<>();
        for (Class joinedClass : joinedClasses) {
            StudentResponseDTO studentResponseDTO = mappedClassToDto(joinedClass);
            studentResponseDTOs.add(studentResponseDTO);
        }

        // return the response DTO
        return ResponseEntity.ok(studentResponseDTOs) ;

    }

    private StudentResponseDTO mappedClassToDto(Class joinedClass) {
        StudentResponseDTO studentResponseDTO = new StudentResponseDTO();
        studentResponseDTO.setClassId(joinedClass.getId());
        studentResponseDTO.setTeacherId(joinedClass.getTeacher().getId());
        studentResponseDTO.setTitle(joinedClass.getTitle());
        studentResponseDTO.setCode(joinedClass.getCode());
        studentResponseDTO.setTeacherFullName(joinedClass.getTeacher().getFullName());
        studentResponseDTO.setStatus(joinedClass.getStatus());

        int studentCount = joinedClass.getStudentCount();

        joinedClass.setStudentCount(studentCount + 1);
        classService.save(joinedClass);

        studentResponseDTO.setStudentCount(joinedClass.getStudentCount());

        return studentResponseDTO;
    }
}
