package com.dev.company.exception;

import com.dev.company.exception.department.DepartmentAlreadyExistsException;
import com.dev.company.exception.department.DepartmentNotFoundException;
import com.dev.company.exception.employee.EmployeeNotFoundException;
import com.dev.company.exception.position.PositionAlreadyExistsException;
import com.dev.company.exception.position.PositionNotFoundException;
import com.dev.company.exception.project.ProjectAlreadyExistsException;
import com.dev.company.exception.project.ProjectNotFoundException;
import com.dev.company.exception.status.StatusAlreadyExistsException;
import com.dev.company.exception.status.StatusNotFoundException;
import com.mysql.cj.jdbc.exceptions.SQLError;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Log4j2
@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(SQLException.class)
    public ErrorResponse handleSQLException(SQLException ex) {
        return ErrorResponse.create(ex, BAD_REQUEST, ex.getMessage());
    }

//    @ExceptionHandler(DuplicateKeyException.class)
//    public ErrorResponse handleDuplicateKeyException(DuplicateKeyException ex) {
//        return ErrorResponse.create(ex, BAD_REQUEST, ex.getMessage());
//    }


//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
//        return ErrorResponse.create(ex, BAD_REQUEST, ex.getMessage());
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ErrorResponse.builder(ex, BAD_REQUEST, ex.getMessage())
                .property("errors", errors)
                .build();
    }

    @ExceptionHandler(value = {
            DepartmentNotFoundException.class,
            EmployeeNotFoundException.class,
            PositionNotFoundException.class,
            ProjectNotFoundException.class,
            StatusNotFoundException.class})
    public ErrorResponse handleNotFoundException(RuntimeException ex) {
        return ErrorResponse.create(ex, NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(value = {
            DepartmentAlreadyExistsException.class,
            PositionAlreadyExistsException.class,
            ProjectAlreadyExistsException.class,
            StatusAlreadyExistsException.class,
    })
    public ErrorResponse handleAlreadyExistsException(RuntimeException ex) {
        return ErrorResponse.create(ex, BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ErrorResponse.create(ex, BAD_REQUEST, ex.getMessage());
    }

//    @ExceptionHandler({DepartmentNotFoundException.class, EmployeeNotFoundException.class, PositionNotFoundException.class, ProjectNotFoundException.class, StatusNotFoundException.class})
//    public ResponseEntity<DefaultErrorAttributes> handleNotFound() {
//
//
//
//        return ResponseEntity.of();
//    }

//    @ExceptionHandler(RuntimeException.class)
//    public void handleAll(RuntimeException ex) {
//        System.out.println(ex.toString());
//
//        ErrorDetail errorDetail = ErrorDetail.builder()
//                .title("Department Already Exists")
//                .status(BAD_REQUEST)
//                .detail("")
//                .timestamp(new Date().getTime())
//                .message("")
//                .build();
//        return new ResponseEntity<>(errorDetail, null, BAD_REQUEST);
//    }


/*    @ExceptionHandler(DepartmentAlreadyExistsException.class)
    public ResponseEntity<ErrorDetail> handleDepartmentAlreadyExistsException(DepartmentAlreadyExistsException ex) {
//        HttpStatus status = BAD_REQUEST;
//        return ErrorResponse.builder(ex, status, ex.getMessage())
////                .type(status)
//                .build();
        ErrorDetail errorDetail = ErrorDetail.builder()
                .title("Department Already Exists")
                .status(BAD_REQUEST)
                .detail(ex.getMessage())
                .timestamp(new Date().getTime())
                .message(ex.getClass().getName())
                .build();
        return new ResponseEntity<>(errorDetail, null, BAD_REQUEST);
    }*/

/*
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ErrorResponse.builder(ex, BAD_REQUEST, ex.getMessage())
                .property("errors", errors)
                .build();
    }

//    @ExceptionHandler(DepartmentNotFoundException.class)
//    public ErrorResponse handleDepartmentNotFoundException(DepartmentNotFoundException ex) {
//        HttpStatus status = NOT_FOUND;
//        return ErrorResponse.builder(ex, status, ex.getMessage())
//                .type(getType(status))
//                .build();
//    }

//    @ExceptionHandler(DepartmentNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleDepartmentNotFoundException(DepartmentNotFoundException ex) {
//        HttpStatus status = NOT_FOUND;
////        return ErrorResponse.builder(ex, status, ex.getMessage())
////                .type(getType(status))
////                .build();
//        ErrorResponse errorResponse = ErrorResponse.create(ex, status, ex.getMessage());
//        return new ResponseEntity<>(errorResponse, status);
//    }



    @ExceptionHandler(PositionNotFoundException.class)
    public ErrorResponse handlePositionNotFoundException(PositionNotFoundException ex) {
        HttpStatus status = NOT_FOUND;
        return ErrorResponse.builder(ex, status, ex.getMessage())
                .type(getType(status))
                .build();
    }

    private URI getType(HttpStatus status) {
        String type = "/api/v1/errors/%s".formatted(status.value());
        return URI.create(type);
    }
*/
}
