package com.github.beltraliny.admazsshipping.utils;

import com.github.beltraliny.admazsshipping.exceptions.ValidationException;

import java.lang.reflect.Field;

public class ValidationUtils {

    private ValidationUtils() {}

    public static void validateEntityBeforeSave(Object object, String[] fieldsToValidate) {
        try {
            for (String fieldName : fieldsToValidate) {
                // Obtém o atributo da classe.
                Field field = object.getClass().getDeclaredField(fieldName);

                // Permite o acesso ao campo mesmo que seja privado
                field.setAccessible(true);
                var value = field.get(object);

                if (value == null || value.toString().trim().isEmpty()) {
                    throw new ValidationException(fieldName + " cannot be null or empty.");
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            throw new ValidationException();
        }
    }
}
