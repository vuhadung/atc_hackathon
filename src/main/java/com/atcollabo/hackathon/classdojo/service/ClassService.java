package com.atcollabo.hackathon.classdojo.service;

import com.atcollabo.hackathon.classdojo.dao.ClassDAO;
import com.atcollabo.hackathon.classdojo.entity.Class;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface ClassService {
    public Long save(Class _class);
}
