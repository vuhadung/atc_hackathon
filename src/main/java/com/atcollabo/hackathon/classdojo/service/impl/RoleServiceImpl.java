//package com.atcollabo.hackathon.classdojo.service.impl;
//
//import com.atcollabo.hackathon.classdojo.dao.RoleDao;
//import com.atcollabo.hackathon.classdojo.entity.Role;
//import com.atcollabo.hackathon.classdojo.service.RoleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service(value="roleService")
//public class RoleServiceImpl implements RoleService {
//    private RoleDao roleDao;
//
//    @Autowired
//    public RoleServiceImpl(RoleDao roleDao) {
//        this.roleDao = roleDao;
//    }
//
//    @Override
//    public Role findByName(String name) {
//        Role role = roleDao.findRoleByName(name);
//        return role;
//    }
//}
