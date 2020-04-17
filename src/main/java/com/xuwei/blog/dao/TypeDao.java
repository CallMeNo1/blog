package com.xuwei.blog.dao;


import com.xuwei.blog.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TypeDao {

    List<Type> getAllType();

    Type getTypeById(Long id);

    Type getTypeByName(String name);

    int saveType(Type type);

    int updateType(Type type);

    int deleteType(Long id);

    List<Type> getBlogType();  //首页右侧展示type对应的博客数量

}
