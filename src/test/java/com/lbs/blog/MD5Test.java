package com.lbs.blog;


import com.lbs.blog.until.MD5Until;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Administrator
 * @date 2020/11/15 14:30
 * @description
 **/

@SpringBootTest
public class MD5Test {
    @Test
    public void test(){
        String md = MD5Until.code("123456");
        System.out.println(md);
    }
}
