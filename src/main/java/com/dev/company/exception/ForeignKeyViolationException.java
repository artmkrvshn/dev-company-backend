package com.dev.company.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ForeignKeyViolationException extends RuntimeException {

    public ForeignKeyViolationException(String message) {
        super(message);
    }

    public ForeignKeyViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForeignKeyViolationException(Throwable cause) {
        super(cause);
    }
}
