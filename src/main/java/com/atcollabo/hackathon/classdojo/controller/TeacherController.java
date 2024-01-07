package com.atcollabo.hackathon.classdojo.controller;

import com.atcollabo.hackathon.classdojo.dto.CheckAttendaceDto;
import com.atcollabo.hackathon.classdojo.dto.StudentDto;
import com.atcollabo.hackathon.classdojo.dto.StudentIndexDto;
import com.atcollabo.hackathon.classdojo.dto.TeacherClassDto;
import com.atcollabo.hackathon.classdojo.entity.Class;
import com.atcollabo.hackathon.classdojo.entity.StudentClass;
import com.atcollabo.hackathon.classdojo.entity.User;
import com.atcollabo.hackathon.classdojo.service.TeacherService;
import com.atcollabo.hackathon.classdojo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    private final UserService userService;

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping(value="/teachers/classes", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping(value = "teachers/classes/{classId}/students", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentIndexDto> getStudentsInClass(@PathVariable("classId") Long classId) {
        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String teacherUsername = authentication.getName();
        // Call the service method to get the classes for the teacher
        User teacher = userService.findOne(teacherUsername);

        if (!teacherService.checkIfTeacherTeachesClass(teacher.getId(), classId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        List<StudentClass> studentList = teacherService.findAllStudentForClass(classId);
        List<StudentDto> studentDtoList = new ArrayList<>();
        int numClassSessions = teacherService.getTotalClassSessions(classId);

        for (StudentClass studentClass : studentList) {
            studentDtoList.add(new StudentDto(studentClass));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new StudentIndexDto(numClassSessions, studentDtoList));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping(value = "teachers/classes/attendance", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> checkAttendance(@RequestBody CheckAttendaceDto checkAttendaceDto) {
        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String teacherUsername = authentication.getName();
        // Call the service method to get the classes for the teacher
        User teacher = userService.findOne(teacherUsername);

        if (!teacherService.checkIfTeacherTeachesClass(teacher.getId(), checkAttendaceDto.getClassId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        teacherService.checkAttendance(checkAttendaceDto.getClassId(), checkAttendaceDto.getPresentStudentIds());

        return ResponseEntity.status(HttpStatus.OK).body("Attendance recorded");
    }

}
