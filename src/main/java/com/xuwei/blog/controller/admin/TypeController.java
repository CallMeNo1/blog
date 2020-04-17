package com.xuwei.blog.controller.admin;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuwei.blog.pojo.Type;
import com.xuwei.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 分类管理
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    TypeService typeService;

    /**
     * 分页显示分类
     * @param pagenum
     * @param model
     * @return
     */
    @GetMapping("/types")
    public String types(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                        Model model){
        PageHelper.startPage(pagenum, 5);
        List<Type> allType = typeService.getAllType();
        //得到分页结果对象
        PageInfo<Type> pageInfo = new PageInfo<>(allType);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/types";
    }

    /**
     * 跳往分类新增页面
     * @param model
     * @return
     */
    @GetMapping("/types/input")
    public String toAddType(Model model){
        model.addAttribute("type", new Type());   //返回一个type对象给前端th:object
        return "admin/types-input";
    }

    /**
     * 跳往分类编辑页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}/input")
    public String toEditType(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getTypeById(id));
        return "admin/types-input";
    }

    /**
     * 分类新增
     * @param type
     * @param attributes
     * @return
     */
    @PostMapping("/types")
    public String addType(Type type, RedirectAttributes attributes){   //新增
        Type t = typeService.getTypeByName(type.getName());
        if(t != null){
            attributes.addFlashAttribute("msg", "不能添加重复的分类");
            return "redirect:/admin/types/input";
        }else {
            attributes.addFlashAttribute("msg", "添加成功");
        }
        typeService.saveType(type);
        return "redirect:/admin/types";   //不能直接跳转到types页面，否则不会显示type数据(没经过types方法)
    }

    /**
     * 分类编辑
     * @param id
     * @param type
     * @param attributes
     * @return
     */
    @PostMapping("/types/{id}")
    public String editType(@PathVariable Long id, Type type, RedirectAttributes attributes){  //修改
        Type t = typeService.getTypeByName(type.getName());
        if(t != null){
            attributes.addFlashAttribute("msg", "不能添加重复的分类");
            return "redirect:/admin/types/input";
        }else {
            attributes.addFlashAttribute("msg", "修改成功");
        }
        typeService.updateType(type);
        return "redirect:/admin/types";   //不能直接跳转到types页面，否则不会显示type数据(没经过types方法)
    }

    /**
     * 删除分类
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/admin/types";
    }
}
