package com.hongikbros.jobmanager.common.core.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyFieldsValidator implements ConstraintValidator<NotEmptyFields, List<String>> {

    @Override
    public void initialize(NotEmptyFields constraint) {
        throw new UnsupportedOperationException();
    }

    public boolean isValid(List<String> obj, ConstraintValidatorContext context) {
        if (!obj.isEmpty()) {
            return obj.stream().allMatch(nef -> nef != null && !nef.trim().isEmpty());
        }
        return true;
    }

}
