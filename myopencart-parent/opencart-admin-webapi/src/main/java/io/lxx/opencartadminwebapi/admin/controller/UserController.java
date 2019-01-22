package io.lxx.opencartadminwebapi.admin.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import io.lxx.opencartadminwebapi.admin.DTO.LoginInfo;
import io.lxx.opencartservice.dto.UserAddDTO;
import io.lxx.opencartadminwebapi.admin.exception.BackendClientException;
import io.lxx.opencartservice.dto.UserGetDTO;
import io.lxx.opencartservice.dto.UserListDTO;
import io.lxx.opencartservice.dto.UserUpdateDTO;
import io.lxx.opencartservice.po.User;
import io.lxx.opencartservice.service.impl.UserServiceImpl;
import io.lxx.opencartservice.utils.AES;
import io.lxx.opencartservice.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.xml.bind.DatatypeConverter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
@EnableAutoConfiguration
@CrossOrigin
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sendAddress;

    @Autowired
    private RedisTemplate redisTemplate;

    //注入aes秘钥
    @Value("${token.aes.secret}")
    private String aesSecret;
    /**
     * 根据Id查找用户
     *
     * @param userId
     * @return
     */
    @GetMapping("/getById")
    public UserGetDTO getById(@RequestParam Long userId) {
        User user = userService.getById(userId);
        UserGetDTO userGetDTO = new UserGetDTO(user);
        return userGetDTO;
    }

    /**
     * 拿到当前用户信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/getCurrentUserInfo")
    public User getCurrentUserInfo(@RequestAttribute Long userId) {
        User currentUser = userService.getById(userId);
        return currentUser;
    }

    /**
     * 新增用户
     *
     * @param userAddDTO
     */
    @PostMapping("/add")
    public void add(@RequestBody @Validated UserAddDTO userAddDTO) {
        userService.add(userAddDTO);
    }

    @GetMapping("/login")
    public String login(String username, String password) throws Exception {
        User user = userService.getByUsername(username);
        if (user == null) {
            throw new BackendClientException("user isn't exist");
        }
        String inputpassword = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!user.getEncryptedPassword().equals(inputpassword)) {
            throw new BackendClientException("password isn't match");
        }
        LoginInfo loginInfo = new LoginInfo(user.getUserId(), user.getUsername(), user.getRoles(), new Date());
        String loginInfoStr = JSON.toJSONString(loginInfo);//对象转换为字符串
       // String token = Base64.getEncoder().encodeToString(loginInfoStr.getBytes());//Base64转码
        String token = AES.encrypt(loginInfoStr,aesSecret);//AES加密
        return token;
    }
    /**
     * 修改user信息
     *
     * @param userUpdateDTO
     */
    @PostMapping("/update")
    public void update(@RequestBody @Validated UserUpdateDTO userUpdateDTO) {
        userService.update(userUpdateDTO);
    }

    @GetMapping("/getUsersWithPage")
    public PageInfo<UserListDTO> getUsersWithPage(@RequestParam(required = false, defaultValue = "1") Integer pageNum) {
        PageInfo<UserListDTO> usersWithPage = userService.getUsersWithPage(pageNum);
        return usersWithPage;
    }

    //todo batchdelete
    @PostMapping("/batchdelete")
    public void batchdelete(@RequestBody List<Integer> userIds, @RequestAttribute Integer currentUserId) throws BackendClientException {
        boolean contains = userIds.contains(currentUserId);//判断集合中是否有当前用户id
        if (contains) {
            throw new BackendClientException("don't delete currentUser");
        }
        userService.batchdelete(userIds);
    }

    /**
     * 重置密码发邮件到邮箱
     *
     * @param email
     */
    @GetMapping("/resetPassword")
    public void resetPassword(@RequestParam @Email String email) {
        //随机码
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = secureRandom.generateSeed(3);
        String code = DatatypeConverter.printHexBinary(bytes);

        //发送简单邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sendAddress);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Myopencart Email Verify Code");
        simpleMailMessage.setText("您的随机验证码为:" + code);
        javaMailSender.send(simpleMailMessage);
        //存入redis并设置过期时间
        redisTemplate.opsForValue().set(email, code, 10 * 60, TimeUnit.SECONDS);
    }

    @GetMapping("/verifyCode")
    public void verifyCode(@RequestParam @Email String email, @RequestParam String code) throws BackendClientException {
        String redisCode = (String) redisTemplate.opsForValue().get(email);
        //todo email 是否存在
        if (redisCode == null) {
            throw new BackendClientException("email verify code is expire");
        }
        if (!redisCode.equals(code)) {
            throw new BackendClientException("email verify code is invalid");
        }
        userService.changeUserPassworddByEmail(email, "123456");
    }

    //Base64转码，只适合小图片
    @PostMapping("/uploadAvatar")
    public void updateAvatar(@RequestBody String acatarData) throws Exception {
        String[] split = acatarData.split(",");
        String type = split[0].split(";")[0].split("/")[1];
        byte[] imgBytes = Base64.getDecoder().decode(split[1]);
        String uuid = UUID.randomUUID().toString();
        String url = String.format("avatar/%s.%s", uuid, type);
        FileUtil.storeFile(imgBytes,url);//存储
    }

    //MultipartFile
    @PostMapping("/uploadAvatar2")
    public String uploadAvatar(@RequestParam("file") MultipartFile file) throws Exception {
        String contentType = file.getContentType();
        if (!contentType.equals("image/png") && !contentType.equals("image/jpeg")) {
            throw new BackendClientException("file only support png or jpg");
        }
        String uuid = UUID.randomUUID().toString();
        String type = file.getContentType().split("/")[1];
        String fileName = String.format("%s.%s", uuid, type);
        String url = String.format("avatar/%s", fileName);
        FileUtil.storeFile(file.getBytes(),url);//存储
        return fileName;
    }
}
