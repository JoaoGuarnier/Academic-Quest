package com.academicquest.components;

import org.springframework.validation.BindingResult;

public class Util {
	
    public static String errorHandling(String[] fields, BindingResult bindingResult) {
        StringBuilder errors = new StringBuilder();

        if (bindingResult.hasErrors()) {
            for (String field : fields) {
                if (bindingResult.hasFieldErrors(field)) {
                    errors.append(bindingResult.getFieldError(field).getDefaultMessage()).append("\n");
                }
            }
        }

        return errors.toString();
    }
}
