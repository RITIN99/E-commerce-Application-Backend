package com.ecom.ecommerce.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.ecommerce.dto.ResponseDto;
import com.ecom.ecommerce.dto.user.SignInDto;
import com.ecom.ecommerce.dto.user.SigninResponseDto;
import com.ecom.ecommerce.dto.user.SignupDto;
import com.ecom.ecommerce.exception.AuthenticationFailException;
import com.ecom.ecommerce.exception.CustomException;
import com.ecom.ecommerce.model.AuthenticationToken;
import com.ecom.ecommerce.model.User;
import com.ecom.ecommerce.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.xml.bind.DatatypeConverter;

@Service
public class UserService {
    
	@Autowired
	 UserRepository userRepository;
	
    @Autowired
    AuthenticationService authenticationService;
	
    @Transactional
	public ResponseDto signUp(SignupDto signupDto) {
		// check if  user is already present 
		if(Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))) {
			// we have an user
			throw new CustomException("user already present");
		}
		
		// hash the password
		
		String encryptedpassword = signupDto.getPassword();
		
		 try {
	            encryptedpassword = hashPassword(signupDto.getPassword());
	        }catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
    
		 // save the user
		 
		 User user = new User(signupDto.getFirstName(),signupDto.getLastName(),signupDto.getEmail(),encryptedpassword);
	
		  userRepository.save(user);
		 
		 // create the token 
		  
		  final AuthenticationToken authenticationToken = new AuthenticationToken(user);
		   authenticationService.saveConfirmationToken(authenticationToken);
		 
	 ResponseDto responseDto = new ResponseDto("success", "user created successfully");
	 return responseDto;
	}

	private String hashPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("Md5");
		md.update(password.getBytes());
		byte[] digest = md.digest();
		String hash = DatatypeConverter
	                .printHexBinary(digest).toUpperCase();
	    return hash;
	}

	public SigninResponseDto signIn(SignInDto signInDto) {
		// find user by email
		
		User user = userRepository.findByEmail(signInDto.getEmail());
		
		if(Objects.isNull(user)) {
			  throw new AuthenticationFailException("user is not valid");
		}
		
		// hash the password
		
		 try {
	            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
	                throw new AuthenticationFailException("wrong password");
	            }
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
		 
		  // compare the password in DB

	        // if password match
		 
		 AuthenticationToken token = authenticationService.getToken(user);
		 
		 // retrive the token 
		 
		 if (Objects.isNull(token)) {
	            throw new CustomException("token is not present");
	        }
		 
		 // return response 
		 
		 return new SigninResponseDto("sucess", token.getToken());
		
	}
    
}
