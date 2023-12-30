package com.dev.company.exception.position;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PositionAlreadyExistsException extends RuntimeException {

    public PositionAlreadyExistsException(String message) {
        super(message);
    }

    public PositionAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public PositionAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
