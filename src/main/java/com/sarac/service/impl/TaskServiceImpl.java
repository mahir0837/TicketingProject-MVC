package com.sarac.service.impl;

import com.sarac.Repository.TaskRepository;
import com.sarac.dto.ProjectDTO;
import com.sarac.dto.TaskDTO;
import com.sarac.dto.UserDTO;
import com.sarac.entity.Project;
import com.sarac.entity.Task;
import com.sarac.enums.Status;
import com.sarac.mapper.ProjectMapper;
import com.sarac.mapper.TaskMapper;
import com.sarac.mapper.UserMapper;
import com.sarac.service.TaskService;
import com.sarac.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {


    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final UserMapper userMapper;


    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, ProjectMapper projectMapper, UserService userService, UserMapper userMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        Optional<Task> task=taskRepository.findById(id);
        if (task.isPresent()){
            return taskMapper.convertToDto(task.get());
        }
        return null;
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        List<Task>taskList=taskRepository.findAll();
        return taskList.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(TaskDTO dto) {
        dto.setTaskStatus(Status.OPEN);
        dto.setAssignedDate(LocalDate.now());
        Task task = taskMapper.convertToEntity(dto);
        taskRepository.save(task);
    }

    @Override
    public void update(TaskDTO dto) {

        Optional<Task> task=taskRepository.findById(dto.getId());

        Task convertTask=taskMapper.convertToEntity(dto);

        if (task.isPresent()) {
            convertTask.setTaskStatus(dto.getTaskStatus()==null?task.get().getTaskStatus()
                    : dto.getTaskStatus());
            convertTask.setAssignedDate(task.get().getAssignedDate());

            taskRepository.save(convertTask);
        }
    }

    @Override
    public void delete(Long id) {

        Optional<Task> task=taskRepository.findById(id);

        task.get().setIsDeleted(true);

        taskRepository.save(task.get());
    }

    @Override
    public int totalNonCompletedTask(String projectCode) {
        return taskRepository.totalNonCompletedTasks(projectCode);

    }

    @Override
    public int totalCompletedTask(String projectCode) {
        return taskRepository.totalCompletedTasks(projectCode);
    }

    @Override
    public void deleteByProject(ProjectDTO dto) {

        Project project=projectMapper.convertToEntity(dto);
        List<Task> tasks=taskRepository.findAllByProject(project);
        tasks.forEach(task -> delete(task.getId()));
    }

    @Override
    public void completeByProject(ProjectDTO projectDto) {
        Project project=projectMapper.convertToEntity(projectDto);
        List<Task> tasks=taskRepository.findAllByProject(project);
        tasks.stream().map(taskMapper::convertToDto).forEach(taskDTO -> {
            taskDTO.setTaskStatus(Status.COMPLETE);
            update(taskDTO);
        });
    }

    @Override
    public List<TaskDTO> listAllTaskByStatusIsNot(Status status) {
        UserDTO loggedInUser=userService.findByUSerName("john@employee.com");
        List<Task>tasks=taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee(status,userMapper.convertToEntity(loggedInUser));
        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllTaskByStatus(Status status) {
        UserDTO loggedInUser=userService.findByUSerName("john@employee.com");
        List<Task>tasks=taskRepository.findAllByTaskStatusAndAssignedEmployee(status,userMapper.convertToEntity(loggedInUser));
        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllNonCompletedByAssignedEmployee(UserDTO assignedEmployee) {
        List<Task>tasks=taskRepository
                .findAllByTaskStatusIsNotAndAssignedEmployee(Status.COMPLETE,userMapper.convertToEntity(assignedEmployee));
        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }
}
