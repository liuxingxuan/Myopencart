package io.lxx.opencartservice.dao;

import io.lxx.opencartservice.dto.UserAddDTO;
import io.lxx.opencartservice.dto.UserUpdateDTO;
import io.lxx.opencartservice.po.User;

public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User getByUsername(String username);

    void add(UserAddDTO userAddDTO);

    void update(UserUpdateDTO userUpdateDTO);
}