package com.sarac.service;

import com.sarac.dto.ProjectDTO;
import com.sarac.dto.UserDTO;

import java.util.List;

public interface ProjectService extends CrudService<ProjectDTO, String>{

    void complete(ProjectDTO projectDTO);
    List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager);

}
