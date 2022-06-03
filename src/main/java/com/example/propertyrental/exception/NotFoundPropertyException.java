package com.example.propertyrental.exception;

public class NotFoundPropertyException extends RuntimeException {

    public NotFoundPropertyException() {
        super();
    }

    public NotFoundPropertyException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
