package com.lbs.blog.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Administrator
 * @date 2020/11/13 15:31
 * @description
 **/
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFountException extends RuntimeException {

    public NotFountException() {
    }

    public NotFountException(String massage) {
        super(massage);
    }

    public NotFountException(String massage, Throwable throwable) {
        super(massage, throwable);
    }
}
