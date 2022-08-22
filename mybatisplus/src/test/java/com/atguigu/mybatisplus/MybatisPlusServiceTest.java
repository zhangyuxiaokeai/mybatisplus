package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.pojo.User;
import com.atguigu.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MybatisPlusServiceTest {
    @Autowired
private UserService userService;

    @Test
    public void testGetCount(){
        /**
         * 查询总记录数
         */
        long count = userService.count();
        System.out.println("总记录数："+count);
    }

    @Test
    public  void testInsertMore(){
        /*
        批量添加
         */
        List<User> list= new ArrayList<>();
        for(int i=1;i<=10;i++){
            User user=new User();
            user.setName("ybc"+i);
            user.setAge(20+i);
            list.add(user);
        }
        boolean result = userService.saveBatch(list);
        System.out.println(result);
    }
}
