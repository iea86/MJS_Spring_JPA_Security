package com.epam.esm.entity;


public enum UserRole {
    ROLE_USER("role_user"),
    ROLE_ADMIN("role_admin");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static UserRole from(String name) {
        for (UserRole userRole : values()) {
            if (userRole.getName().toUpperCase().equals(name.toUpperCase()))
                return userRole;
        }
        throw new IllegalArgumentException("No such user role " + name);
    }
}