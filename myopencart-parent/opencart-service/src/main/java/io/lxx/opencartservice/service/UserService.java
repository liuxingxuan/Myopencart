package io.lxx.opencartservice.service;

import com.github.pagehelper.PageInfo;
import io.lxx.opencartservice.dto.UserAddDTO;
import io.lxx.opencartservice.dto.UserListDTO;
import io.lxx.opencartservice.dto.UserUpdateDTO;
import io.lxx.opencartservice.po.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {

    User getById(Long userId);

    void add(UserAddDTO userAddDTO);

    User getByUsername(String username);

    void update(UserUpdateDTO userUpdateDTO);

    PageInfo<UserListDTO> getUsersWithPage(Integer pageNum);

    void changeUserPassworddByEmail(String email, String password);

    void batchdelete(@Param("userIds") List<Integer> userIds);
}
