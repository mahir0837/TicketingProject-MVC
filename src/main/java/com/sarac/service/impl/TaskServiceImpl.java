package com.sarac.service.impl;

import com.sarac.dto.TaskDTO;
import com.sarac.dto.UserDTO;
import com.sarac.enums.Status;
import com.sarac.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl extends AbstractMapService<TaskDTO,Long> implements TaskService {

    @Override
    public TaskDTO save(TaskDTO task) {
        if (task.getTaskStatus()==null)
            task.setTaskStatus(Status.OPEN);

        if (task.getAssignedDate()==null)
            task.setAssignedDate(LocalDate.now());

        if (task.getId()==null)
            task.setId(UUID.randomUUID().getMostSignificantBits());

        return super.save(task.getId(), task);
    }

    @Override
    public TaskDTO findById(Long id) {
        return super.findByID(id);
    }

    @Override
    public List<TaskDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {

        super.deleteByID(id);
    }

    @Override
    public void update(TaskDTO task) {

        TaskDTO foundTask=findById(task.getId());

        task.setTaskStatus(foundTask.getTaskStatus());

        task.setAssignedDate(foundTask.getAssignedDate());

        super.update(task.getId(), task);
    }


    @Override
    public List<TaskDTO> findTaskByManager(UserDTO manager) {

        return findAll().stream().filter(taskDTO -> taskDTO.getProject().getAssignedManager().equals(manager))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllTaskByStatusIsNot(Status status) {
        return findAll().stream()
                .filter(taskDTO -> !taskDTO.getTaskStatus().equals(status)).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllTaskByStatus(Status status) {
        return findAll().stream()
                .filter(taskDTO -> taskDTO.getTaskStatus().equals(status)).collect(Collectors.toList());
    }

    @Override
    public void updateStatus(TaskDTO task) {
        findById(task.getId()).setTaskStatus(task.getTaskStatus());
        update(task);
    }
}
