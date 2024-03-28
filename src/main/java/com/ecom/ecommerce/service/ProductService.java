package com.ecom.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.ecommerce.dto.ProductDto;
import com.ecom.ecommerce.exception.CustomException;
import com.ecom.ecommerce.exception.ProductNotExistsException;
import com.ecom.ecommerce.model.Cart;
import com.ecom.ecommerce.model.Category;
import com.ecom.ecommerce.model.Product;
import com.ecom.ecommerce.model.WishList;
import com.ecom.ecommerce.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepository;

	@Autowired
	CartService cartService;

	@Autowired
	WishListService wishListService;

	public void createProduct(ProductDto productDto, Category category) {
		Product product = new Product();
		product.setDescription(productDto.getDescription());
		product.setImageURL(productDto.getImageURL());
		product.setName(productDto.getName());
		product.setCategory(category);
		product.setPrice(productDto.getPrice());
		productRepository.save(product);
	}

	public ProductDto getProductDto(Product product) {
		ProductDto productDto = new ProductDto();
		productDto.setDescription(product.getDescription());
		productDto.setImageURL(product.getImageURL());
		productDto.setName(product.getName());
		productDto.setCategoryId(product.getCategory().getId());
		productDto.setPrice(product.getPrice());
		productDto.setId(product.getId());
		return productDto;
	}

	public List<ProductDto> getAllProducts() {
		List<Product> allProducts = productRepository.findAll();

		List<ProductDto> productDtos = new ArrayList<>();
		for (Product product : allProducts) {
			productDtos.add(getProductDto(product));
		}
		return productDtos;
	}

	public void updateProduct(ProductDto productDto, Integer productId) throws Exception {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		// throw an exception if product does not exists
		if (!optionalProduct.isPresent()) {
			throw new Exception("product not present");
		}
		Product product = optionalProduct.get();
		product.setDescription(productDto.getDescription());
		product.setImageURL(productDto.getImageURL());
		product.setName(productDto.getName());
		product.setPrice(productDto.getPrice());
		productRepository.save(product);
	}

	public Product findById(Integer productId) throws ProductNotExistsException {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		if (optionalProduct.isEmpty()) {
			throw new ProductNotExistsException("product id is invalid: " + productId);
		}
		return optionalProduct.get();
	}

	public Product getProductById(Integer productId) {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		if (!optionalProduct.isPresent())
			throw new ProductNotExistsException("Product id is invalid " + productId);
		return optionalProduct.get();
	}

	public void deleteProduct(Integer productId) {

		Optional<Product> optionalProduct = productRepository.findById(productId);
		Cart CartProduct = cartService.findById(productId);
		WishList WishListProduct = wishListService.findById(productId);

		if (optionalProduct.isEmpty()) {
			throw new CustomException("Product id is not present: " + productId);
		}
		if (CartProduct != null) {
			cartService.deleteCartItem(CartProduct.getId(), CartProduct.getUser());
		}
		if (WishListProduct != null) {
			wishListService.deleteWishListItem(WishListProduct.getId(), WishListProduct.getUser());
		}
		Product product = optionalProduct.get();
		productRepository.delete(product);

	}


	public Product FindById(Integer categoryId) { 
		Optional <Product> optionalProduct = productRepository.findById(categoryId);
	  
	    if (optionalProduct.isEmpty()) { return null; }
	  
	    return optionalProduct.get();
	 }
	 

}
