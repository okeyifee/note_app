package com.example.okeyifee.exception;

import com.example.okeyifee.payload.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

/**
 * RequestExceptionHandler
 */
@ControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(com.example.okeyifee.exception.CustomException.class)
    public ResponseEntity<Object> handleCustomException(com.example.okeyifee.exception.CustomException ce) {
        ApiResponse<?> ar = new ApiResponse<>(ce.getStatus());
        ar.setError(ce.getMessage());
        return buildResponseEntity(ar);
    }

     @ExceptionHandler(ConstraintViolationException.class)
     public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException cve) {
     	ApiResponse<?> ar = new ApiResponse<>(HttpStatus.BAD_REQUEST);
     	ar.addValidationErrors(cve.getConstraintViolations());
     	ar.setError("Validation Error");
     	return buildResponseEntity(ar);
     }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException iae) {
        ApiResponse<?> ar = new ApiResponse<>(HttpStatus.BAD_REQUEST);
        ar.setError(iae.getLocalizedMessage());
        return buildResponseEntity(ar);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException hmre, HttpHeaders headers, HttpStatus status,
                                                               WebRequest request) {
        ApiResponse<?> ar = new ApiResponse<>(HttpStatus.BAD_REQUEST);
        ar.setError("Validation Error: "+hmre.getMostSpecificCause().getLocalizedMessage());
        return buildResponseEntity(ar);
    }

    @Override
    public ResponseEntity<Object> handleBindException(BindException be, HttpHeaders headers, HttpStatus status,
                                                      WebRequest request) {
        ApiResponse<?> ar = new ApiResponse<>(HttpStatus.BAD_REQUEST);
        ar.addValidationErrors(be.getFieldErrors());
        ar.setError("Validation Error");
        return buildResponseEntity(ar);
    }

     @ExceptionHandler(UsernameNotFoundException.class)
     public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException e) {
     	ApiResponse<?> ar = new ApiResponse<>(HttpStatus.UNPROCESSABLE_ENTITY);
     	ar.setError(e.getMessage());
     	return buildResponseEntity(ar);
     }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException mx, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        ApiResponse<?> ar = new ApiResponse<>(HttpStatus.BAD_REQUEST);
        ar.addValidationError(mx.getBindingResult().getAllErrors());
        ar.setError("Validation Error");
        return buildResponseEntity(ar);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiResponse<?> apiResponse) {
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }
}
