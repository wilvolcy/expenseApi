package com.datts.expenses.exceptionHandeler;

import com.datts.expenses.entity.ErrorObj;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorObj> exceptionHandlerMethod(ResourceNotFoundException exception, WebRequest request){
        ErrorObj errorObj = new ErrorObj();
        errorObj.setMessage(exception.getMessage());
        errorObj.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObj.setTimeStamp(new Date());
        return new ResponseEntity<>(errorObj,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorObj> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException exception, WebRequest request){
        ErrorObj errorObj = new ErrorObj();
        errorObj.setMessage(exception.getMessage());
        errorObj.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObj.setTimeStamp(new Date());
        return new ResponseEntity<>(errorObj,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorObj> generalExceptionHandler(Exception exception, WebRequest request){
        ErrorObj errorObj = new ErrorObj();
        errorObj.setMessage(exception.getMessage());
        errorObj.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorObj.setTimeStamp(new Date());
        return new ResponseEntity<>(errorObj,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("statusCode",HttpStatus.BAD_REQUEST.value());
        body.put("timeStamp",new Date());
        List<String> error = ex.getBindingResult().getFieldErrors()
                .stream().map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        body.put("message", error);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
