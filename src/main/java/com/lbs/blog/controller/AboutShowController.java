package com.lbs.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Administrator
 * @date 2020/11/22 14:42
 * @description
 **/
@Controller
public class AboutShowController {

    @GetMapping("/about")
    public String about(){
        return "about";
    }

}
