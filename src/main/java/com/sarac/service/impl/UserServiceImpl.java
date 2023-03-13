package com.sarac.service.impl;

import com.sarac.dto.UserDTO;
import com.sarac.service.UserService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl extends AbstractMapService<UserDTO,String> implements UserService {

    @Override
    public UserDTO save(UserDTO user) {
        return super.save(user.getUserName(),user);
    }

    @Override
    public UserDTO findById(String username) {
        return super.findByID(username);
    }

    @Override
    public List<UserDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(String username) {
        super.deleteByID(username);
    }

    @Override
    public void update(UserDTO object) {
        super.update(object.getFirstName(),object);
    }
}