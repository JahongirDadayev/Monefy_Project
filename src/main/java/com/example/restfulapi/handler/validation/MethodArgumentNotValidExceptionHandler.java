package com.example.restfulapi.handler.validation;

import com.example.restfulapi.handler.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handler(MethodArgumentNotValidException exception) {
        List<Map<String, String>> errors = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            Map<String, String> error = new HashMap<>();
            error.put("field", fieldError.getField());
            error.put("error", fieldError.getDefaultMessage());
            errors.add(error);
        }
        Model model = new Model();
        model.setErrors(errors);
        model.setStatus(HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(model);
    }
}
