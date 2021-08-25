package com.heo.jinstargramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration //IoC에 띄워서 메모리 띄워야 하므로
public class WebMvcConfig implements WebMvcConfigurer{ // web설정 파일
	@Value("${file.path}")
	private String uploadFolder;
	
	@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			WebMvcConfigurer.super.addResourceHandlers(registry);
			
			// /upload/** 패턴이 발견되면 C:workspace/springbootwork/upload/로 바꿔줌	
			registry
				.addResourceHandler("/upload/**") // JSP 페이지에서 /upload/** 들을 발동
				.addResourceLocations("file:///" + uploadFolder) // upload/**이 있으면 얘가발동
				.setCachePeriod(60*10*6) // 60초 * 10 * 6 초 -> 1시간 
				.resourceChain(true) // 이렇게 해야 발동
				.addResolver(new PathResourceResolver());
		}
}
