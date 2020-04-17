package com.xuwei.blog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuwei.blog.dto.BlogQuery;
import com.xuwei.blog.dto.SearchBlog;
import com.xuwei.blog.pojo.Blog;
import com.xuwei.blog.pojo.User;
import com.xuwei.blog.service.BlogService;
import com.xuwei.blog.service.TagService;
import com.xuwei.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/admin")
@Controller
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /**
     * 设置所有分类和所有标签
     * @param model
     */
    public void setTypeAndTag(Model model){
        model.addAttribute("types", typeService.getAllType());
        model.addAttribute("tags", tagService.getAllTag());
    }

    /**
     * 分页显示博客列表
     * @param pageNum
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String blogs(@RequestParam(required = false,defaultValue = "1",value = "pageNum")int pageNum, Model model){
        PageHelper.startPage(pageNum, 5);
        List<BlogQuery> allBlog = blogService.getAllBlog();
        //得到分页结果对象
        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        setTypeAndTag(model);  //查询类型和标签
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(SearchBlog searchBlog, Model model,
                         @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        //将recommend转换一下
        blogService.transformRecommend(searchBlog);
        //动态sql可以解决
        List<BlogQuery> blogBySearch = blogService.getBlogBySearch(searchBlog);
        PageHelper.startPage(pageNum, 3);
        PageInfo<BlogQuery> pageInfo = new PageInfo<>(blogBySearch);
        model.addAttribute("pageInfo", pageInfo);
        setTypeAndTag(model);
        model.addAttribute("message", "查询成功");
        return "admin/blogs";
    }


    /**
     * 去新增博客页面
     * @param model
     * @return
     */
    @GetMapping("/blogs/input")
    public String toAddBlog(Model model){
        model.addAttribute("blog", new Blog());  //返回一个blog对象给前端th:object
        setTypeAndTag(model);
        return "admin/blogs-input";
    }

    /**
     * 去编辑博客页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blogs/{id}/input")
    public String toEditBlog(@PathVariable Long id, Model model){
        Blog blog = blogService.getBlogById(id);
        blog.init();   //将tags集合转换为tagIds字符串
        model.addAttribute("blog", blog);     //返回一个blog对象给前端th:object
        setTypeAndTag(model);
        return "admin/blogs-input";
    }


    /**
     * 新增、编辑博客
     * @param blog
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("/blogs")
    public String addBlog(Blog blog, HttpSession session, RedirectAttributes attributes){
        //设置user属性
        blog.setUser((User) session.getAttribute("user"));
        //设置用户id
        blog.setUserId(blog.getUser().getId());
        //设置blog的type
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        //设置blog中typeId属性
        blog.setTypeId(blog.getType().getId());
        //给blog中的List<Tag>赋值
        blog.setTags(tagService.getTagByString(blog.getTagIds()));

        if (blog.getId() == null) {   //id为空，则为新增
            blogService.saveBlog(blog);
        } else {
            blogService.updateBlog(blog);
        }

        attributes.addFlashAttribute("message", "新增成功");
        return "redirect:/admin/blogs";
    }


    /**
     * 删除博客
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/blogs/{id}/delete")
    public String deleteBlogs(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }
}
