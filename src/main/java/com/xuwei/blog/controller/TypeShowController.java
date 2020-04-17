package com.xuwei.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuwei.blog.pojo.Blog;
import com.xuwei.blog.pojo.Type;
import com.xuwei.blog.service.BlogService;
import com.xuwei.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    /**
     * 分类页出来
     * @param id 要显示的该分类的id
     * @param pagenum
     * @param model
     * @return
     */
    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id,
                        @RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                        Model model){
        PageHelper.startPage(pagenum, 5);  //开启分页
        List<Type> types = typeService.getBlogType(); //获取所有分类
        //-1从导航点过来的
        if (id == -1){
            id = types.get(0).getId();
        }
        List<Blog> blogs = blogService.getByTypeId(id); //根据分类id查询相关所有博客
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("types", types);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("activeTypeId", id);

        return "types";
    }
}
