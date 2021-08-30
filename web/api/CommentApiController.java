package com.heo.jinstargramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.heo.jinstargramstart.config.auth.PrincipalDetails;
import com.heo.jinstargramstart.domain.comment.Comment;
import com.heo.jinstargramstart.handler.ex.CustomValidationApiException;
import com.heo.jinstargramstart.service.CommentService;
import com.heo.jinstargramstart.web.dto.CMRespDto;
import com.heo.jinstargramstart.web.dto.comment.CommentDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentApiController {
	private final CommentService commentService;
	
	@PostMapping("/api/comment")
	public ResponseEntity<?> commentSave(@Valid @RequestBody CommentDto commentDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails){
		
		Comment comment = commentService.댓글쓰기(commentDto.getContent(), commentDto.getImageId(), principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1, "댓글쓰기 성공", comment), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/api/comment/{id}")
	public ResponseEntity<?> commentDelete(@PathVariable int id){
		commentService.댓글삭제(id);
		return new ResponseEntity<>(new CMRespDto<>(1, "댓글삭제 성공", null), HttpStatus.OK);
	}
}
