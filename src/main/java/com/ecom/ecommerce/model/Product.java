package com.ecom.ecommerce.model;

import javax.validation.constraints.NotEmpty;

/*import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
*/
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
  
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
   

   private @NotEmpty String name;
   private @NotEmpty String imageURL;
   private @NotEmpty double price;
   private @NotEmpty String description;
   
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "category_id", nullable = false)
   Category category;	
   
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
public Category getCategory() {
	return category;
}
public void setCategory(Category category) {
	this.category = category;
}

public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
   
}
