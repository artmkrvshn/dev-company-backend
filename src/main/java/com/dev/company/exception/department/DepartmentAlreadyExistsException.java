package com.dev.company.exception.department;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DepartmentAlreadyExistsException extends RuntimeException {

    public DepartmentAlreadyExistsException(String message) {
        super(message);
    }

    public DepartmentAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepartmentAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
