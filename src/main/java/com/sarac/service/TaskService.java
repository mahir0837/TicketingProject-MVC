package com.sarac.service;

import com.sarac.dto.ProjectDTO;
import com.sarac.dto.TaskDTO;
import com.sarac.dto.UserDTO;
import com.sarac.enums.Status;

import java.util.List;

public interface TaskService {

    TaskDTO getTaskById(Long id);
    List<TaskDTO>getAllTasks();
    void save(TaskDTO dto);
    void update(TaskDTO dto);
    void delete(Long id);
    int totalNonCompletedTask(String projectCode);
    int totalCompletedTask(String projectCode);
    void deleteByProject(ProjectDTO convertToDTO);
    void completeByProject(ProjectDTO projectDto);
    List<TaskDTO>listAllTaskByStatusIsNot(Status status);
    List<TaskDTO>listAllTaskByStatus(Status status);
    List<TaskDTO> listAllNonCompletedByAssignedEmployee(UserDTO assignedEmployee);
}
