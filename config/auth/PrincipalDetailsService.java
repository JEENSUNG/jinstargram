package com.heo.jinstargramstart.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.heo.jinstargramstart.domain.user.User;
import com.heo.jinstargramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service //IoC가됨
public class PrincipalDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	//1 패스워드는 스스로 체킹하므로 신경안써도됨 
	//2 리턴 잘되면 자동으로 Userdetails 타입을 세션으로 만듦
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) {
			return null;
		}else {
			return new PrincipalDetails(userEntity);
		}
	}
}
