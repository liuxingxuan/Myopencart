package io.lxx.opencartservice.service.impl;

import io.lxx.opencartservice.dao.UserMapper;
import io.lxx.opencartservice.dto.UserAddDTO;
import io.lxx.opencartservice.po.User;
import io.lxx.opencartservice.service.UserService;
import io.lxx.opencartservice.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getById(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user;
    }

    @Override
    public void add(@RequestBody UserAddDTO userAddDTO) {
        User user = new User();
        user.setUsername(userAddDTO.getUsername());
        user.setFirstName(userAddDTO.getFirstName());
        user.setLastName(userAddDTO.getLastName());
        user.setEmail(userAddDTO.getEmail());
        user.setAvatarUrl(userAddDTO.getAvatarUrl());
        //密码MD5处理  用springboot自带的
        user.setEncryptedPassword(DigestUtils.md5DigestAsHex(userAddDTO.getPassword().getBytes()));
        user.setRoles(Constant.rolesStr);
        userMapper.insert(user);
    }
}
