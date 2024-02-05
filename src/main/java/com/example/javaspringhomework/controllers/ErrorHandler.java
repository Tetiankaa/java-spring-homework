package com.example.javaspringhomework.controllers;

import com.example.javaspringhomework.dto.ErrorDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorDTO> handleValidationException(MethodArgumentNotValidException exception){

        BindingResult bindingResult = exception.getBindingResult();

        List<String> validationErrors = bindingResult.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorDTO.builder()
                        .timestamp(System.currentTimeMillis())
                        .details(validationErrors.toString())
                        .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintViolationException(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        List<String> validationErrors = violations.stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        validationErrors.forEach(System.err::println);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorDTO.builder()
                        .timestamp(System.currentTimeMillis())
                        .details(validationErrors.toString())
                        .build());
    }

}
