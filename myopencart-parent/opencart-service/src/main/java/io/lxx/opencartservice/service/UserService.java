package io.lxx.opencartservice.service;

import io.lxx.opencartservice.dto.UserAddDTO;
import io.lxx.opencartservice.dto.UserUpdateDTO;
import io.lxx.opencartservice.po.User;

public interface UserService {

    User getById(Long userId);

    void add(UserAddDTO userAddDTO);

    User getByUsername(String username);

    void update(UserUpdateDTO userUpdateDTO);
}
