package io.lxx.opencartadminwebapi.admin.controller;

import com.alibaba.fastjson.JSON;
import io.lxx.opencartadminwebapi.admin.DTO.LoginInfo;
import io.lxx.opencartservice.dto.UserAddDTO;
import io.lxx.opencartadminwebapi.admin.exception.BackendClientException;
import io.lxx.opencartservice.dto.UserUpdateDTO;
import io.lxx.opencartservice.po.User;
import io.lxx.opencartservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 根据Id查找用户
     * @param userId
     * @return
     */
    @GetMapping("/getById")
    public User getById(@RequestParam Long userId){
        User user = userService.getById(userId);
        return user;
    }

    /**
     * 拿到当前用户信息
     * @param userId
     * @return
     */
    @GetMapping("/getCurrentUserInfo")
    public User getCurrentUserInfo(@RequestAttribute Long userId){
        User currentUser = userService.getById(userId);
        return currentUser;
    }
    /**
     * 新增用户
     * @param userAddDTO
     */
    @PostMapping("/add")
    public void add(@RequestBody UserAddDTO userAddDTO){
        userService.add(userAddDTO);
    }
    @GetMapping("/login")
    public String login(String username,String password) throws BackendClientException {
        User user = userService.getByUsername(username);
        if(user == null){
            throw new BackendClientException("user isn't exist");
        }
        String inputpassword = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!user.getEncryptedPassword().equals(inputpassword)){
            throw new BackendClientException("password isn't match");
        }
        LoginInfo loginInfo = new LoginInfo(user.getUserId(), user.getUsername(), user.getRoles(), new Date());
        String loginInfoStr = JSON.toJSONString(loginInfo);//对象转换为字符串
        String token = Base64.getEncoder().encodeToString(loginInfoStr.getBytes());//Base64转码
        return token;
    }

    /**
     * 修改user信息
     * @param userUpdateDTO
     */
    @PostMapping("/update")
    public void update(@RequestBody UserUpdateDTO userUpdateDTO){
        userService.update(userUpdateDTO);
    }
}
