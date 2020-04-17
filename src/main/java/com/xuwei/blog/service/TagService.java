package com.xuwei.blog.service;

import com.xuwei.blog.pojo.Tag;

import java.util.List;

public interface TagService {

    List<Tag> getAllTag();

    Tag getTagById(Long id);

    Tag getTagByName(String name); //新增标签时没有为其赋值id，所以通过名称来取这个标签

    int saveTag(Tag tag);

    int updateTag(Tag tag);

    int deleteTag(Long id);

    List<Tag> getTagByString(String text);   //从字符串中获取tag集合

    List<Tag> getBlogTag();  //首页展示博客标签
}
