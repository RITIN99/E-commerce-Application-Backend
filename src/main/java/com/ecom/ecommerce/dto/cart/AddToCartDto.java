package com.ecom.ecommerce.dto.cart;

import javax.validation.constraints.NotEmpty;

public class AddToCartDto {
	
	 private Integer id;
	 private @NotEmpty Integer productId;
	 private @NotEmpty Integer quantity;
     
	 public AddToCartDto() {
	    }

	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }

	    public Integer getProductId() {
	        return productId;
	    }

	    public void setProductId(Integer productId) {
	        this.productId = productId;
	    }

	    public Integer getQuantity() {
	        return quantity;
	    }

	    public void setQuantity(Integer quantity) {
	        this.quantity = quantity;
	    }
	    
}
