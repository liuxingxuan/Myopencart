package io.lxx.opencartservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.lxx.opencartservice.dao.UserMapper;
import io.lxx.opencartservice.dto.UserAddDTO;
import io.lxx.opencartservice.dto.UserListDTO;
import io.lxx.opencartservice.dto.UserUpdateDTO;
import io.lxx.opencartservice.po.User;
import io.lxx.opencartservice.service.UserService;
import io.lxx.opencartservice.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Service
@EnableAutoConfiguration
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
        user.setRoles(JSON.toJSONString(userAddDTO.getRoles()));
        userMapper.insert(user);
    }

    @Override
    public User getByUsername(String username) {
        return userMapper.getByUsername(username);
    }

    @Override
    public void update(UserUpdateDTO userUpdateDTO) {
        //先根据userId查找用户
        User user = userMapper.selectByPrimaryKey(userUpdateDTO.getUserId());
        user.setUsername(userUpdateDTO.getUsername());
        user.setFirstName(userUpdateDTO.getFirstName());
        user.setLastName(userUpdateDTO.getLastName());
        user.setEmail(userUpdateDTO.getEmail());
        user.setAvatarUrl(userUpdateDTO.getAvatarUrl());
        user.setEncryptedPassword(DigestUtils.md5DigestAsHex(userUpdateDTO.getPassword().getBytes()));
        user.setRoles(JSON.toJSONString(userUpdateDTO.getRoles()));
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public PageInfo<UserListDTO> getUsersWithPage(Integer pageNum) {
        //todo change pagesize
        PageHelper.startPage(pageNum,10);
        Page<UserListDTO> user = userMapper.selectWithPage();
        PageInfo<UserListDTO> pageInfo = user.toPageInfo();
        return pageInfo;
    }

    @Override
    public void changeUserPassworddByEmail(String email, String password) {
        User user = userMapper.selectByEmail(email);
        user.setEncryptedPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public void batchdelete(List<Integer> userIds) {
        userMapper.batchdelete(userIds);
    }
}
