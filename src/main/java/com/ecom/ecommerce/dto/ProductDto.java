package com.ecom.ecommerce.dto;

import javax.validation.constraints.NotEmpty;

public class ProductDto {
	   // for create it cane be optional , for update we need the id
	   private Integer id;
	   private @NotEmpty String name;
	   private @NotEmpty String imageURL;
	   private @NotEmpty double price;
	   private @NotEmpty String description;
	   private @NotEmpty Integer categoryId;
	   
	   
	public ProductDto() {
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	   
	   

}
