package com.sarac.service.impl;

import com.sarac.Repository.UserRepository;
import com.sarac.dto.ProjectDTO;
import com.sarac.dto.TaskDTO;
import com.sarac.dto.UserDTO;
import com.sarac.entity.User;
import com.sarac.mapper.UserMapper;
import com.sarac.service.ProjectService;
import com.sarac.service.TaskService;
import com.sarac.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, @Lazy ProjectService projectService, @Lazy TaskService taskService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectService = projectService;
        this.taskService = taskService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        List<User> userList = userRepository.findAllByIsDeletedOrderByFirstNameDesc(false);
        return userList.stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUSerName(String username) {

        User user = userRepository.findByUserNameAndIsDeleted(username, false);
        return userMapper.convertToDTO(user);
    }

    @Override
    public void save(UserDTO user) {

        user.setEnabled(true);

        User obj = userMapper.convertToEntity(user);
        obj.setPassWord(passwordEncoder.encode(obj.getPassWord()));

        userRepository.save(obj);
    }


    @Override
    public UserDTO update(UserDTO user) {
        User user1 = userRepository.findByUserNameAndIsDeleted(user.getUserName(), false);
        user.setEnabled(true);
        User convertedUser = userMapper.convertToEntity(user);

        convertedUser.setId(user1.getId());

        convertedUser.setPassWord(passwordEncoder.encode(convertedUser.getPassWord()));

        userRepository.save(convertedUser);

        return findByUSerName(user.getUserName());
    }

    @Override
    public void delete(String username) {

        User user = userRepository.findByUserNameAndIsDeleted(username, false);
        if (checkidUSerCanBeDeleted(user)) {
            user.setIsDeleted(true);
            user.setUserName(user.getUserName() + "-" + user.getId());
            userRepository.save(user);
        }
    }

    @Override
    public List<UserDTO> listByRole(String role) {
        List<User> users = userRepository.findByRoleDescriptionIgnoreCaseAndIsDeleted(role, false);
        return users.stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }

    private boolean checkidUSerCanBeDeleted(User user) {
        switch (user.getRole().getDescription()) {
            case "Manager":
                List<ProjectDTO> projectDTOList = projectService.listAllNonCompletedByAssignedManager(userMapper.convertToDTO(user));
                return projectDTOList.size() == 0;

            case "Employee":
                List<TaskDTO> taskDTOList = taskService.listAllNonCompletedByAssignedEmployee(userMapper.convertToDTO(user));
                return taskDTOList.size() == 0;
            default:
                return true;
        }
    }
}
