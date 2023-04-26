package com.sarac.mapper;

import com.sarac.dto.ProjectDTO;
import com.sarac.dto.UserDTO;
import com.sarac.entity.Project;
import com.sarac.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Component
public class ProjectMapper {

    private final ModelMapper modelMapper;

    public ProjectMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Project convertToEntity(ProjectDTO dto){

        return modelMapper.map(dto,Project.class);
    }

    public ProjectDTO convertToDTO(Project entity){

        return modelMapper.map(entity,ProjectDTO.class);
    }

}
