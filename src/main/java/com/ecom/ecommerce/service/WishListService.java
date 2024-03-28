package com.ecom.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.ecommerce.dto.ProductDto;
import com.ecom.ecommerce.exception.CustomException;
import com.ecom.ecommerce.model.User;
import com.ecom.ecommerce.model.WishList;
import com.ecom.ecommerce.repository.WishListRepository;

@Service
public class WishListService {
 
	@Autowired
    WishListRepository wishListRepository;
	
	@Autowired
	ProductService productService;

	public void createWishlist(WishList wishList) {
		wishListRepository.save(wishList);
	}
	
    public List<ProductDto> getWishListForUser(User user) {
        final List<WishList> wishLists = wishListRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<ProductDto> productDtos = new ArrayList<>();
        for (WishList wishList: wishLists) {
            productDtos.add(productService.getProductDto(wishList.getProduct()));
        }

        return productDtos;
    }

	public  void deleteWishListItem(Integer ItemId, User user) {
		
		Optional<WishList> optionalWishList = wishListRepository.findById(ItemId);

        if (optionalWishList.isEmpty()) {
            throw new CustomException("WishList item id is invalid: " + ItemId);
        }
        	
        WishList wishList = optionalWishList.get();

        if (wishList.getUser() != user) {
            throw  new CustomException("WishList item does not belong to user: " +ItemId);
        }

        wishListRepository.delete(wishList);
		
		
	}
	

	public WishList findById(Integer productId) {
		 Optional<WishList> optionalwishlist = wishListRepository.findById(productId);
	      if (optionalwishlist.isEmpty()) {
	          return null;
	      }
	      return optionalwishlist.get();
	}
	
	
}
