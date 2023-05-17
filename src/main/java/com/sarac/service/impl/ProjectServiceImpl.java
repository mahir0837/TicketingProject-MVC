package com.sarac.service.impl;

import com.sarac.Repository.ProjectRepository;
import com.sarac.dto.ProjectDTO;
import com.sarac.dto.UserDTO;
import com.sarac.entity.Project;
import com.sarac.entity.User;
import com.sarac.enums.Status;
import com.sarac.mapper.ProjectMapper;
import com.sarac.mapper.UserMapper;
import com.sarac.service.ProjectService;
import com.sarac.service.TaskService;
import com.sarac.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final TaskService taskService;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, @Lazy UserService userService, UserMapper userMapper, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO getByProjectCode(String code) {
        Project project=projectRepository.findByProjectCode(code);
        return projectMapper.convertToDTO(project);
    }

    @Override
    public List<ProjectDTO> listProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream().map(projectMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO dto) {

        dto.setProjectStatus(Status.OPEN);
        Project project = projectMapper.convertToEntity(dto);
        projectRepository.save(project);
    }

    @Override
    public void update(ProjectDTO dto) {

        Project project=projectRepository.findByProjectCode(dto.getProjectCode());

        Project convertProject=projectMapper.convertToEntity(dto);

        convertProject.setId(project.getId());

        convertProject.setProjectStatus(project.getProjectStatus());

        projectRepository.save(convertProject);

    }

    @Override
    public void delete(String code) {

        Project project = projectRepository.findByProjectCode(code);
        project.setIsDeleted(true);

        project.setProjectCode(project.getProjectCode() + "-" + project.getId());

        projectRepository.save(project);

        taskService.deleteByProject(projectMapper.convertToDTO(project));
    }

    @Override
    public void complete(String projectCode) {

        Project project = projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);
        taskService.completeByProject(projectMapper.convertToDTO(project));
    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {

        String userName= SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO currentUserdto=userService.findByUSerName(userName);

        User user=userMapper.convertToEntity(currentUserdto);

        List<Project> list=projectRepository.findAllByAssignedManager(user);

        return list.stream().map(project->{

           ProjectDTO obj=projectMapper.convertToDTO(project);

           obj.setUnfinishedTaskCounts(taskService.totalNonCompletedTask(project.getProjectCode()));
           obj.setCompleteTaskCounts(taskService.totalCompletedTask(project.getProjectCode()));
           return obj;
        }).collect(Collectors.toList());

    }

    @Override
    public List<ProjectDTO> listAllNonCompletedByAssignedManager(UserDTO assignedManager) {
        List<Project>projects=projectRepository.findAllByProjectStatusIsNotAndAssignedManager(Status.COMPLETE,userMapper.convertToEntity(assignedManager));
        return projects.stream().map(projectMapper::convertToDTO).collect(Collectors.toList());
    }
}
