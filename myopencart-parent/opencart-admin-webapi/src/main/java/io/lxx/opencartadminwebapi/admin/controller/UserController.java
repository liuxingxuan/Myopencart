package io.lxx.opencartadminwebapi.admin.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.lxx.opencartadminwebapi.admin.DTO.LoginInfo;
import io.lxx.opencartservice.dto.UserAddDTO;
import io.lxx.opencartadminwebapi.admin.exception.BackendClientException;
import io.lxx.opencartservice.dto.UserListDTO;
import io.lxx.opencartservice.dto.UserUpdateDTO;
import io.lxx.opencartservice.po.User;
import io.lxx.opencartservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Date;

@RestController
@RequestMapping("/user")
@Validated
@EnableAutoConfiguration
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String Sender;

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
    public void add(@RequestBody @Validated UserAddDTO userAddDTO){
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
    public void update(@RequestBody @Validated UserUpdateDTO userUpdateDTO){
        userService.update(userUpdateDTO);
    }

    @GetMapping("/getUsersWithPage")
    public PageInfo<UserListDTO> getUsersWithPage(@RequestParam(required = false,defaultValue = "1")Integer pageNum){
        PageInfo<UserListDTO> usersWithPage = userService.getUsersWithPage(pageNum);
        return usersWithPage;
    }
    //todo batchdelete

    @RequestMapping("/sendSimpleMail")
    public String sendSimpleMail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Sender);//发送人
        message.setTo("853555374@qq.com");//接收人
        message.setSubject("测试邮件");//主题
        message.setText("内容测试");//内容
        mailSender.send(message);
        return "发送成功";
    }
}
