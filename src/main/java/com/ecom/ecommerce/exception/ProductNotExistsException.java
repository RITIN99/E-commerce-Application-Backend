package com.ecom.ecommerce.exception;

@SuppressWarnings("serial")
public class ProductNotExistsException extends IllegalArgumentException  {

	  public ProductNotExistsException(String msg) {
	        super(msg);
	    }
	
}
