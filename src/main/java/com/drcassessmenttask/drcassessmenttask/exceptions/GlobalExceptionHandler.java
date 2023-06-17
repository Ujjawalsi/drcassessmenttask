package com.drcassessmenttask.drcassessmenttask.exceptions;

import com.drcassessmenttask.drcassessmenttask.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException exception){

        String exceptionMessage = exception.getMessage();
        ApiResponse apiResponse = new ApiResponse(exceptionMessage,false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException ex){
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<Map<String,String>> handleMethodArgsNotValidException(MethodArgumentNotValidException exception){
        Map<String,String> resp = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName=((FieldError)error).getField();
            String message = error.getDefaultMessage();
            resp.put(fieldName,message);
        });
        return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String,String>> requestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception){
        Map<String , String> resp = new HashMap<>();
        String message = exception.getMessage();
        String method = exception.getMethod();
        resp.put(message,method);

        return new ResponseEntity<>(resp, HttpStatus.METHOD_NOT_ALLOWED);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String,String>> handleNullPointerException(NullPointerException exception){
        Map<String , String> resp = new HashMap<>();
        String message = exception.getMessage();
        Throwable cause = exception.getCause();

        resp.put(message, cause.getMessage());

        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }

}
