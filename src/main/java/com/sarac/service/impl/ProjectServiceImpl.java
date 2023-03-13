package com.sarac.service.impl;

import com.sarac.dto.ProjectDTO;
import com.sarac.enums.Status;
import com.sarac.service.ProjectService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectServiceImpl extends AbstractMapService<ProjectDTO, String> implements ProjectService {




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
}
