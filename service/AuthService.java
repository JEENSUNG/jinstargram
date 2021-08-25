package com.heo.jinstargramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heo.jinstargramstart.domain.user.User;
import com.heo.jinstargramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service //1.IoC 등록 2. 트랜잭션 관리
public class AuthService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional // Write(insert, update, delete)할 떄 트랜잭션이 관리
	public User 회원가입(User user) { //회원가입 진행
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		
		user.setRole("ROLE_USER"); // 관리자는 ROLE_ADMIN
		
		User userEntity = userRepository.save(user);
		return userEntity;
	}
}
