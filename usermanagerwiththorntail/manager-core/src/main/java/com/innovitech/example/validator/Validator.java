package com.innovitech.example.validator;

import org.hibernate.exception.ConstraintViolationException;

public class Validator {

    public static boolean requiredValidator(Object... requiredObjcets) {
        for (Object s : requiredObjcets) {
            if (s == null) {
                return false;
            }
        }
        return true;
    }

    public static String exceptionHandler(Exception e) {
        if (e.getCause() != null) {
            if (e.getCause().getCause() != null) {
                if (e.getCause().getCause().getClass().equals(ConstraintViolationException.class)) {
                    return "unique exception";
                }
            }
        }
        return "fatal error";
    }
}
