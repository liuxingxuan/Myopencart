package io.lxx.opencartadminwebapi.admin.DTO;

import io.lxx.opencartservice.utils.Constant;

import java.util.Date;

public class LoginInfo {
    private Long userId;
    private String username;
    private String roles;
    private Date issuedAt;
    private Date expirationTime;


    public LoginInfo(Long userId, String username, String roles, Date issuedAt) {
        this.userId = userId;
        this.username = username;
        this.roles = roles;
        this.issuedAt = issuedAt;

        long issuedAtTimestamp = issuedAt.getTime();//当前时间戳
        long expirationTimestamp = issuedAtTimestamp + Constant.expireDuration *60*1000;//过期时间  分钟转为毫秒
        this.expirationTime = new Date(expirationTimestamp);//设置过期时间
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }
}
