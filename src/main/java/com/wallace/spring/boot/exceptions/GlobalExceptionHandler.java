package com.wallace.spring.boot.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.wallace.spring.boot.dto.ErroResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ClienteNaoEncontradoException.class)
	public ResponseEntity<ErroResponse> handleClienteNaoEncontradoException(ClienteNaoEncontradoException ex,
			WebRequest request) {

		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(erroResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserEmailNaoEncontradoException.class)
	public ResponseEntity<ErroResponse> UserEmailNaoEncontradoException(UserEmailNaoEncontradoException ex,
			WebRequest request) {

		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(erroResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ValorMenorQueZeroException.class)
	public ResponseEntity<ErroResponse> handleValorMenorQueZeroException(ValorMenorQueZeroException ex,
			WebRequest request) {

		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(erroResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UsuarioNaoEncontradoException.class)
	public ResponseEntity<ErroResponse> handleUsuarioNaoEncontradoException(UsuarioNaoEncontradoException ex,
			WebRequest request) {

		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(erroResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(TipoDeContaInvalidaException.class)
	public ResponseEntity<ErroResponse> handleTipoDeContaInvalidaException(TipoDeContaInvalidaException ex,
			WebRequest request) {

		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(erroResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(SaldoInsuficienteException.class)
	public ResponseEntity<ErroResponse> handleSaldoInsuficienteException(SaldoInsuficienteException ex,
			WebRequest request) {

		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(erroResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(DataInvalidaException.class)
	public ResponseEntity<ErroResponse> handleDataInvalidaException(DataInvalidaException ex, WebRequest request) {

		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(erroResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CpfJaExistenteException.class)
	public ResponseEntity<ErroResponse> handleCpfJaExistenteException(CpfJaExistenteException ex, WebRequest request) {

		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(erroResponse, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ContaJaExistenteException.class)
	public ResponseEntity<ErroResponse> handleContaJaExistenteException(ContaJaExistenteException ex,
			WebRequest request) {

		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(erroResponse, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ContaInexistenteException.class)
	public ResponseEntity<ErroResponse> handleContaInexistenteException(ContaInexistenteException ex,
			WebRequest request) {

		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(erroResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CpfInvalidoException.class)
	public ResponseEntity<ErroResponse> handleCpfInvalidoException(CpfInvalidoException ex, WebRequest request) {

		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(erroResponse, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErroResponse> handleGenericException(Exception ex, WebRequest request) {

		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), "Erro interno do servidor",
				request.getDescription(false));

		return new ResponseEntity<>(erroResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
