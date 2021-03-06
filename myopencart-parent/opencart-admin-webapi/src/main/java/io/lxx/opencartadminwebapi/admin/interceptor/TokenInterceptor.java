package io.lxx.opencartadminwebapi.admin.interceptor;

import com.alibaba.fastjson.JSON;
import io.lxx.opencartadminwebapi.admin.DTO.LoginInfo;
import io.lxx.opencartadminwebapi.admin.exception.BackendClientException;
import io.lxx.opencartadminwebapi.admin.exception.BackendUnauthenticationException;
import io.lxx.opencartservice.utils.AES;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Value("${token.aes.secret}")
    private String aesSecret;

    //过滤拦截
    private String[] urls = {
            "/user/login",
            "/error",
            "/user/resetPassword",
            "/user/verifyCode"
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws BackendUnauthenticationException, BackendClientException {
        String method = request.getMethod();
        if(method.equals("OPTIONS")){
            return true;
        }

        boolean contains = Arrays.asList(urls).contains(request.getRequestURI());//是否包括uri
        if(contains){
            return true;
        }
        String authorizationStr = request.getHeader("Authorization");//获取头信息
        if(authorizationStr == null){
            throw new BackendUnauthenticationException("Unauthentication");
        }
        String[] s = authorizationStr.split(" ");//解析头信息
        String token = s[1];
        LoginInfo loginInfo;
        try {
            //byte[] decode = Base64.getDecoder().decode(token);//base64解码
            //String loginJsonStr = new String(decode, "UTF-8");
            String loginJsonStr = AES.decrypt(token,aesSecret);
            loginInfo = JSON.parseObject(loginJsonStr,LoginInfo.class);
        } catch (Exception e) {
            throw new BackendClientException("auth invalid caused by some issues");
        }
        Long userId = loginInfo.getUserId();//获取用户id
        String username = loginInfo.getUsername();
        if(username == null){
            throw new BackendUnauthenticationException("Unauthentication");
        }
        long expireTimestamp = loginInfo.getExpirationTime().getTime();//获取过期时间戳
        Date currentTime = new Date();
        long currentTimestamp = currentTime.getTime();//当前时间戳
        if(username == null || username.isEmpty()){
            throw new BackendUnauthenticationException("Unauthentication: username is null or empty");
        }
        if(currentTimestamp > expireTimestamp){
            throw new BackendUnauthenticationException("Unauthentication: token is expired");
        }
        //根据token取出当前用户信息
        request.setAttribute("currentUserId",userId);
        request.setAttribute("username",username);
        return true;
    }
}
