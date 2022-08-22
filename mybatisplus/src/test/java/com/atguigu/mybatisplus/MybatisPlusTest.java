package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MybatisPlusTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList(){
        //通过条件构造器查询一个list集合，若没有条件，则可以设置null为参数
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    public void testInsert(){
     //   新增数据
        User user = new User();
        user.setName("sad");
        user.setAge(21);
        user.setEmail("1q23@qq.com");
        int result = userMapper.insert(user);
        System.out.println("result:"+result);
        System.out.println("id"+user.getId());
    }

    @Test
    public void delete(){
        //通过id删除用户信息
//        int result = userMapper.deleteById(1561171018766237697L);
//        System.out.println("result" + result);


        /*
        根据map结合中的所设置的条件伸出用户信息
        DELETE FROM user WHERE name = ? AND age = ?
         */
//        Map<String, Object> map = new HashMap<>();
//        map.put("name","张三");
//        map.put("age","12");
//        int result = userMapper.deleteByMap(map);
//        System.out.println("result" + result);


        /*
        通过多个id实现批量删除
        DELETE FROM user WHERE id IN ( ? , ? , ? )
         */
        List<Long> list = Arrays.asList(4L, 5L);
        int result = userMapper.deleteBatchIds(list);
        System.out.println("result" + result);
    }

    @Test
    public void testUpdate(){
        /**
         *  UPDATE user SET email=? WHERE id=?
         *  通过id跟新数据
         */
        User user = new User();
        user.setId(4L);
        user.setEmail("lisiemail@qq.com");
        int result = userMapper.updateById(user);
        User user1 = userMapper.selectById(4);
        System.out.println("result:"+result);
        System.out.println("user1"+user1.toString());
    }

    @Test
    public void testSelect(){
        /**
         * 通过id查询用户信息
         */
//        User user = userMapper.selectById(1L);
//        System.out.println(user);


        /*
        通过多个id查询
         SELECT id,name,age,email FROM user WHERE id IN ( ? , ? , ? )
         */
//        List<Long> list = Arrays.asList(5L, 6L, 7L);
//        List<User> users = userMapper.selectBatchIds(list);
//        users.forEach(System.out::println);


        /*
        根据map集合中的条件来查找数据
        SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
         */
//    Map<String,Object>  map=new HashMap<>();
//    map.put("name","jery");
//    map.put("age",22);
//        List<User> users = userMapper.selectByMap(map);
//        users.forEach(System.out::println);


        /*
        SELECT id,name,age,email FROM user查询全部数据
         */
//        List<User> users = userMapper.selectList(null);
//        users.forEach(System.out::println);

        /**
         * 自定义mapper接口方法
         */
        Map<String, Object> map = userMapper.selectMapById(7L);
        System.out.println(map);

    }


}
