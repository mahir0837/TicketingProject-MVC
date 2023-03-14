package com.sarac.service;

import com.sarac.dto.UserDTO;

import java.util.List;

public interface UserService extends CrudService<UserDTO,String> {

    List<UserDTO> findManager();
    List<UserDTO> findEmployee();

}
