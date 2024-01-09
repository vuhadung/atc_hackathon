package com.atcollabo.hackathon.classdojo.service.impl;

import com.atcollabo.hackathon.classdojo.dao.ClassDAO;
import com.atcollabo.hackathon.classdojo.entity.Class;
import com.atcollabo.hackathon.classdojo.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClassServiceImpl implements ClassService {
    private final ClassDAO classDao;

    public Long save(Class _class){
        classDao.save(_class);
        return _class.getId();
    }
    public void delete(Long id){
        classDao.delete(id);
    }
}
