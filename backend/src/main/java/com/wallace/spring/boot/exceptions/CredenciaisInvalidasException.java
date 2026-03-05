package com.wallace.spring.boot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CredenciaisInvalidasException extends DomainException {
    private static final long serialVersionUID = 1L;

    public CredenciaisInvalidasException(String msg) {
        super(msg);
    }
}