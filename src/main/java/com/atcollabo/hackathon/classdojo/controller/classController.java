package com.atcollabo.hackathon.classdojo.controller;

import com.atcollabo.hackathon.classdojo.dto.ClassDTO;
import com.atcollabo.hackathon.classdojo.entity.Class;
import com.atcollabo.hackathon.classdojo.entity.ClassStatus;
import com.atcollabo.hackathon.classdojo.entity.User;
import com.atcollabo.hackathon.classdojo.service.ClassService;
import com.atcollabo.hackathon.classdojo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class classController {
  private final UserService userService;
  private final ClassService classService;


  /* -khi ta bấm nút "+" trên thanh task bar để hiện form tạo class
  * - form sẽ cần có mục "title" và "code" để teacher điền thông tin của class */
  @GetMapping("/teachers/classes")
  public String createClassForm(Model model){
    model.addAttribute("ClassForm", new ClassDTO());
    return "class/ClassForm";

  }

  // sau khi bấm submit, sẽ lấy thông tin để tạo class
  @PreAuthorize("hasRole('TEACHER')")
  @PostMapping(value = "/teachers/classes", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createClass(@Valid @RequestBody ClassDTO classDto) {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User teacher = userService.findOne(auth.getName());

    Class _class = new Class();
    _class.setTeacher(teacher);

    _class.setTitle(classDto.getTitle());
    _class.setCode(classDto.getCode());

    _class.setCreatedAt(LocalDateTime.now());
    _class.setStatus(ClassStatus.ACTIVE);
    classService.save(_class);
    return ResponseEntity.status(HttpStatus.OK).body(_class);


  }
}
