package com.atcollabo.hackathon.classdojo.controller;

import com.atcollabo.hackathon.classdojo.dto.CreateClassRequest;
import com.atcollabo.hackathon.classdojo.dto.CreateClassResponse;
import com.atcollabo.hackathon.classdojo.entity.Class;
import com.atcollabo.hackathon.classdojo.entity.ClassStatus;
import com.atcollabo.hackathon.classdojo.entity.User;
import com.atcollabo.hackathon.classdojo.service.ClassService;
import com.atcollabo.hackathon.classdojo.service.TeacherService;
import com.atcollabo.hackathon.classdojo.service.UserService;
import com.atcollabo.hackathon.classdojo.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.logging.ErrorManager;

@Controller
@RequiredArgsConstructor
public class ClassController {
    private final UserService userService;
    private final ClassService classService;
    private final TeacherService teacherService;

    // sau khi bấm submit, sẽ lấy thông tin để tạo class
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping(value = "/teachers/classes", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createClass(@Valid @RequestBody CreateClassRequest createClassRequest) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User teacher = userService.findOne(auth.getName());
        String randomCode = AppUtils.generateRandomCode();

        Class _class = new Class();
        _class.setTeacher(teacher);

        _class.setTitle(createClassRequest.getTitle());
        _class.setCode(randomCode);

        _class.setCreatedAt(LocalDateTime.now());
        _class.setStatus(ClassStatus.active);
        classService.save(_class);

        CreateClassResponse response = new CreateClassResponse();
        response.setClassId(_class.getId()).setTeacherId(_class.getTeacher().getId())
                .setTitle(_class.getTitle()).setCode(_class.getCode())
                .setCreatedAt(_class.getCreatedAt().toString())
                .setStatus(_class.getStatus().name()).setStudentCount(_class.getStudentCount());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("teachers/classes/{id}")
    public ResponseEntity<?> deleteClass(@PathVariable("id") Long classId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String teacherUsername = authentication.getName();
        // Call the service method to get the classes for the teacher
        User teacher = userService.findOne(teacherUsername);

        if (!teacherService.checkIfTeacherTeachesClass(teacher.getId(), classId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        classService.delete(classId);
        return ResponseEntity.status(HttpStatus.OK).body("Delete success");
    }
}
