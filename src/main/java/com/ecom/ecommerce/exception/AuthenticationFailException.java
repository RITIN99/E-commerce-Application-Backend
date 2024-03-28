package com.ecom.ecommerce.exception;

@SuppressWarnings("serial")
public class AuthenticationFailException extends IllegalArgumentException {

	public AuthenticationFailException(String msg){
	        super(msg);
	    }
}
