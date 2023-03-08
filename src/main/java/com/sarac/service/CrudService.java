package com.sarac.service;

import com.sarac.dto.UserDTO;

import java.util.List;

public interface CrudService <T,ID>  {

    T save(T user);
    T findById(ID username);
    List<T> findAll();
    void deleteById(ID username);
}
