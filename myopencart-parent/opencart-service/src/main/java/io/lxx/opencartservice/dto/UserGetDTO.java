package io.lxx.opencartservice.dto;

import com.alibaba.fastjson.JSON;
import io.lxx.opencartservice.po.User;

import java.util.List;

public class UserGetDTO {
    private Long userId;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String avatarUrl;


    private List<String> roles;

    public UserGetDTO(User user){
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.avatarUrl = user.getAvatarUrl();
        this.roles = JSON.parseArray(user.getRoles(),String.class);//数组转为JSON
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
