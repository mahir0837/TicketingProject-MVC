package com.sarac.service;

import com.sarac.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    List<RoleDTO>listAllRoles();
    RoleDTO findById(Long id);
}
