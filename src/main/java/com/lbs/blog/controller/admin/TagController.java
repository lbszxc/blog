package com.lbs.blog.controller.admin;

import com.lbs.blog.entity.Tag;
import com.lbs.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author Administrator
 * @date 2020/11/16 14:15
 * @description
 **/
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;


    @GetMapping("/tags")
    public String tags(
            @PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                        Pageable pageable,
                        Model model){

        model.addAttribute("page",tagService.listTag(pageable));

        return "admin/tags";

    }

    @GetMapping("/tags/input")
    public String tagsInput(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }

    @PostMapping("/tags/add")
    public String tagsAdd(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){

        Tag tag1 = tagService.getTagByName(tag.getName());

        if (tag1 != null){

            result.rejectValue("name","nameError","不能添加重复的标签名称");

        }

        if (result.hasErrors()){
            return "admin/tags-input";
        }

        Tag t = tagService.saveTag(tag);

        if (t == null){

            attributes.addFlashAttribute("message","添加失败");


        }else {

            attributes.addFlashAttribute("message","添加成功");

        }

        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/edit/{id}")
    public String tagsEdit(
            @Valid Tag tag,
            BindingResult result,
            @PathVariable Long id,
            RedirectAttributes attributes){

        Tag tag1 = tagService.getTagByName(tag.getName());

        if (tag1 != null){

            result.rejectValue("name","nameError","不能添加重复的标签名称");

        }

        if (result.hasErrors()){
            return "admin/tags-input";
        }

        Tag t = tagService.updateTag(id,tag);

        if (t == null){

            attributes.addFlashAttribute("message","修改失败");


        }else {

            attributes.addFlashAttribute("message","修改成功");

        }

        return "redirect:/admin/tags";


    }

    @GetMapping("/tags/{id}/delete")
    public String tagsDelete(@PathVariable Long id,RedirectAttributes attributes){

        tagService.deleteTag(id);

        attributes.addFlashAttribute("message","删除成功");

        return "redirect:/admin/tags";

    }

}
