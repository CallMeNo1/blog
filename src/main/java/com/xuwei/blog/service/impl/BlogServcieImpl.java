package com.xuwei.blog.service.impl;

import com.xuwei.blog.dao.BlogDao;
import com.xuwei.blog.dto.BlogQuery;
import com.xuwei.blog.dto.SearchBlog;
import com.xuwei.blog.exception.NotFoundException;
import com.xuwei.blog.pojo.Blog;
import com.xuwei.blog.pojo.BlogAndTag;
import com.xuwei.blog.pojo.Tag;
import com.xuwei.blog.service.BlogService;
import com.xuwei.blog.util.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BlogServcieImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    //=======================================后台管理======================================

    @Override
    public List<BlogQuery> getAllBlog() {
        List<BlogQuery> allBlogQuery = blogDao.getAllBlogQuery();
        return allBlogQuery;
    }

    @Override
    public Blog getBlogById(Long id) {
        return blogDao.getBlogById(id);
    }

    @Override
    public List<Blog> getByTypeId(Long typeId) {
        return blogDao.getByTypeId(typeId);
    }

    @Override
    public List<Blog> getByTagId(Long tagId) {
        return blogDao.getByTagId(tagId);
    }


    @Override
    public List<BlogQuery> getBlogBySearch(SearchBlog searchBlog) {
        return blogDao.searchByTitleOrTypeOrRecommend(searchBlog);
    }

    /**
     * 如果前端传入的这个布尔值为空，就设置为整型1
     * @param searchBlog
     */
    @Override
    public void transformRecommend(SearchBlog searchBlog) {
        if (!"".equals(searchBlog.getRecommend()) && null != searchBlog.getRecommend()) {
            searchBlog.setRecommend2(1);
        }
    }

    @Override
    public int saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        //保存博客
        blogDao.saveBlog(blog);
        //保存博客后才能获取自增的id
        Long id = blog.getId();
        //将标签的数据存到t_blogs_tag表中
        List<Tag> tags = blog.getTags();
        BlogAndTag blogAndTag = null;
        for (Tag tag : tags) {
            //新增时无法获取自增的id,在mybatis里修改
            blogAndTag = new BlogAndTag(tag.getId(), id);
            blogDao.saveBlogAndTag(blogAndTag);
        }
        return 1;
    }

    @Override
    public int saveBlogAndTag(BlogAndTag blogAndTag) {
        return blogDao.saveBlogAndTag(blogAndTag);
    }

    @Override
    public int updateBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        //将标签的数据存到t_blogs_tag表中
        List<Tag> tags = blog.getTags();
        BlogAndTag blogAndTag = null;
        for (Tag tag : tags) {
            blogAndTag = new BlogAndTag(tag.getId(), blog.getId());
            blogDao.saveBlogAndTag(blogAndTag);
        }
        return blogDao.updateBlog(blog);
    }

    @Override
    public int deleteBlog(Long id) {
        return blogDao.deleteBlog(id);
    }

    //==================================前台显示=================================

    @Override
    public List<Blog> getIndexBlog() {
        return blogDao.getIndexBlog();
    }

    @Override
    public List<Blog> getAllRecommendBlog() {
        return blogDao.getAllRecommendBlog();
    }

    @Override
    public List<Blog> getSearchBlog(String query) {
        return blogDao.getSearchBlog(query);
    }

    @Override
    public Blog getDetailedBlog(Long id) {
        Blog blog = blogDao.getDetailedBlog(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        String content = blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));  //将Markdown格式转换成html
        return blog;
    }

    //==========================归档页显示=================================

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogDao.findGroupYear(); //获取年份列表
        Set<String> set = new HashSet<>(years);  //set去掉重复的年份
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : set) {
            map.put(year, blogDao.findByYear(year)); //map中存放的是每一年所对应的博客列表
        }
        return map;
    }

    @Override
    public int countBlog() {
        return blogDao.getAllBlogQuery().size(); //返回后台查询出来的所有博客的总数
    }
}
