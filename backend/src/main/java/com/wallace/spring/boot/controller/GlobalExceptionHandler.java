package com.wallace.spring.boot.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.wallace.spring.boot.dto.ErroResponse;
import com.wallace.spring.boot.exceptions.AcessoNegadoException;
import com.wallace.spring.boot.exceptions.ClienteNaoEncontradoException;
import com.wallace.spring.boot.exceptions.ContaInexistenteException;
import com.wallace.spring.boot.exceptions.ContaJaExistenteException;
import com.wallace.spring.boot.exceptions.CpfInvalidoException;
import com.wallace.spring.boot.exceptions.CpfJaExistenteException;
import com.wallace.spring.boot.exceptions.CredenciaisInvalidasException;
import com.wallace.spring.boot.exceptions.DataInvalidaException;
import com.wallace.spring.boot.exceptions.EmailJaExistenteException;
import com.wallace.spring.boot.exceptions.EmailNaoEncontradoException;
import com.wallace.spring.boot.exceptions.SaldoInsuficienteException;
import com.wallace.spring.boot.exceptions.TipoDeContaInvalidaException;
import com.wallace.spring.boot.exceptions.UsuarioNaoEncontradoException;
import com.wallace.spring.boot.exceptions.ValorMenorQueZeroException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(AcessoNegadoException.class)
	public ResponseEntity<ErroResponse> handleAcessoNegadoException(AcessoNegadoException ex,
			WebRequest request) {
		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		logger.warn("AcessoNegadoException: {}", ex.getMessage());
		return new ResponseEntity<>(erroResponse, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErroResponse> handleAccessDeniedException(AccessDeniedException ex,
			WebRequest request) {
		String customMessage = "Acesso negado: Você não possui as permissões necessárias para executar esta operação. " +
				"Contate um administrador se acredita que deveria ter acesso.";
		
		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), customMessage,
				request.getDescription(false));
		logger.warn("AccessDeniedException: {}", ex.getMessage());
		return new ResponseEntity<>(erroResponse, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(ClienteNaoEncontradoException.class)
	public ResponseEntity<ErroResponse> handleClienteNaoEncontradoException(ClienteNaoEncontradoException ex,
			WebRequest request) {
		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		logger.error("ClienteNaoEncontradoException: {}", ex.getMessage());
		return new ResponseEntity<>(erroResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ValorMenorQueZeroException.class)
	public ResponseEntity<ErroResponse> handleValorMenorQueZeroException(ValorMenorQueZeroException ex,
			WebRequest request) {
		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		logger.warn("ValorMenorQueZeroException: {}", ex.getMessage());
		return new ResponseEntity<>(erroResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(EmailJaExistenteException.class)
	public ResponseEntity<ErroResponse> handleEmailJaExistenteException(EmailJaExistenteException ex, WebRequest request) {
	    ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
	            request.getDescription(false));
	    logger.warn("EmailJaExistenteException: {}", ex.getMessage());
	    return new ResponseEntity<>(erroResponse, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(UsuarioNaoEncontradoException.class)
	public ResponseEntity<ErroResponse> handleUsuarioNaoEncontradoException(UsuarioNaoEncontradoException ex,
			WebRequest request) {
		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		logger.error("UsuarioNaoEncontradoException: {}", ex.getMessage());
		return new ResponseEntity<>(erroResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(TipoDeContaInvalidaException.class)
	public ResponseEntity<ErroResponse> handleTipoDeContaInvalidaException(TipoDeContaInvalidaException ex,
			WebRequest request) {
		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		logger.warn("TipoDeContaInvalidaException: {}", ex.getMessage());
		return new ResponseEntity<>(erroResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(SaldoInsuficienteException.class)
	public ResponseEntity<ErroResponse> handleSaldoInsuficienteException(SaldoInsuficienteException ex,
			WebRequest request) {
		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		logger.warn("SaldoInsuficienteException: {}", ex.getMessage());
		return new ResponseEntity<>(erroResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(DataInvalidaException.class)
	public ResponseEntity<ErroResponse> handleDataInvalidaException(DataInvalidaException ex, WebRequest request) {
		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		logger.warn("DataInvalidaException: {}", ex.getMessage());
		return new ResponseEntity<>(erroResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CpfJaExistenteException.class)
	public ResponseEntity<ErroResponse> handleCpfJaExistenteException(CpfJaExistenteException ex, WebRequest request) {
		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		logger.warn("CpfJaExistenteException: {}", ex.getMessage());
		return new ResponseEntity<>(erroResponse, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ContaJaExistenteException.class)
	public ResponseEntity<ErroResponse> handleContaJaExistenteException(ContaJaExistenteException ex,
			WebRequest request) {
		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		logger.warn("ContaJaExistenteException: {}", ex.getMessage());
		return new ResponseEntity<>(erroResponse, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ContaInexistenteException.class)
	public ResponseEntity<ErroResponse> handleContaInexistenteException(ContaInexistenteException ex,
			WebRequest request) {
		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		logger.error("ContaInexistenteException: {}", ex.getMessage());
		return new ResponseEntity<>(erroResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CpfInvalidoException.class)
	public ResponseEntity<ErroResponse> handleCpfInvalidoException(CpfInvalidoException ex, WebRequest request) {
		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		logger.warn("CpfInvalidoException: {}", ex.getMessage());
		return new ResponseEntity<>(erroResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErroResponse> handleGenericException(Exception ex, WebRequest request) {
		ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), "Erro interno do servidor",
				request.getDescription(false));
		logger.error("Erro interno inesperado: ", ex);
		return new ResponseEntity<>(erroResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CredenciaisInvalidasException.class)
	public ResponseEntity<ErroResponse> handleCredenciaisInvalidasException(CredenciaisInvalidasException ex, WebRequest request) {
	    ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
	            request.getDescription(false));
	    logger.warn("CredenciaisInvalidasException: {}", ex.getMessage());
	    return new ResponseEntity<>(erroResponse, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(EmailNaoEncontradoException.class)
	public ResponseEntity<ErroResponse> handleEmailNaoEncontradoException(EmailNaoEncontradoException ex, WebRequest request) {
	    ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), ex.getMessage(),
	            request.getDescription(false));
	    logger.warn("EmailNaoEncontradoException: {}", ex.getMessage());
	    return new ResponseEntity<>(erroResponse, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErroResponse> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
	    ErroResponse erroResponse = new ErroResponse(LocalDateTime.now(), 
	            "Email ou senha incorretos.", request.getDescription(false));
	    logger.warn("BadCredentialsException: {}", ex.getMessage());
	    return new ResponseEntity<>(erroResponse, HttpStatus.UNAUTHORIZED);
	}
}