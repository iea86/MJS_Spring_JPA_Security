package com.epam.esm.auth.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status attribute) {
        return attribute.getName();
    }

    @Override
    public Status convertToEntityAttribute(String dbData) {
        return Status.from(dbData);
    }
}

