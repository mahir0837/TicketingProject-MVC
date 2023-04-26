package com.sarac.mapper;

import com.sarac.dto.TaskDTO;
import com.sarac.entity.Task;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper  {

    private final ModelMapper modelMapper;

    public TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Task convertToEntity(TaskDTO dto){

        return modelMapper.map(dto,Task.class);
    }

    public TaskDTO convertToDto(Task entity){

        return modelMapper.map(entity,TaskDTO.class);
    }
}
