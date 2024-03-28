package com.ecom.ecommerce.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.ecommerce.dto.cart.AddToCartDto;
import com.ecom.ecommerce.dto.cart.CartDto;
import com.ecom.ecommerce.dto.cart.CartItemDto;
import com.ecom.ecommerce.exception.CustomException;
import com.ecom.ecommerce.exception.ProductNotExistsException;
import com.ecom.ecommerce.model.Cart;
import com.ecom.ecommerce.model.Product;
import com.ecom.ecommerce.model.User;
import com.ecom.ecommerce.repository.CartRepository;


@SuppressWarnings("unused")
@Service
public class CartService {
	
	  @Autowired
	  CartRepository cartRepository;
	  
	  @Autowired
	  ProductService productService;

	  
	public void addToCart(AddToCartDto addToCartDto, User user) {
		 Product product = productService.findById(addToCartDto.getProductId());
		 
		    Cart cart = new Cart();
	        cart.setProduct(product);
	        cart.setUser(user);
	        cart.setQuantity(addToCartDto.getQuantity());
	        cart.setCreatedDate(new Date());


	        // save the cart
	        cartRepository.save(cart);
	}

	public CartDto listCartItems(User user) {
		List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
		
		 List<CartItemDto> cartItems = new ArrayList<>();
	     double totalCost = 0;
	     
	     for (Cart cart: cartList) {
	            CartItemDto cartItemDto = new CartItemDto(cart);
	            totalCost += cartItemDto.getQuantity() * cart.getProduct().getPrice();
	            cartItems.add(cartItemDto);
	        }
	     
	        CartDto cartDto = new CartDto();
	        cartDto.setTotalCost(totalCost);
	        cartDto.setCartItems(cartItems);
	        return  cartDto;
	}

	public void deleteCartItem(Integer cartItemId, User user) {
		 // the item id belongs to user

        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);

        if (optionalCart.isEmpty()) {
            throw new CustomException("cart item id is invalid: " + cartItemId);
        }
        	
        Cart cart = optionalCart.get();

        if (cart.getUser() != user) {
            throw  new CustomException("cart item does not belong to user: " +cartItemId);
        }

        cartRepository.delete(cart);
		
	}

	@SuppressWarnings("deprecation")
	public void updateCartItem(AddToCartDto cartDto, User user, Product product) {
		    Cart cart = cartRepository.getOne(cartDto.getId());
	        cart.setQuantity(cartDto.getQuantity());
	        cart.setCreatedDate(new Date());
	        cartRepository.save(cart);	
	}

	public Cart findById(Integer productId) {
		 Optional<Cart> optionalcart = cartRepository.findById(productId);
	      if (optionalcart.isEmpty()) {
	          return null;
	      }
	      return optionalcart.get();
	}


}
