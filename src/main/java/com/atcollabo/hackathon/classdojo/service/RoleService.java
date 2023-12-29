package com.atcollabo.hackathon.classdojo.service;

import com.atcollabo.hackathon.classdojo.entity.Role;

public interface RoleService {
    Role findByName(String name);
}
