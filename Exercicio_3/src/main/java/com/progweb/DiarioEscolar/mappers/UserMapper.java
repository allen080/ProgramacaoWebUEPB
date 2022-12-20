package com.progweb.DiarioEscolar.mappers;


import com.progweb.DiarioEscolar.domain.User;
import com.progweb.DiarioEscolar.domain.dtos.UserDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;
    
    public UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return userDTO;
    }

    public User convertFromUserDTO(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
    
        return user;
    }
}
