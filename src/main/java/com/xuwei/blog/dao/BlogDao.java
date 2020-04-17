package com.xuwei.blog.dao;

import com.xuwei.blog.dto.BlogQuery;
import com.xuwei.blog.dto.SearchBlog;
import com.xuwei.blog.pojo.Blog;
import com.xuwei.blog.pojo.BlogAndTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BlogDao {

    //===================================后台管理===================================
    List<BlogQuery> getAllBlogQuery(); //后台显示所有博客，通过dto类显示

    Blog getBlogById(Long id);  //后台展示博客

    List<Blog> getByTypeId(Long typeId);  //根据分类获取博客

    List<Blog> getByTagId(Long tagId);  //根据标签获取博客

    List<BlogQuery> searchByTitleOrTypeOrRecommend(SearchBlog searchBlog);

    int saveBlog(Blog blog);

    int saveBlogAndTag(BlogAndTag blogAndTag);

    int updateBlog(Blog blog);

    int deleteBlog(Long id);

    //==================================前台显示=================================
    List<Blog> getIndexBlog();  //主页博客展示

    List<Blog> getAllRecommendBlog();  //推荐博客展示

    List<Blog> getSearchBlog(String query);  //全局搜索博客，通过标题或者内容

    Blog getDetailedBlog(@Param("id") Long id);  //博客详情

    //===============================前台:归档页=======================
    List<String> findGroupYear();  //查询所有年份，返回一个集合
    List<Blog> findByYear(@Param("year") String year);  //按年份查询博客
}