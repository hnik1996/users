package com.ecommerce.users.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class SaveUserException extends RuntimeException{
    public SaveUserException() {
        super();
    }

    public SaveUserException(String message) {
        super(message);
    }
}
