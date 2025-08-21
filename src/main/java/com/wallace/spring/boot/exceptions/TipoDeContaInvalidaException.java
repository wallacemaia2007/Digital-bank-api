package com.wallace.spring.boot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TipoDeContaInvalidaException extends DomainException {
private static final long serialVersionUID = 1L;

	public TipoDeContaInvalidaException(String msg) {
		super(msg);
	}

}
