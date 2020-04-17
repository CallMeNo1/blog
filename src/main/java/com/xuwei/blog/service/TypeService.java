package com.xuwei.blog.service;

import com.xuwei.blog.pojo.Type;

import java.util.List;

public interface TypeService {
    List<Type> getAllType();

    Type getTypeById(Long id);

    Type getTypeByName(String name);

    int saveType(Type type);

    int updateType(Type type);

    int deleteType(Long id);

    List<Type> getBlogType();  //首页右侧展示type对应的博客数量
}
