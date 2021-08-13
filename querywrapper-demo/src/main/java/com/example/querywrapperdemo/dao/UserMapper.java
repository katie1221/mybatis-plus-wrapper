package com.example.querywrapperdemo.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.querywrapperdemo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author qzz
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据条件获取用户列表信息
     * @param wrapper
     * @return
     */
    List<User> getUserList(@Param("ew")QueryWrapper<User> wrapper);
}
