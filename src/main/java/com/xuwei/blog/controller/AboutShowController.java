package com.xuwei.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 关于我页面展示
 */
@Controller
public class AboutShowController {

    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
