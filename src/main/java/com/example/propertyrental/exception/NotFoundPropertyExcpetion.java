package com.example.propertyrental.exception;

public class NotFoundPropertyExcpetion extends RuntimeException{

    public NotFoundPropertyExcpetion() {
        super();
    }

    public NotFoundPropertyExcpetion(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
