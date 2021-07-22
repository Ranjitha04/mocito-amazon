package com.example.amazon.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.amazon.dto.Credentials;
import com.example.amazon.dto.UserRequestDto;
import com.example.amazon.service.UserService;

/**
 * @author ranjitha-r
 *
 */

@RestController
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("/users")
	public String register(@RequestBody UserRequestDto userRequestDto) {	
		
		
		boolean isSaved = userService.saveUserDetails(userRequestDto);
        if(isSaved) return "Registration Successful";
		return "Registration failed";
	}
	
	@PostMapping("/users/login")
	public ResponseEntity<String> login(@Valid @RequestBody Credentials credentials) {
		return userService.authenticate(credentials.getUsername(), credentials.getPassword());
	}
}
