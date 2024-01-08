package com.atcollabo.hackathon.classdojo.controller;

import com.atcollabo.hackathon.classdojo.dto.LoginRequest;
import com.atcollabo.hackathon.classdojo.dto.UserDto;
import com.atcollabo.hackathon.classdojo.dto.UserResponseDTO;
import com.atcollabo.hackathon.classdojo.entity.User;
import com.atcollabo.hackathon.classdojo.entity.UserInfo;
import com.atcollabo.hackathon.classdojo.security.TokenProvider;
import com.atcollabo.hackathon.classdojo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDTO> generateToken(@Valid @RequestBody LoginRequest loginUser) throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        final String token = jwtTokenUtil.generateToken(authentication);
        UserResponseDTO userResponseDTO = new UserResponseDTO(userInfo, token);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    @PostMapping(value = "/student/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> studentSignUp(@Valid @RequestBody UserDto user) {
        User entity = userService.findOne(user.getUsername());
        if (entity == null) {
            userService.save(user, "STUDENT");
            return ResponseEntity.status(HttpStatus.OK).body("Register successfully!");
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already existed!");
    }

    @PostMapping(value = "/teacher/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> teacherSignUp(@Valid @RequestBody UserDto user) {
        User entity = userService.findOne(user.getUsername());
        if (entity == null) {
            userService.save(user, "TEACHER");
            return ResponseEntity.status(HttpStatus.OK).body("Register successfully!");
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already existed!");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> ping() {
        return ResponseEntity.status(HttpStatus.OK).body("This page is for admin only!");
    }

    @GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Logout successfully!");
    }


}
