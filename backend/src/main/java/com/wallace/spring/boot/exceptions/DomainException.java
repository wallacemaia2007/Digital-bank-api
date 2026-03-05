package com.wallace.spring.boot.exceptions;

public class DomainException extends RuntimeException {
 private static final long serialVersionUID = 1L;

 public DomainException(String msg) {
     super(msg);
 }
}