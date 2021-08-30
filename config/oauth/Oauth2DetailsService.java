package com.heo.jinstargramstart.config.oauth;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.heo.jinstargramstart.config.auth.PrincipalDetails;
import com.heo.jinstargramstart.domain.user.User;
import com.heo.jinstargramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service// IoC등록
public class Oauth2DetailsService extends DefaultOAuth2UserService{
	
	private final UserRepository userRepository;
	
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		Map<String, Object> userInfo = oauth2User.getAttributes();
		String name = (String) userInfo.get("name"); // (String) 써주는 이유는 오브젝트 타입이라서 다운캐스트해줘야함.
		String username = "facebook_" + (String) userInfo.get("id");
		String email = (String) userInfo.get("email");
		String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
		
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) {  //페이스북 최초 로그인
			User user = User.builder()
					.username(username)
					.password(password)
					.email(email)
					.name(name)
					.role("ROLE_USER")
					.build();
			
			return new PrincipalDetails(userRepository.save(user), oauth2User.getAttributes());
		}else { // 이미 회원가입 되어있을 때
			return new PrincipalDetails(userEntity, oauth2User.getAttributes());
		}
		

	}
}
