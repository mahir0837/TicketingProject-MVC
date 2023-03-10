package com.sarac.service.impl;

import com.sarac.dto.UserDTO;
import com.sarac.service.UserService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<UserDTO> findManager() {
        return findAll().stream().filter(userDTO -> userDTO.getRole().getId()==2).collect(Collectors.toList());
    }
}
