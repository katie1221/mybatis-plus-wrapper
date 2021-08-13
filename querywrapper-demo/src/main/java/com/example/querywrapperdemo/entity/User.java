package com.example.querywrapperdemo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @author qzz
 */
@Data
@TableName("t_user")
public class User implements Serializable {
    private  static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value="id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 年龄
     */
    @TableField("age")
    private Integer age;

    /**
     * 删除标识 0：正常 1：删除  默认0
     */
    @TableField("del_flag")
    private Integer del_flag;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date create_time;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date update_time;
}
