package kz.justdika.service_center.controller;

import kz.justdika.service_center.exception.ParamNotCorrectException;
import kz.justdika.service_center.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(ParamNotCorrectException.class)
    public ResponseEntity<ErrorResponse> handleException(ParamNotCorrectException ex){
        var errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

