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
import java.security.SecureRandom;

@Controller
@RequiredArgsConstructor
public class classController {
  private final UserService userService;
  private final ClassService classService;






    // Ký tự có thể sử dụng để tạo mã code
    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    // Độ dài của mã code
    private static final int CODE_LENGTH = 8;

    public static String generateRandomCode() {
      SecureRandom random = new SecureRandom();
      StringBuilder sb = new StringBuilder(CODE_LENGTH);

      for (int i = 0; i < CODE_LENGTH; i++) {
        int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
        char randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);
        sb.append(randomChar);
      }

      return sb.toString();
    }








  // sau khi bấm submit, sẽ lấy thông tin để tạo class
  @PreAuthorize("hasRole('TEACHER')")
  @PostMapping(value = "/teachers/classes", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createClass(@Valid @RequestBody ClassDTO classDto) {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User teacher = userService.findOne(auth.getName());
    String randomCode = generateRandomCode();

    Class _class = new Class();
    _class.setTeacher(teacher);

    _class.setTitle(classDto.getTitle());
   _class.setCode(randomCode);

    _class.setCreatedAt(LocalDateTime.now());
    _class.setStatus(ClassStatus.active);
    classService.save(_class);
    return ResponseEntity.status(HttpStatus.OK).body(_class);


  }
}
