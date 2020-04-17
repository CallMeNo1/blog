package com.xuwei.blog.service;

import com.xuwei.blog.dto.BlogQuery;
import com.xuwei.blog.dto.SearchBlog;
import com.xuwei.blog.pojo.Blog;
import com.xuwei.blog.pojo.BlogAndTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BlogService {
    //==================================后台管理=================================
    List<BlogQuery> getAllBlog();; //后台展示所有博客,使用dto类

    Blog getBlogById(Long id);  //后台展示博客

    List<Blog> getByTypeId(Long typeId);  //根据类型id获取博客

    List<Blog> getByTagId(Long tagId);  //根据标签id获取博客


    List<BlogQuery> getBlogBySearch(SearchBlog searchBlog);//后台根据标题、分类、推荐搜索博客，定义该类存储这几个条件

    //修改recommend,因为recommend从前台接收只能接收字符串，但数据库中的Integer类型，所以转一下
    void transformRecommend(SearchBlog searchBlog);

    int saveBlog(Blog blog);

    int saveBlogAndTag(BlogAndTag blogAndTag);

    int updateBlog(Blog blog);

    int deleteBlog(Long id);

    //==================================前台显示=================================
    List<Blog> getIndexBlog();  //主页博客展示

    List<Blog> getAllRecommendBlog();  //推荐博客展示

    List<Blog> getSearchBlog(String query);  //全局搜索博客，通过标题或者内容

    Blog getDetailedBlog(@Param("id") Long id);  //博客详情

    //==========================归档页显示=================================
    Map<String,List<Blog>> archiveBlog();  //归档博客
    int countBlog();  //查询博客条数
}
