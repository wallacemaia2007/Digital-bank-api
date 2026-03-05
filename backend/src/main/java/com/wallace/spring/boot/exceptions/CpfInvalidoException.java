package com.wallace.spring.boot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CpfInvalidoException extends DomainException {
	
	private static final long serialVersionUID = 1L;

	public CpfInvalidoException(String msg) {
		super(msg);
	}

}
