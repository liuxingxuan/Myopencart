package io.lxx.opencartadminwebapi.admin.controller;

import com.alibaba.fastjson.JSON;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.xml.bind.DatatypeConverter;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
@EnableAutoConfiguration
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sendAddress;

    @Autowired
    private RedisTemplate redisTemplate;

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

    /**
     * 重置密码发邮件到邮箱
     * @param email
     */
    @GetMapping("/resetPassword")
    public void resetPassword(@RequestParam @Email String email){
        //随机码
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = secureRandom.generateSeed(3);
        String code = DatatypeConverter.printHexBinary(bytes);

        //发送简单邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sendAddress);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Myopencart Email Verify Code");
        simpleMailMessage.setText("您的随机验证码为:"+code);
        javaMailSender.send(simpleMailMessage);
        //存入redis并设置过期时间
        redisTemplate.opsForValue().set(email,code,10*60, TimeUnit.SECONDS);
    }

    @GetMapping("/verifyCode")
    public void verifyCode(@RequestParam @Email String email,@RequestParam String code) throws BackendClientException {
        String redisCode = (String)redisTemplate.opsForValue().get(email);
        //todo email 是否存在
        if(redisCode == null){
            throw new BackendClientException("email verify code is expire");
        }
        if(!redisCode.equals(code)){
            throw new BackendClientException("email verify code is invalid");
        }
        userService.changeUserPassworddByEmail(email,"123456");
    }
}
