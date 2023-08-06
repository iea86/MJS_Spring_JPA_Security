package com.epam.esm.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {

    @Override
    public String convertToDatabaseColumn(UserRole attribute) {
        return attribute.getName();
    }

    @Override
    public UserRole convertToEntityAttribute(String dbData) {
        System.out.println("in converter");
        return UserRole.from(dbData);
    }
}
