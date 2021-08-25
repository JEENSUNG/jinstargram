package com.heo.jinstargramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity // 해당 파일로 시큐리티 활성화(SecurityConfig로 시큐리티 활성화)
@Configuration //IoC 등록 -> 메모리에 뜸
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean //securityconfig가 등록될 때 bean어노테이션을 읽어서 리턴해줌 -> di에서 쓰기만 하면됨
	public BCryptPasswordEncoder eocode() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//super 삭제시킴 -> 기존 시큐리티가 갖고 있던 기능이 다 비활성화됨
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**", "/api/**","/unsubscribe/**").authenticated()
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.loginPage("/auth/signin") // GET
			.loginProcessingUrl("/auth/signin") // POST -> 스프링 시큐리티가 로그인 프로세스 진행
			.defaultSuccessUrl("/");
	}
}
