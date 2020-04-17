package com.xuwei.blog.dao;

import com.xuwei.blog.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TagDao {
    List<Tag> getAllTag();

    Tag getTagById(Long id);

    Tag getTagByName(String name); //新增标签时没有为其赋值id，所以通过名称来取这个标签

    int saveTag(Tag tag);

    int updateTag(Tag tag);

    int deleteTag(Long id);

    List<Tag> getBlogTag();  //首页展示博客标签
}
