package com.sarac.converter;


import com.sarac.dto.UserDTO;
import com.sarac.service.UserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter implements Converter<String,UserDTO> {

    private final UserService userService;

    public UserDtoConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDTO convert(String source) {
        if (source==null||source.equals("")){
            return null;
        }
        return userService.findByUSerName(source);
    }
}
