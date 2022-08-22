package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.ls.LSException;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class MybatisPlusWrapperTest {
@Autowired
    private UserMapper userMapper;

@Test
    public void test01(){
    //查询用户名包含a，年龄在20到30之间，邮箱信息不为null的用户信息
    //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (user_name LIKE ? AND age BETWEEN ? AND ? AND email IS NOT NULL)
    QueryWrapper<User> queryWrapper = new QueryWrapper();
    queryWrapper.like("user_name","a")
                    .between("age",20,30)
                            .isNotNull("email");
    List<User> users = userMapper.selectList(queryWrapper);
    users.forEach(System.out::println);
}

@Test
    public void test02(){
//查询用户信息，按照年龄的降序排序，若年龄相同，则按照id的升序排序
    //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 ORDER BY age DESC,uid ASC
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.orderByDesc("age")
            .orderByAsc("uid");
    List<User> list = userMapper.selectList(queryWrapper);
    list.forEach(System.out::println);
}

@Test
    public void test03(){
    //删除邮箱地址为null的用户信息
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    QueryWrapper<User> email = queryWrapper.isNull("email");
    int delete = userMapper.delete(email);
    System.out.println(delete);
}

@Test
    public void test04(){
    //查询用户名包含a，年龄在20到30之间,或邮箱信息不为null的用户信息
    //UPDATE t_user SET user_name=?, email=? WHERE is_deleted=0 AND (age > ? AND user_name LIKE ? OR email IS NULL)
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.gt("age",20)
                    .like("user_name","a")
                            .or()
                                    .isNull("email");
    User user = new User();
    user.setName("小明");
    user.setEmail("12345@qq.com");
    int update = userMapper.update(user, queryWrapper);

}

@Test
    public void test05(){
    //查询用户名包含a，并且（年龄大于20或邮箱信息不为null的用户信息）的用户信息修改
    //lambda中的条件优先执行
    //UPDATE t_user SET user_name=?, email=? WHERE is_deleted=0 AND (user_name LIKE ? AND (age > ? OR email IS NULL))
    QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
    userQueryWrapper.like("user_name","a")
            .and(i->i.gt("age",20).or().isNull("email"));
    User user = new User();
    user.setName("小明");
    user.setEmail("12345@qq.com");
    int update = userMapper.update(user, userQueryWrapper);
    System.out.println(update);
    //
}

@Test
    public void test06(){
    //查询用户的用户名，年龄，邮箱信息
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.select("user_name","age","email");
    List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
    maps.forEach(System.out::println);
}

@Test
    public void test07(){
    //查询id小鱼等于100的用户信息
    //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (uid IN (select uid from t_user where uid<=100))
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    QueryWrapper<User> queryWrapper1 = queryWrapper.inSql("uid", "select uid from t_user where uid<=100");
    List<User> list = userMapper.selectList(queryWrapper1);
    list.forEach(System.out::println);
}

@Test
    public void test08(){
    //查询用户名包含a，并且（年龄大于20或邮箱信息为null的用户信息）的用户信息修改
   //UPDATE t_user SET user_name=?,email=? WHERE is_deleted=0 AND (user_name LIKE ? AND (age > ? OR email IS NULL))
    UpdateWrapper<User> updateWrapper = new UpdateWrapper();
    updateWrapper.like("user_name" ,"a")
            .and(i->i.gt("age",20).or().isNull("email"));

    updateWrapper.set("user_name","小黑").set("email","abc@guigu,com");
    int update = userMapper.update(null, updateWrapper);
    System.out.println("updata"+update);
}

@Test
    public void tset09(){
    //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (age >= ? AND age <= ?)
    String username="";
    Integer ageBegin=20;
    Integer ageEnd=30;
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    if(StringUtils.isNotBlank(username)){
        //isNotBlank判断某个字符串是否不为空字符串，不为null，不为空白符
        queryWrapper.like("user_name",username);
    }
    if (ageBegin!=null){
        queryWrapper.ge("age",ageBegin);
    }
    if(ageEnd!=null){
        queryWrapper.le("age",ageEnd);
    }
    List<User> list = userMapper.selectList(queryWrapper);
    list.forEach(System.out::println);
}

@Test
    public void test10(){
    //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (true LIKE ? AND age <= ?)
    String username="a";
    Integer ageBegin=null;
    Integer ageEnd=30;
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.like(String.valueOf(StringUtils.isNotBlank(username)),"user_name")
            .ge(ageBegin!=null,"age",ageBegin)
            .le(ageEnd!=null,"age",ageEnd);
    List<User> list = userMapper.selectList(queryWrapper);
    list.forEach(System.out::println);
}
}
