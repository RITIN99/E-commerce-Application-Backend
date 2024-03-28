package com.ecom.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.Entity;

@SuppressWarnings("unused")
@ControllerAdvice
public class ExceptionControllerAdvice {
   
	@ExceptionHandler(value = CustomException.class)
	  public final 	ResponseEntity<String> handleCustomExcetion(CustomException exception){
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	 @ExceptionHandler(value = AuthenticationFailException.class)
	    public final ResponseEntity<String> handleAuthenticationFailException(AuthenticationFailException exception) {
	        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	 
	 @ExceptionHandler(value = ProductNotExistsException.class)
	    public final ResponseEntity<String> handleProductNotExistsException(ProductNotExistsException exception) {
	        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	    }
}
