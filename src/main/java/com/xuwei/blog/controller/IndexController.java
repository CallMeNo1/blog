package com.xuwei.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuwei.blog.pojo.Blog;
import com.xuwei.blog.pojo.Tag;
import com.xuwei.blog.pojo.Type;
import com.xuwei.blog.service.BlogService;
import com.xuwei.blog.service.TagService;
import com.xuwei.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 首页：博客列表，查找博客，top标签，top分类、推荐博客
 */
@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /**
     * 跳转到首页
     * @return
     */
    @GetMapping("/")
    public String toIndex(@RequestParam(required = false, defaultValue = "1", value = "pageNum")Integer pageNum,
                          Model model) {
        PageHelper.startPage(pageNum, 5);
        List<Blog> allBlog =  blogService.getIndexBlog(); //获取要显示的博客列表
        List<Type> allType = typeService.getBlogType(); ////获取博客的类型(联表查询)
        List<Tag> allTag = tagService.getBlogTag();  //获取博客的标签(联表查询)
        List<Blog> recommendBlog =blogService.getAllRecommendBlog();  //获取推荐博客

        //得到分页结果对象
        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("tags", allTag);
        model.addAttribute("types", allType);
        model.addAttribute("recommendBlogs", recommendBlog);
        return "index";
    }

    /**
     * 搜索博客
     * @param pagenum
     * @param query
     * @param model
     * @return
     */
    @PostMapping("/search")
    public String search(@RequestParam(required = false,defaultValue = "1",value = "pageNum")int pageNum,
                         @RequestParam String query,
                         Model model) {
        PageHelper.startPage(pageNum, 5);
        List<Blog> searchBlog = blogService.getSearchBlog(query); //根据条件搜索博客
        PageInfo pageInfo = new PageInfo(searchBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("query", query);
        return "search";
    }

    /**
     * 跳转到博客详情页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/{id}")
    public String toLogin(@PathVariable Long id, Model model) {
        Blog blog = blogService.getDetailedBlog(id);
        model.addAttribute("blog", blog);
        return "blog";
    }
}
