package com.example.amazon.service;

import org.springframework.http.ResponseEntity;

import com.example.amazon.dto.UserRequestDto;
import com.example.amazon.model.User;

public interface UserService {

	boolean saveUserDetails(UserRequestDto userRequestDto);

	ResponseEntity<String> authenticate(String username, String password);

}
