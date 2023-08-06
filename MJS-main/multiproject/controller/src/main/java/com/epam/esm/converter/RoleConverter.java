package com.epam.esm.converter;

import com.epam.esm.dto.RoleDTO;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.UserRole;

public class RoleConverter {

    private RoleConverter() {
    }

    public static RoleDTO convertToDto(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getRoleName().getName());
        return roleDTO;
    }

    public static Role convertToEntity(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setRoleName(UserRole.from(roleDTO.getName()));
        return role;
    }
}
