package com.sarac.service.impl;

import com.sarac.dto.ProjectDTO;
import com.sarac.dto.TaskDTO;
import com.sarac.dto.UserDTO;
import com.sarac.enums.Status;
import com.sarac.service.ProjectService;
import com.sarac.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl extends AbstractMapService<ProjectDTO, String> implements ProjectService {

private final TaskService taskService;

    public ProjectServiceImpl(TaskService taskService) {
        this.taskService = taskService;
    }


    @Override
    public ProjectDTO save(ProjectDTO project) {

        if (project.getProjectStatus()==null) {
            project.setProjectStatus(Status.OPEN);
        }
        return super.save(project.getProjectCode(), project);
    }

    @Override
    public ProjectDTO findById(String project) {
        return super.findByID(project);
    }

    @Override
    public List<ProjectDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(String projectId) {
        super.deleteByID(projectId);
    }

    @Override
    public void update(ProjectDTO project) {

        if (project.getProjectStatus()==null) {
            project.setProjectStatus(findById(project.getProjectCode()).getProjectStatus());
        }
        super.update(project.getProjectCode(), project);
    }


    @Override
    public void complete(ProjectDTO projectDTO) {

        projectDTO.setProjectStatus(Status.COMPLETE);
    }

    @Override
    public List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager) {


        List<ProjectDTO>projectList=
                findAll()
                        .stream()
                        .filter(projectDTO -> projectDTO.getAssignedManager().equals(manager))
                        .map(projectDTO -> {

                            List<TaskDTO>taskList= taskService.findTaskByManager(manager);

                            int completeTask= (int) taskList.stream()
                                    .filter(t->t.getProject().equals(projectDTO)&&t.getTaskStatus()==Status.COMPLETE)
                                    .count();

                            int unfinishedTask= (int) taskList.stream()
                                    .filter(t->t.getProject().equals(projectDTO)&&t.getTaskStatus()!=Status.COMPLETE)
                                    .count();

                            projectDTO.setCompleteTaskCounts(completeTask);
                            projectDTO.setUnfinishedTaskCounts(unfinishedTask);
                            return projectDTO;
                        })
                        .collect(Collectors.toList());

        return projectList;
    }
}
