package com.atcollabo.hackathon.classdojo.service.impl;

import com.atcollabo.hackathon.classdojo.dao.RoleDao;
import com.atcollabo.hackathon.classdojo.dao.UserDao;
import com.atcollabo.hackathon.classdojo.dto.UserDto;
import com.atcollabo.hackathon.classdojo.entity.*;
import com.atcollabo.hackathon.classdojo.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    private UserDao userDao;
    private RoleDao roleDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public User save(UserDto userDto, String roleName) {
        User user = userDto.getUserFromDto();

        Role role = roleDao.findRoleByName(roleName);
        user.getRoles().add(role);

        userDao.save(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        // TODO
        return null;
    }

    @Override
    public User findOne(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }
}
