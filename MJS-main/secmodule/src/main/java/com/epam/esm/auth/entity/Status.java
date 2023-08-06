package com.epam.esm.auth.entity;

public enum Status {

    ACTIVE("active"),
    INACTIVE("inactive");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Status from(String name) {
        for (Status status : values()) {
            if (status.getName().toUpperCase().equals(name.toUpperCase()))
                return status;
        }
        throw new IllegalArgumentException("No such status " + name);
    }
}
