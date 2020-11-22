package com.lbs.blog.service.Impl;

import com.lbs.blog.dao.UserRepository;
import com.lbs.blog.entity.User;
import com.lbs.blog.service.UserService;
import com.lbs.blog.until.MD5Until;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @date 2020/11/15 11:26
 * @description
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Until.code(password));
        return user;
    }
}
