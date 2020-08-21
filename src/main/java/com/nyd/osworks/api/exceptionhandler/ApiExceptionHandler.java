package com.nyd.osworks.api.exceptionhandler;


import java.time.OffsetDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nyd.osworks.domain.exception.DomainException;
import com.nyd.osworks.domain.exception.EntityNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFound(DomainException ex, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		
		var issue = new Issues();
		issue.setStatus(status.value());
		issue.setTitle(ex.getMessage());
		issue.setDateHour(OffsetDateTime.now());
		
		
		return handleExceptionInternal(ex, issue, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(DomainException.class)
	public ResponseEntity<Object> handleDomain(DomainException ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		
		var issue = new Issues();
		issue.setStatus(status.value());
		issue.setTitle(ex.getMessage());
		issue.setDateHour(OffsetDateTime.now());
		
		
		return handleExceptionInternal(ex, issue, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var fields = new ArrayList<Field>();
		
		for(ObjectError error: ex.getBindingResult().getAllErrors()) {
			String name = ((FieldError) error).getField();
			String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			fields.add(new Field(name, message));
		}
		
		var issue = new Issues();
		issue.setStatus(status.value());
		issue.setTitle("One or more fields are invalid, fill in correctly and try again");
		issue.setDateHour(OffsetDateTime.now());
		issue.setFields(fields);
		
		
		
		return super.handleExceptionInternal(ex, issue , headers, status, request);
	}
	
}
