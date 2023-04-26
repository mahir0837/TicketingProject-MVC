package com.sarac.service;


import com.sarac.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO>listAllUsers();
    UserDTO findByUSerName(String username);
    void save(UserDTO user);
    UserDTO update(UserDTO user);
    void delete(String username);
    List<UserDTO>listByRole(String role);
}
