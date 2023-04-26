package com.sarac.service;

import com.sarac.dto.ProjectDTO;
import com.sarac.dto.UserDTO;


import java.util.List;

public interface ProjectService {

    ProjectDTO getByProjectCode(String code);
    List<ProjectDTO>listProjects();
    void save(ProjectDTO dto);
    void update(ProjectDTO dto);
    void delete(String code);
    void complete(String projectCode);
    List<ProjectDTO> listAllProjectDetails();
    List<ProjectDTO> listAllNonCompletedByAssignedManager(UserDTO assignedManager);
}
