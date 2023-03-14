package com.sarac.service;
import com.sarac.dto.TaskDTO;
import com.sarac.dto.UserDTO;
import com.sarac.enums.Status;

import java.util.List;

public interface TaskService extends CrudService<TaskDTO,Long>{
    List<TaskDTO>findTaskByManager(UserDTO manager);
    List<TaskDTO>findAllTaskByStatusIsNot(Status status);
    List<TaskDTO>findAllTaskByStatus(Status status);
    void updateStatus(TaskDTO task);
}
