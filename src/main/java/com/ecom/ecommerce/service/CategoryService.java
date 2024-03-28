package com.ecom.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.ecommerce.exception.CustomException;
import com.ecom.ecommerce.model.Category;
import com.ecom.ecommerce.model.Product;
import com.ecom.ecommerce.repository.CategoryRepo;

@Service
public class CategoryService {

    @Autowired
    CategoryRepo categoryRepo;
    
    @Autowired
    ProductService productService;

    public void createCategory(Category category){
        categoryRepo.save(category);
    }
    
    public List<Category> listCategory() {
        return categoryRepo.findAll();
    }

	@SuppressWarnings("deprecation")
	public void editCategory(int categoryId, Category updateCategory) {
		Category category = categoryRepo.getById(categoryId);
		category.setCategoryName(updateCategory.getCategoryName());
		category.setDescription(updateCategory.getDescription());
		category.setImageUrl(updateCategory.getImageUrl());
		categoryRepo.save(category);
	}
	
	  public boolean findById(int categoryId) {
	        return categoryRepo.findById(categoryId).isPresent();
	    }
	  

	  public void deleteCategory(Integer categoryId) {
			
		  Optional<Category> optionalCategory = categoryRepo.findById(categoryId);
		  Product ProductCategory = productService.FindById(categoryId);
		
	      if (optionalCategory.isEmpty()) {
	          throw new CustomException("Category id is not present: " + categoryId);
	      }
			
			
			  if(ProductCategory != null) {
			  productService.deleteProduct(ProductCategory.getId());
			  }
			 
	    // productService.deleteProduct(ProductCategory.getId()); 
	     Category category  = optionalCategory.get(); 
	     categoryRepo.delete(category);
	      
		
	  }
	
}
