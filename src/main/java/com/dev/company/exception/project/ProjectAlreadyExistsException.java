package com.dev.company.exception.project;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectAlreadyExistsException extends RuntimeException {

    public ProjectAlreadyExistsException(String message) {
        super(message);
    }

    public ProjectAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
