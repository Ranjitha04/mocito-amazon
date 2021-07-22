package com.example.amazon.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.amazon.dao.UserDao;
import com.example.amazon.dto.UserRequestDto;
import com.example.amazon.exception.InvalidCredentialsException;
import com.example.amazon.model.User;
import com.example.amazon.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Override
	public boolean saveUserDetails(UserRequestDto userRequestDto) {
		User user = new User();
		BeanUtils.copyProperties(userRequestDto, user);
		User userPerst = userDao.save(user);
		if(ObjectUtils.isEmpty(userPerst)) return false;
		return true;
	}

	@Override
	public ResponseEntity<String> authenticate(String username, String password) {
		User user = userDao.findByUserNameAndPassword(username, password);
		if (user != null)
			return new ResponseEntity<>("Login success", HttpStatus.OK);
		throw new InvalidCredentialsException("Invalid Credentials..!!Please Verify your Credentials and Try Again.");
	}
}
