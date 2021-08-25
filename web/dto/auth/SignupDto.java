package com.heo.jinstargramstart.web.dto.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.heo.jinstargramstart.domain.user.User;

import lombok.Data;

@Data //Getter, Setter
public class SignupDto {
	
	@Size(min = 3, max = 20)
	private String username;
	
	private String password;
	private String email;
	private String name;
	
	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.email(email)
				.name(name)
				.build();
	}
}
