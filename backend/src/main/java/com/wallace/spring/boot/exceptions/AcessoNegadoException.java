package com.wallace.spring.boot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AcessoNegadoException extends DomainException {
    private static final long serialVersionUID = 1L;

    public AcessoNegadoException(String msg) {
        super(msg);
    }
}