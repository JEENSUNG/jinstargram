package com.heo.jinstargramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.heo.jinstargramstart.domain.user.User;

import lombok.Data;

//User 오브젝트를 담는게 목적
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

	private static final long serialVersionUID = 1L;

	private User user;

	private Map<String, Object> attributes;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}

	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
	}
	
	// 권한이 한개가 아닐 수 있음
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> collector = new ArrayList<>();
		collector.add(() -> {
			return user.getRole();
		});
		return collector;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes; // { 페이스북 유저 정보}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return (String)attributes.get("name");
	}
}
