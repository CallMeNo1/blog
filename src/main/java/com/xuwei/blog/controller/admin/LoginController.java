package com.xuwei.blog.controller.admin;


import com.xuwei.blog.pojo.User;
import com.xuwei.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 用户登录
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 跳转到登录页面
     * @return
     */
    @GetMapping()
    public String loginPage(){
        return "admin/login";
    }

    /**
     * 登录校验
     * @param username
     * @param password
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username, password);
        if(user != null){
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index"; //后台主页面
        }else {
            attributes.addFlashAttribute("msg", "用户名或密码错误");
            return "redirect:/admin";
        }
    }

    /**
     * 退出博客
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
