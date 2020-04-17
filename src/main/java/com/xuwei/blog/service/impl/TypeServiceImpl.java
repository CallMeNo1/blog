package com.xuwei.blog.service.impl;

import com.xuwei.blog.dao.TypeDao;
import com.xuwei.blog.pojo.Type;
import com.xuwei.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;

    @Override
    public List<Type> getAllType() {
        return typeDao.getAllType();
    }

    @Override
    public Type getTypeById(Long id) {
        return typeDao.getTypeById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeDao.getTypeByName(name);
    }

    @Override
    public int saveType(Type type) {
        return typeDao.saveType(type);
    }

    @Override
    public int updateType(Type type) {
        return typeDao.updateType(type);
    }

    @Override
    public int deleteType(Long id) {
        return typeDao.deleteType(id);
    }

    @Override
    public List<Type> getBlogType() {
        return typeDao.getBlogType();
    }
}
