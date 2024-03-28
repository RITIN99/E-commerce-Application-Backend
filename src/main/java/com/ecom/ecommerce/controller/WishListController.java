package com.ecom.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecommerce.common.ApiResponse;
import com.ecom.ecommerce.dto.ProductDto;
import com.ecom.ecommerce.model.Product;
import com.ecom.ecommerce.model.User;
import com.ecom.ecommerce.model.WishList;
import com.ecom.ecommerce.service.AuthenticationService;
import com.ecom.ecommerce.service.WishListService;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

	@Autowired 
    WishListService wishListService;
	

    @Autowired
    AuthenticationService authenticationService;
	
	// save product as wishlist item
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product, @RequestParam("token") String token){
        // authenticate the token
    	  authenticationService.authenticate(token);

       // find the user

          User user = authenticationService.getUser(token);

      // save the item in wishlist
          
          WishList wishList = new WishList(user, product);

          wishListService.createWishlist(wishList);

          ApiResponse apiResponse = new ApiResponse(true, "Added to wishlist");
          return  new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
          
    }
    
   // get all wishlist item for a user

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) {

        // authenticate the token
        authenticationService.authenticate(token);


        // find the user

        User user = authenticationService.getUser(token);

        List<ProductDto> productDtos = wishListService.getWishListForUser(user);

        return new ResponseEntity<>(productDtos, HttpStatus.OK);

    }
    
    @DeleteMapping("/delete/{WishListItemId}")
    public ResponseEntity<ApiResponse> deleteWishList(@PathVariable("WishListItemId") Integer ItemId,@RequestParam("token") String token)  {
    	
    	
    	// authenticate the token
        authenticationService.authenticate(token);

        // find the user
        User user = authenticationService.getUser(token);

        wishListService.deleteWishListItem(ItemId, user);
    	
        return new ResponseEntity<>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);
    }
    
}
