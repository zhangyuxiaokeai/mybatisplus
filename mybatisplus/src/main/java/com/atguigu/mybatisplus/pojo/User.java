package com.atguigu.mybatisplus.pojo;

import com.atguigu.mybatisplus.enums.SexEnum;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
//@TableName("t_user") //设置实体类对应的表明
public class User {
    //将属性所对应的字段指定为主键
    //@TableId的value属性用于指定主键的字段
    //@TableId的type属性用于设置主键的生成策略
    @TableId(value = "uid",type = IdType.AUTO)
    private Long id;
    //指定属性所对应的字段名
    @TableField("user_name")
    private String name;
    private Integer age;
    private String email;
    private SexEnum sex;
    @TableLogic
    private Integer isDeleted;
}
