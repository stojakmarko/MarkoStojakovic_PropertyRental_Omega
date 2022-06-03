package com.example.propertyrental.exception;


import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(value = {NotFoundPropertyException.class})
    protected ResponseEntity<Object> handleEntityNotFound(
            NotFoundPropertyException ex) {
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage("Not found property");
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    protected ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex) {
        ApiError apiError = new ApiError(UNAUTHORIZED);
        apiError.setMessage("Bad credentials!");
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }


    @ExceptionHandler(value = {AuthenticationException.class})
    protected ResponseEntity<Object> handleAuthentication(AuthenticationException ex) {
        ApiError apiError = new ApiError(UNAUTHORIZED);
        apiError.setMessage("Bad credentials!");
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(value = {JwtException.class})
    public ResponseEntity<Object> handleAccessDeniedException(JwtException ex) {
        ApiError apiError = new ApiError(UNAUTHORIZED);
        apiError.setMessage("Not authorized!");
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }


    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }


}
