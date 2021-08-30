package com.heo.jinstargramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heo.jinstargramstart.config.auth.PrincipalDetails;
import com.heo.jinstargramstart.domain.image.Image;
import com.heo.jinstargramstart.domain.image.ImageRepository;
import com.heo.jinstargramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	
	@Transactional(readOnly = true)
	public List<Image> 인기사진(){
		return imageRepository.mPopular();
	}
	
	@Transactional(readOnly = true) // 리드온리를 안걸면 영속성 컨텍스트 변경 감지해서 더티체킹, flush(반영)
	public Page<Image> 이미지스토리(int principalId, Pageable pageable) {
		Page<Image> images = imageRepository.mStory(principalId, pageable);
		
		//2번으로 로그인 했다면 
		//images 에 좋아요 상태담기
		images.forEach((image) -> {
			
			image.setLikeCount(image.getLikes().size());
			
			image.getLikes().forEach((like)->{
				if(like.getUser().getId() == principalId) {//해당 이미지에 좋아요한 사람들 찾아서 현재 로그인한 사람이 좋아요 한건지 비교
					image.setLikeState(true);
				}
			});
		});
		return images;
	}
	
	@Value("${file.path}")
	private String uploadFolder;
	
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID(); // 네트워크 상에서 고유성이 보장되는 id를 만들기 위한 표준 규약
		String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename(); // 실제 파일명이 들어감.
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		//통신, I/O -> 예외가 발생할 수 있음
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		} catch(Exception e) {
			e.printStackTrace();
		}
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
		imageRepository.save(image);
	}
}
