package com.atcollabo.hackathon.classdojo.dao;

import com.atcollabo.hackathon.classdojo.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
