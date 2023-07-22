package com.oluwaseun.airtimepayment.Exception;

public class DuplicateEntityException extends RuntimeException{
    public DuplicateEntityException() {
    }

    public DuplicateEntityException(String message) {
        super(message);
    }

}
