package com.shop.polyedrum.service;

import com.shop.polyedrum.dto.UserDTO;
import com.shop.polyedrum.domain.User;
import com.shop.polyedrum.exception.ApiException;

import java.util.List;

public interface UserService{
    List<UserDTO> getUsers();
    User getUserById(Long id);
    UserDTO getUserDTOById(Long id);
    String deleteUser(Long id);
    String updateUser(UserDTO userDTO, Long id);
    User findUserByJwt(String token) throws ApiException;
}
