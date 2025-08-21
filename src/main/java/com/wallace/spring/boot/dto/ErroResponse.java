package com.wallace.spring.boot.dto;

import java.time.LocalDateTime;

public record ErroResponse(LocalDateTime timestamp,  String message,String details) {
	
}