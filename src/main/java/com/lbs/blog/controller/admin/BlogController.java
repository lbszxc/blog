package com.lbs.blog.controller.admin;

import com.lbs.blog.entity.Blog;
import com.lbs.blog.entity.User;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author Administrator
 * @date 2020/11/15 15:41
 * @description
 **/
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";

    private static final String LIST = "admin/blogs";

    private static final String REDIRECT_LIST = "redirect:/admin/blogs";


    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(
            @PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)
                    Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return LIST;
    }

    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }

    @GetMapping("/blogs/input")
    public String blogsInput(Model model){
        setTypeAndTag(model);
        model.addAttribute("blog",new Blog());
        return INPUT;
    }

    @GetMapping("/blogs/{id}/edit")
    public String blogsEdit(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;

    }

    @PostMapping("/blogs/search")
    public String blogsSearch(
            @PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)
                    Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";
    }

    @PostMapping("/blogs")
    public String blogsAdd(Blog blog, HttpSession session, RedirectAttributes attributes){

        blog.setUser((User) session.getAttribute("user"));

        blog.setType(typeService.getType(blog.getType().getId()));

        blog.setTags(tagService.listTag(blog.getTagIds()));

        Blog b;

        if (blog.getId() == null){
            b = blogService.saveBlog(blog);
        }else {
            b = blogService.updateBlog(blog.getId(),blog);
        }

        if (b == null){

            attributes.addFlashAttribute("message","操作失败");


        }else {

            attributes.addFlashAttribute("message","操作成功");

        }

        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String blogDelete(@PathVariable Long id,RedirectAttributes attributes){
        attributes.addFlashAttribute("message","删除成功");
        blogService.deleteBlog(id);
        return REDIRECT_LIST;
    }

}
