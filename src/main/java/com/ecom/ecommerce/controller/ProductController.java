package com.ecom.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecommerce.common.ApiResponse;
import com.ecom.ecommerce.dto.ProductDto;
import com.ecom.ecommerce.model.Category;
import com.ecom.ecommerce.repository.CategoryRepo;
import com.ecom.ecommerce.service.ProductService;


@RestController
@RequestMapping("/product")
public class ProductController {
   @Autowired
   ProductService productService;
   @Autowired
   CategoryRepo categoryRepo;
   
   @PostMapping("/add")
   public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto){
	   Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
	   if(!optionalCategory.isPresent()) {
		   return new ResponseEntity<ApiResponse>(new ApiResponse(false,"category does not exist"),	HttpStatus.BAD_REQUEST);
	   }
	   productService.createProduct(productDto, optionalCategory.get());
       return new ResponseEntity<ApiResponse>(new ApiResponse(true, "product has been added"), HttpStatus.CREATED);
   }
   
   @GetMapping("/")
   public ResponseEntity<List<ProductDto>> getProducts() {
       List<ProductDto> products = productService.getAllProducts();
       return new ResponseEntity<>(products, HttpStatus.OK);
   }

   // create an api to edit the product
   
   @PutMapping("/update/{productId}")
   public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Integer productId, @RequestBody ProductDto productDto) throws Exception {
       Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
       if (!optionalCategory.isPresent()) {
           return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category does not exists"), HttpStatus.BAD_REQUEST);
       }
       productService.updateProduct(productDto, productId);
       return new ResponseEntity<ApiResponse>(new ApiResponse(true, "product has been updated"), HttpStatus.OK);
   }
   
   @DeleteMapping("/delete/{productId}")
   public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") Integer productId){
	   productService.deleteProduct(productId);
	   return new ResponseEntity<>(new ApiResponse(true, "Product has been removed successfully"), HttpStatus.OK);
   }
   
   
}
