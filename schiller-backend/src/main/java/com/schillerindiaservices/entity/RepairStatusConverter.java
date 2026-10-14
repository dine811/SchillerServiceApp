package com.schillerindiaservices.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RepairStatusConverter implements AttributeConverter<RepairStatus, String> {
    @Override
    public String convertToDatabaseColumn(RepairStatus attribute) {
        return attribute == null ? null : attribute.databaseValue();
    }

    @Override
    public RepairStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : RepairStatus.from(dbData);
    }
}
