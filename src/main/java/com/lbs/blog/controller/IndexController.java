package com.lbs.blog.controller;

import com.lbs.blog.Exception.NotFountException;
import com.lbs.blog.entity.vo.BlogQuery;
import com.lbs.blog.service.BlogService;
import com.lbs.blog.service.TagService;
import com.lbs.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author Administrator
 * @date 2020/11/13 14:53
 * @description
 **/
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping(value = "/")
    public String index(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                    Pageable pageable,Model model){

        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(8));
        model.addAttribute("recommendBlogs",blogService.listBlogTop(5));

        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                 @RequestParam String query, Pageable pageable, Model model){

        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";

    }

    @GetMapping(value = "/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/footer/newBlog")
    public String newBlogs(Model model){
        model.addAttribute("newBlogs",blogService.listBlogTop(3));
        return "fragment/fragments :: newBlogList";
    }

}
