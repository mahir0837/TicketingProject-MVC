package com.sarac.converter;

import com.sarac.dto.ProjectDTO;
import com.sarac.service.ProjectService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProjectDtoConverter implements Converter<String,ProjectDTO> {

    private final ProjectService projectService;

    public ProjectDtoConverter(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public ProjectDTO convert(String source) {
        if (source==null||source.equals("")){
            return null;
        }
        return projectService.getByProjectCode(source);
    }
}
