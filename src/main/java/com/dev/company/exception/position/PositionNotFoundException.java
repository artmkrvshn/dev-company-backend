package com.dev.company.exception.position;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PositionNotFoundException extends RuntimeException {

    public PositionNotFoundException(String message) {
        super(message);
    }

    public PositionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PositionNotFoundException(Throwable cause) {
        super(cause);
    }
}
