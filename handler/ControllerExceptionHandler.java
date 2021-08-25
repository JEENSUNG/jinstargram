package com.heo.jinstargramstart.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.heo.jinstargramstart.handler.ex.CustomApiException;
import com.heo.jinstargramstart.handler.ex.CustomException;
import com.heo.jinstargramstart.handler.ex.CustomValidationApiException;
import com.heo.jinstargramstart.handler.ex.CustomValidationException;
import com.heo.jinstargramstart.util.Script;
import com.heo.jinstargramstart.web.dto.CMRespDto;

@RestController // 데이터를 응답해야 하므로
@ControllerAdvice // 모든 exception을 낚아챔
public class ControllerExceptionHandler {
	@ExceptionHandler(CustomValidationException.class) // runtimeexception이 발생하는 모든 익셉션을 가로챔
	public String validationException(CustomValidationException e) {
		
		//CMRespDto, Script 둘 다 만들어본 결과
		//1. 클라이언트에게 응답할 때는 Script 가 좋음
		//2. Ajax 통신 - CMRespDto
		//3. Android 통신 - CMRespDto 
		
		if(e.getErrorMap() == null) {
			return Script.back(e.getMessage());
		}else {
			return Script.back(e.getErrorMap().toString()); // 메세지를 리턴
		}
	}
	
	@ExceptionHandler(CustomValidationApiException.class) // runtimeexception이 발생하는 모든 익셉션을 가로챔
	public ResponseEntity<CMRespDto<?>> validationApiException(CustomValidationApiException e) {		
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
		}
	
	@ExceptionHandler(CustomApiException.class) // runtimeexception이 발생하는 모든 익셉션을 가로챔
	public ResponseEntity<CMRespDto<?>> apiException(CustomApiException e) {		
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
		}
	
	@ExceptionHandler(CustomException.class) // runtimeexception이 발생하는 모든 익셉션을 가로챔
	public String Exception(CustomException e) {
		return Script.back(e.getMessage());
	}
}
