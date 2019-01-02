package io.lxx.opencartadminwebapi.admin.controller;

import io.lxx.opencartservice.dto.UserAddDTO;
import io.lxx.opencartadminwebapi.admin.exception.BackendClientException;
import io.lxx.opencartservice.po.User;
import io.lxx.opencartservice.service.UserService;
import io.lxx.opencartservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 新增用户
     * @param userAddDTO
     */
    @PostMapping("/add")
    public void add(@RequestBody UserAddDTO userAddDTO){
        userService.add(userAddDTO);
    }

}
