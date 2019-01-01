package io.lxx.opencartservice.service.impl;

import io.lxx.opencartservice.dao.UserMapper;
import io.lxx.opencartservice.po.User;
import io.lxx.opencartservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getById(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user;
    }
}
