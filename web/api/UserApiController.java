package com.heo.jinstargramstart.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heo.jinstargramstart.config.auth.PrincipalDetails;
import com.heo.jinstargramstart.domain.user.User;
import com.heo.jinstargramstart.handler.ex.CustomValidationApiException;
import com.heo.jinstargramstart.service.SubscribeService;
import com.heo.jinstargramstart.service.UserService;
import com.heo.jinstargramstart.web.dto.CMRespDto;
import com.heo.jinstargramstart.web.dto.subscribe.SubscribeDto;
import com.heo.jinstargramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {
	
	private final SubscribeService subscribeService;
	private final UserService userService; //DI되어야함

	@GetMapping("/api/user/{pageUserId}/subscribe")
	public ResponseEntity<?> subscribeList(@PathVariable int pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		List<SubscribeDto> subscribeDto = subscribeService.구독리스트(principalDetails.getUser().getId(), pageUserId);
		return new ResponseEntity<>(new CMRespDto<>(1, "구독자 정보 불러오기 성공", subscribeDto), HttpStatus.OK);
	}
	
	@GetMapping("/api/user/{pageUserId}/unsubscribe")
	public ResponseEntity<?> unsubscribeList(@PathVariable int pageUserId,@AuthenticationPrincipal PrincipalDetails principalDetails){
		List<SubscribeDto> unsubscribeDtoDo = subscribeService.구독자리스트(principalDetails.getUser().getId(), pageUserId);
		return new ResponseEntity<>(new CMRespDto<>(1, "구독자 정보", unsubscribeDtoDo), HttpStatus.OK);
	}
	
	@PutMapping("/api/user/{id}")	
	public CMRespDto<?> update(@PathVariable int id,
			@Valid UserUpdateDto userUpdateDto,
			BindingResult bindingResult, // 꼭 @valid 가 적혀있는 다음 파라미터에 적어야함
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			throw new CustomValidationApiException("유효성 검사 실패", errorMap);
		}else {
			User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
			System.out.println(userUpdateDto);
			principalDetails.setUser(userEntity); // 세션 정보 변경
			return new CMRespDto<>(1, "회원수정이 완료되었습니다.", userEntity); // 응답 시에 userEntity의 모든 getter함수가 호출되고 JSON으로 파싱하여 응답
		}
	}
}
