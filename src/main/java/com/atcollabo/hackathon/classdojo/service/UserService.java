package com.atcollabo.hackathon.classdojo.service;

import com.atcollabo.hackathon.classdojo.dto.UserDto;
import com.atcollabo.hackathon.classdojo.entity.User;

import java.util.List;

public interface UserService {
    User save(UserDto user, String roleName);
    List<User> findAll();
    User findOne(String username);
}
