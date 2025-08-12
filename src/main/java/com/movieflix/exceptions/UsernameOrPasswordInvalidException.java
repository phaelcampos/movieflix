package com.movieflix.exceptions;

public class UsernameOrPasswordInvalidException extends RuntimeException{

    public UsernameOrPasswordInvalidException(String message) {
        super(message);
    }
}
