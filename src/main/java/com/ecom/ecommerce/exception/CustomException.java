package com.ecom.ecommerce.exception;

@SuppressWarnings("serial")
public class CustomException extends IllegalArgumentException {
	  
	public CustomException(String msg) {
	        super(msg);
	    }
}
