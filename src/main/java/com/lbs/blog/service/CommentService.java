package com.lbs.blog.service;

import com.lbs.blog.entity.Comment;

import java.util.List;

/**
 * @author Administrator
 * @date 2020/11/20 10:36
 * @description
 **/
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

}
