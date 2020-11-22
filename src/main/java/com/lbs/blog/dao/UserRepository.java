package com.lbs.blog.dao;

import com.lbs.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 * @date 2020/11/15 11:29
 * @description
 **/
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username,String password);

}
