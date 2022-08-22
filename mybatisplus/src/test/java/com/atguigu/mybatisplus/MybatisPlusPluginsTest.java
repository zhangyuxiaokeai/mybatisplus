package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MybatisPlusPluginsTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void testPage(){
        //测试分页

        Page<User> userPage = new Page<User>(1,3);
        Page<User> page = userMapper.selectPage(userPage, null);
        System.out.println(page);
        System.out.println(page.getPages());
    }


    @Test
    public void testPageVo(){
        Page<User> page = new Page<>(1,3);
        Page<User> pages = userMapper.selectPageVo(page, 20);
        System.out.println(pages);
        System.out.println(pages.getPages());
         }
}
