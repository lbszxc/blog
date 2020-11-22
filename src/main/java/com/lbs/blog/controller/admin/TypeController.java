package com.lbs.blog.controller.admin;

import com.lbs.blog.entity.Type;
import com.lbs.blog.service.TypeService;
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
 * @date 2020/11/16 9:09
 * @description
 **/
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(
            @PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                    Pageable pageable,
                    Model model){

        model.addAttribute("page",typeService.listType(pageable));

        return "admin/types";
    }

    @GetMapping("/types/input")
    public String typesInput(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

    @PostMapping("/types/add")
    public String typesAdd(@Valid Type type, BindingResult result, RedirectAttributes attributes){

        Type type1 = typeService.getTypeByName(type.getName());

        if (type1 != null){

            result.rejectValue("name","nameError","不能添加重复的分类名称");

        }

        if (result.hasErrors()){
            return "admin/types-input";
        }

        Type t = typeService.saveType(type);

        if (t == null){

            attributes.addFlashAttribute("message","添加失败");


        }else {

            attributes.addFlashAttribute("message","添加成功");

        }

        return "redirect:/admin/types";
    }

    @PostMapping("/types/edit/{id}")
    public String typesEdit(
            @Valid Type type,
            BindingResult result,
            @PathVariable Long id,
            RedirectAttributes attributes){

        Type type1 = typeService.getTypeByName(type.getName());

        if (type1 != null){

            result.rejectValue("name","nameError","不能添加重复的分类名称");

        }

        if (result.hasErrors()){
            return "admin/types-input";
        }

        Type t = typeService.updateType(id,type);

        if (t == null){

            attributes.addFlashAttribute("message","修改失败");


        }else {

            attributes.addFlashAttribute("message","修改成功");

        }

        return "redirect:/admin/types";


    }

    @GetMapping("/types/{id}/delete")
    public String typesDelete(@PathVariable Long id,RedirectAttributes attributes){

        typeService.deleteType(id);

        attributes.addFlashAttribute("message","删除成功");

        return "redirect:/admin/types";

    }

}
