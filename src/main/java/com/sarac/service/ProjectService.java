package com.sarac.service;

import com.sarac.dto.ProjectDTO;

public interface ProjectService extends CrudService<ProjectDTO, String>{

    void complete(ProjectDTO projectDTO);

}
