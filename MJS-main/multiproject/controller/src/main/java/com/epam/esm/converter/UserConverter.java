package com.epam.esm.converter;

import com.epam.esm.dto.RoleDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.Status;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserRole;
import com.epam.esm.util.CryptUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {

    private UserConverter() {
    }

    public static UserDTO convertToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setStatus(user.getStatus().name());
        List<Role> roles = user.getRoles();
        List<RoleDTO> rolesDTO = roles.stream().map(RoleConverter::convertToDto).collect(Collectors.toList());
        userDTO.setRoles(rolesDTO);
        return userDTO;
    }

    public static User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getUsername());
        user.setPassword(CryptUtil.hashString(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setStatus(Status.ACTIVE);
        if (userDTO.getRoles() == null) {
            List<Role> roles = new ArrayList<>();
            Role role = new Role();
            role.setRoleName(UserRole.ROLE_USER);
            roles.add(role);
            user.setRoles(roles);
        } else {
            List<Role> roles = userDTO.getRoles()
                    .stream()
                    .map(RoleConverter::convertToEntity)
                    .collect(Collectors.toList());
            user.setRoles(roles);
        }
        user.setEnabled(true);
        return user;
    }
}


