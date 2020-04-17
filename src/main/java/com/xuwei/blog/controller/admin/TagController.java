package com.xuwei.blog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuwei.blog.pojo.Tag;
import com.xuwei.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 标签管理：分页，新增、编辑、删除
 */
@RequestMapping("/admin")
@Controller
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 分页显示标签列表
     * @return
     */
    @GetMapping("/tags")
    public String tags(@RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pageNum,
                       Model model) {
        PageHelper.startPage(pageNum, 5);
        List<Tag> allTag = tagService.getAllTag(); //这个方法的结果会自动的进行分页

        //得到分页结果对象
        PageInfo<Tag> pageInfo = new PageInfo<>(allTag);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/tags";
    }

    /**
     * 跳往标签新增页面
     * @param model
     * @return
     */
    @GetMapping("/tags/input")
    public String toAddTag(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    /**
     * 跳往标签编辑页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}/input")
    public String toEditTag(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTagById(id));
        return "admin/tags-input";
    }

    /**
     * 新增标签
     * @param tag
     * @param attributes
     * @return
     */
    @PostMapping("/tags")
    public String addTag(Tag tag, RedirectAttributes attributes) {
        Tag t = tagService.getTagByName(tag.getName()); //从数据库中找到是否有和要添加标签名相同的
        if (t != null) {
            attributes.addFlashAttribute("msg", "不能添加重复的标签");
            return "redirect:/admin/tags/input";
        }else {
            attributes.addFlashAttribute("msg", "添加成功");
        }
        tagService.saveTag(tag);
        return "redirect:/admin/tags"; ////不能直接跳转到tags页面，否则不会显示tag数据(没经过tags方法)
    }

    /**
     * 更新标签
     * @param id
     * @param tag
     * @param attributes
     * @return
     */
    @PostMapping("/tags/{id}")
    public String editTag(@PathVariable Long id, Tag tag, RedirectAttributes attributes) {
        Tag t = tagService.getTagByName(tag.getName());
        if(t != null){ //判断更新后的标签名是否重复
            attributes.addFlashAttribute("msg", "不能添加重复的标签");
            return "redirect:/admin/tags/input";
        }else {
            attributes.addFlashAttribute("msg", "修改成功");
        }
        tagService.updateTag(tag); //将更新后的标签保存到数据库中
        return "redirect:/admin/tags";
    }

    /**
     * 删除标签
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/admin/tags";
    }
}
