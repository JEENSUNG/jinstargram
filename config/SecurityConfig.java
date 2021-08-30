package com.heo.jinstargramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.heo.jinstargramstart.config.oauth.Oauth2DetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity // 해당 파일로 시큐리티 활성화(SecurityConfig로 시큐리티 활성화)
@Configuration //IoC 등록 -> 메모리에 뜸
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final Oauth2DetailsService oauth2DetailsService;
	
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
			.defaultSuccessUrl("/")
			.and()
			.oauth2Login() //form 로그인도 하겠지만 oauth2 로그인도 하겠다는 것.
			.userInfoEndpoint() //oauth로그인을 하면 최종 응답을 회원 정보로 바로 받을 수 있다.(이게 제일 편함)
			.userService(oauth2DetailsService);
	}
}
