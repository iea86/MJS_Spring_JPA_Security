package com.epam.esm.auth.converter;

import com.epam.esm.auth.dto.UserDTO;
import com.epam.esm.auth.entity.Role;
import com.epam.esm.auth.entity.Status;
import com.epam.esm.auth.entity.User;
import com.epam.esm.auth.util.CryptUtil;


import java.util.ArrayList;
import java.util.List;

public class UserConverter {

    private UserConverter() {
    }

    public static User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(CryptUtil.hashString(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setStatus(Status.ACTIVE);
         List<Role> roles = new ArrayList<>();
         Role role = new Role();
            role.setRoleName("role_user");
            roles.add(role);
            user.setRoles(roles);

        user.setEnabled(true);
        return user;
    }
}


