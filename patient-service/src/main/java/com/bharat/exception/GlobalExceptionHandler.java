package com.bharat.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errorMap = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
		return ResponseEntity.badRequest().body(errorMap);
	}

	@ExceptionHandler(PatientNotFoundException.class)
	public ResponseEntity<Map<String, String>> handlePatientNotFoundException(PatientNotFoundException ex) {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("message", ex.getMessage());
		return ResponseEntity.badRequest().body(errorMap);
	}

	@ExceptionHandler(EmailAlreadyExistException.class)
	public ResponseEntity<Map<String, String>> handleEmailAlreadyExistException(EmailAlreadyExistException ex) {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("message", ex.getMessage());
		return ResponseEntity.badRequest().body(errorMap);
	}

}