package com.sarac.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class MapperUtil {

    private final ModelMapper modelMapper;


    public MapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

//    public <T> T convert(Object objectToConverted, T convertedObject){
//
//       return modelMapper.map(objectToConverted,(Type) convertedObject.getClass());
//    }

    public <T> T convert(Object objectToConverted, Class<T> convertedObject){

        return modelMapper.map(objectToConverted, convertedObject);
    }
}
