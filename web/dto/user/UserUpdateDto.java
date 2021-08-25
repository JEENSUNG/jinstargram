package com.heo.jinstargramstart.web.dto.user;

import javax.validation.constraints.NotBlank;

import com.heo.jinstargramstart.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {
	@NotBlank
	public String name;
	@NotBlank
	public String password;
	public String website;
	public String bio;
	public String phone;
	public String gender;
	
	
	//	위험함. 코드 수정 필요
	public User toEntity() {
		return User.builder()
				.name(name) // 이름 기재 안하면 문제이므로 validation 체크를 해야함
				.password(password) // 패스워드 기재 안하면 문제가됨 - > validation 체크를 해야함
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();
	}
}
