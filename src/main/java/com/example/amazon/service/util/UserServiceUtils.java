package com.example.amazon.service.util;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

import com.example.amazon.dto.UserRequestDto;
import com.example.amazon.model.User;

public class UserServiceUtils {

	public static User convertDtoToModel(UserRequestDto userRequestDto) {
		User user = new User();
		BeanUtils.copyProperties(userRequestDto, user);
		return user;
	}
}
