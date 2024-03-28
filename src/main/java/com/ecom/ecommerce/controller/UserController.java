package com.ecom.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecommerce.dto.ResponseDto;
import com.ecom.ecommerce.dto.user.SignInDto;
import com.ecom.ecommerce.dto.user.SigninResponseDto;
import com.ecom.ecommerce.dto.user.SignupDto;
import com.ecom.ecommerce.service.UserService;

@RequestMapping("user")
@RestController
public class UserController {
    
	@Autowired
	UserService userService;
	// two apis
	
	//signup
	
	@PostMapping("/signup")
	public ResponseDto signup(@RequestBody SignupDto signupDto) {
		return userService.signUp(signupDto);
	}
	
	//signin
	
	@PostMapping("/signin")
	public SigninResponseDto signIn(@RequestBody SignInDto signInDto) {
	     return userService.signIn(signInDto);
	}
	
	
}
