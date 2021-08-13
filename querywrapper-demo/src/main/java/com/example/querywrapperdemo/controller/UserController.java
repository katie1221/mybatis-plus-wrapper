package com.example.querywrapperdemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.querywrapperdemo.dao.UserMapper;
import com.example.querywrapperdemo.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author qzz
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserMapper  userMapper;

    /**
     * 案例一：根据name模糊查看未删除的用户列表信息
     * @param name
     * @return
     */
    @RequestMapping("/list")
    public Map<String,Object> getList(@RequestParam String name){
        Map<String,Object> result = new HashMap<>();

        //构建一个查询的wrapper
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        //name不为空时，组装模糊查询条件
        wrapper.like(StringUtils.isNotBlank(name),"name",name);
        //未删除
        wrapper.eq("del_flag",0);
        //创建时间降序
        wrapper.orderByDesc("create_time");

        List<User> list = userMapper.selectList(wrapper);
        result.put("data",list);
        return result;
    }

    /**
     * 案例二：查看姓张的并且邮箱不为空的用户列表
     * @return
     */
    @RequestMapping("/list2")
    public Map<String,Object> getList2(){
        Map<String,Object> result = new HashMap<>();

        //构建一个查询的wrapper
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        //姓张的
        wrapper.likeRight("name","张");
        //邮箱不为空
        wrapper.isNotNull("email");
        //未删除
        wrapper.eq("del_flag",0);
        //创建时间降序
        wrapper.orderByDesc("create_time");

        List<User> list = userMapper.selectList(wrapper);
        result.put("data",list);
        return result;
    }

    /**
     * 案例三：年龄范围查询（20-30之间的）
     * @return
     */
    @RequestMapping("/list3")
    public Map<String,Object> getList3(){
        Map<String,Object> result = new HashMap<>();

        //构建一个查询的wrapper
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        //年龄20-30之间的
        wrapper.between("age",20,30);
        //未删除
        wrapper.eq("del_flag",0);
        //创建时间降序
        wrapper.orderByDesc("create_time");

        List<User> list = userMapper.selectList(wrapper);
        result.put("data",list);
        return result;
    }

    /**
     * 案例四：根据createTime查看当日的用户列表
     * @return
     */
    @RequestMapping("/list4")
    public Map<String,Object> getList4(@RequestParam String createTime){
        Map<String,Object> result = new HashMap<>();

        //构建一个查询的wrapper
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        //查询条件为创建时间
        wrapper.apply(StringUtils.isNotBlank(createTime),"DATE(create_time) = STR_TO_DATE('"+createTime+"','%Y-%m-%d')");
        //未删除
        wrapper.eq("del_flag",0);
        //创建时间降序
        wrapper.orderByDesc("create_time");

        List<User> list = userMapper.selectList(wrapper);
        result.put("data",list);
        return result;
    }

    /**
     * 案例五：查看某个时间段内的用户列表
     * @return
     */
    @RequestMapping("/list5")
    public Map<String,Object> getList5(@RequestParam String startTime,@RequestParam String endTime){
        Map<String,Object> result = new HashMap<>();

        //构建一个查询的wrapper
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        //查询条件为创建时间
        wrapper.apply(StringUtils.isNotBlank(startTime),"DATE(create_time) >= STR_TO_DATE('"+startTime+"','%Y-%m-%d')");
        wrapper.apply(StringUtils.isNotBlank(endTime),"DATE(create_time) <= STR_TO_DATE('"+endTime+"','%Y-%m-%d')");
        //未删除
        wrapper.eq("del_flag",0);
        //创建时间降序
        wrapper.orderByDesc("create_time");

        List<User> list = userMapper.selectList(wrapper);
        result.put("data",list);
        return result;
    }

    /**
     * 案例六：查询姓李的并且邮箱不为空或者是年龄大于16的用户
     * @return
     */
    @RequestMapping("/list6")
    public Map<String,Object> getList6(){
        Map<String,Object> result = new HashMap<>();

        //构建一个查询的wrapper
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        //and方法嵌套
        wrapper.likeRight("name","李").and(
                userQueryWrapper -> userQueryWrapper.isNotNull("email")
                .or().lt("age",16)
        );
        //未删除
        wrapper.eq("del_flag",0);
        //创建时间降序
        wrapper.orderByDesc("create_time");

        List<User> list = userMapper.selectList(wrapper);
        result.put("data",list);
        return result;
    }

    /**
     * 案例七：根据ids查看用户列表信息
     * @return
     */
    @RequestMapping("/list7")
    public Map<String,Object> getList7(@RequestParam String ids){
        Map<String,Object> result = new HashMap<>();

        //构建一个查询的wrapper
        QueryWrapper<User> wrapper = new QueryWrapper<User>();

        if(StringUtils.isNotBlank(ids)){
            //字符串转数组再转List
            Collection<String> collection = Arrays.asList(ids.split(","));
            //in方法
            wrapper.in(collection.size()>0,"id",collection);
        }

        //未删除
        wrapper.eq("del_flag",0);
        //创建时间降序
        wrapper.orderByDesc("create_time");

        List<User> list = userMapper.selectList(wrapper);
        result.put("data",list);
        return result;
    }

    /**
     * 案例八：根据条件查看用户列表信息----自己编写sql
     * @return
     */
    @RequestMapping("/list8")
    public Map<String,Object> getList8(){
        Map<String,Object> result = new HashMap<>();

        //构建一个查询的wrapper
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        //未删除
        wrapper.eq("del_flag",0);
        //创建时间降序
        wrapper.orderByDesc("create_time");

        List<User> list = userMapper.getUserList(wrapper);
        result.put("data",list);
        return result;
    }
}
