package com.heo.jinstargramstart.web;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.heo.jinstargramstart.config.auth.PrincipalDetails;
import com.heo.jinstargramstart.domain.image.Image;
import com.heo.jinstargramstart.handler.ex.CustomValidationException;
import com.heo.jinstargramstart.service.ImageService;
import com.heo.jinstargramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ImageController{
	
	private final ImageService imageService;
	
	@GetMapping({"/", "/image/story"})
	public String story() {
		return "image/story";
	}
	
	//브라우저가 아니라 안드로이드, ios에서 요청할 때 api에서 구현
	@GetMapping( "/image/popular")
	public String popular(Model model) {
		// api는 데이터를 리턴하는 서버이므로 따로 리턴할 필요 없이 데이터를 들고가기만 하면 됨.(api를 따로 만들필요는x)
		List<Image> images = imageService.인기사진();
		model.addAttribute("images", images);
		return "image/popular";
	}
	
	@GetMapping("/image/upload")
	public String upload() {
		return "image/upload";
	}
	
	@PostMapping("/image")
	public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if(imageUploadDto.getFile().isEmpty()) {
			throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);
		}
		// 서비스 호출
		imageService.사진업로드(imageUploadDto, principalDetails);
		return "redirect:/user/" + principalDetails.getUser().getId();
	}
}