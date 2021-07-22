package com.example.amazon.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.amazon.dto.Credentials;
import com.example.amazon.dto.UserRequestDto;
import com.example.amazon.exception.InvalidCredentialsException;
import com.example.amazon.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

 
	//given or context
	//on a event
	//what is the outcome
	
	@Mock
	UserService userService;
	
	@InjectMocks
	UserController userController;
	
	static UserRequestDto userRequestDto;
	
	static Credentials credentials;
	
	@BeforeAll
	public static void setUp() {
		userRequestDto = new UserRequestDto();
		userRequestDto.setUserName("Anu");
		userRequestDto.setPassword("1234");
		userRequestDto.setMobileNo("+91 9875676786");
		userRequestDto.setMailId("anu@gmail.com");
		
		credentials = new Credentials();
		credentials.setUsername("Deeraj");
		credentials.setPassword("4567");
	}
	
	@Test
	@DisplayName("Reg Function: Positive Scenario")
	public void registerTest() {
		//given or context
		when(userService.saveUserDetails(userRequestDto)).thenReturn(true);
		
		//when or event
		String result = userController.register(userRequestDto);
		
		//then or outcome
		verify(userService).saveUserDetails(userRequestDto);
		assertEquals("Registration Successful", result);
	}
	
	@Test
	@DisplayName("Reg Function: Negative Scenario")
	public void registerTest1() {
		//given or context
		when(userService.saveUserDetails(userRequestDto)).thenReturn(false);
		
		//when or event
		String result = userController.register(userRequestDto);
		
		//then or outcome
		verify(userService).saveUserDetails(userRequestDto);
		assertEquals("Registration failed", result);
	}
	
	@Test
	@DisplayName("Login Function: Positive Scenario")
	public void loginTest() {
		//context
		when(userService.authenticate("Deeraj", "4567")).thenReturn(
				new ResponseEntity<>("Login success", HttpStatus.OK));
		
		//event
		ResponseEntity<String> result = userController.login(credentials);
		
		//outcome
		assertEquals("Login success", result.getBody());
	}
	
	@Test
	@DisplayName("Login Function: Negative Scenario")
	public void loginTest2() {
		//context
		when(userService.authenticate("Deeraj", "4567")).thenThrow(InvalidCredentialsException.class);
		
		//event
		//outcome
		assertThrows(InvalidCredentialsException.class, ()->userController.login(credentials));
	}
}
