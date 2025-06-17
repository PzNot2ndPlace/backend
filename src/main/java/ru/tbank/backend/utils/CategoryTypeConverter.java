package ru.tbank.backend.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.tbank.backend.enums.CategoryType;

@Converter(autoApply = true)
public class CategoryTypeConverter implements AttributeConverter<CategoryType, String> {

    @Override
    public String convertToDatabaseColumn(CategoryType attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public CategoryType convertToEntityAttribute(String dbData) {
        return dbData != null ? CategoryType.fromValue(dbData) : null;
    }
}