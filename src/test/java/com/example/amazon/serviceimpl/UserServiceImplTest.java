package com.example.amazon.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.amazon.dao.UserDao;
import com.example.amazon.dto.Credentials;
import com.example.amazon.dto.UserRequestDto;
import com.example.amazon.exception.InvalidCredentialsException;
import com.example.amazon.model.User;
import com.example.amazon.service.impl.UserServiceImpl;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

	@Mock
	UserDao userDao;
	
	@InjectMocks
	UserServiceImpl userServiceImpl;
	
    static UserRequestDto userRequestDto;
    static User user;
	static User userPerst;
	@BeforeAll
	public static void setUp() {
		userRequestDto = new UserRequestDto();
		userRequestDto.setUserName("Anu");
		userRequestDto.setPassword("1234");
		userRequestDto.setMobileNo("+91 9875676786");
		userRequestDto.setMailId("anu@gmail.com");
		
		user = new User();
		user.setUserName("Anu");
		user.setPassword("1234");
		user.setMobileNo("+91 9875676786");
		user.setMailId("anu@gmail.com");
		
	}
	
	@Test
	@DisplayName("Save UserDetails")
	public void saveUserDetailsTest() {
		//context
		when(userDao.save(any(User.class))).thenAnswer( i -> {
			User user = i.getArgument(0);
			user.setUserId(1);
			return user;
		});
		
		//event
		boolean result = userServiceImpl.saveUserDetails(userRequestDto);
		
		//outcome
		assertTrue(result);
	}
	
	@Test
	@DisplayName("authentication : positive scenario")
	public void authenticationTest() {
		//context
		when(userDao.findByUserNameAndPassword("anu", "1234")).thenReturn(user);
		
		//event
		ResponseEntity<String> result = userServiceImpl.authenticate("anu", "1234");
		
		//outcome
	    assertEquals("Login success", result.getBody());
	    assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	@DisplayName("authentication : negative scenario")
	public void authenticationTest1() {
		//context
		when(userDao.findByUserNameAndPassword("anu", "1234")).thenReturn(null);
		
		//event and outcome
		assertThrows(InvalidCredentialsException.class, () ->userServiceImpl.authenticate("anu", "1234"));
		
	}
}
