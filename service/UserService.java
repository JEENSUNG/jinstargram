package com.heo.jinstargramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.heo.jinstargramstart.domain.subscribe.SubscribeRepository;
import com.heo.jinstargramstart.domain.user.User;
import com.heo.jinstargramstart.domain.user.UserRepository;
import com.heo.jinstargramstart.handler.ex.CustomApiException;
import com.heo.jinstargramstart.handler.ex.CustomException;
import com.heo.jinstargramstart.handler.ex.CustomValidationApiException;
import com.heo.jinstargramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final SubscribeRepository subscribeRepository;

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Value("${file.path}")
	private String uploadFolder;

	@Transactional
	public User 회원프로필사진변경(int principalId, MultipartFile profileImageFile) {
		UUID uuid = UUID.randomUUID(); // 네트워크 상에서 고유성이 보장되는 id를 만들기 위한 표준 규약
		String imageFileName = uuid + "_" + profileImageFile.getOriginalFilename(); // 실제 파일명이 들어감.

		Path imageFilePath = Paths.get(uploadFolder + imageFileName);

		// 통신, I/O -> 예외가 발생할 수 있음
		try {
			Files.write(imageFilePath, profileImageFile.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}

		User userEntity = userRepository.findById(principalId).orElseThrow(() -> {
			throw new CustomApiException("유저를 찾을 수 없습니다.");
		});
		userEntity.setProfileImageUrl(imageFileName);

		return userEntity;
	} // 더티체킹으로 업데이트 됨.

	@Transactional(readOnly = true)
	public UserProfileDto 회원프로필(int pageUserId, int principalId) {

		UserProfileDto dto = new UserProfileDto();
		// SELECT * FROM image WHERE userId =:userId;
		User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
			throw new CustomException("해당 유저를 찾을 수 없습니다.");
		});

		dto.setUser(userEntity);
		dto.setPageOwnerState(pageUserId == principalId); // 1은 페이지 주인, -1은 주인x
		dto.setImageCount(userEntity.getImages().size());

		int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
		int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);
		int subscribeDo = subscribeRepository.mSubscribeDo(pageUserId);

		dto.setSubscribeState(subscribeState == 1);
		dto.setSubscribeCount(subscribeCount);
		dto.setSubscribeDo(subscribeDo);

		userEntity.getImages().forEach((image) -> {
			image.setLikeCount(image.getLikes().size());
		});

		return dto;
	}

	@Transactional
	public User 회원수정(int id, User user) {
		// 해야할 것 : 1. 영속화
		// 1. 무조건 찾았으니 걱정안해도 get(),;// 2. 못찾았을 땐 예외 발동(orElseThrow())
		User userEntity = userRepository.findById(id).orElseThrow(() -> {
			return new CustomValidationApiException("찾을 수 없는 ID입니다.");
		});

		// 2. 영속화된 오브젝트를 수정 -> 더티체킹(업데이트 완료)
		userEntity.setName(user.getName());

		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);

		userEntity.setPassword(encPassword);
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		return userEntity; // 더티체킹 일어나서 업데이트가 완료됨.
	}
}
