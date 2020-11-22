package com.lbs.blog.service;

import com.lbs.blog.entity.User;

/**
 * @author Administrator
 * @date 2020/11/15 11:24
 * @description
 **/
public interface UserService {
    User checkUser(String username,String password);
}
