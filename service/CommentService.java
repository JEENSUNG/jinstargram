package com.heo.jinstargramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heo.jinstargramstart.domain.comment.Comment;
import com.heo.jinstargramstart.domain.comment.CommentRepository;
import com.heo.jinstargramstart.domain.image.Image;
import com.heo.jinstargramstart.domain.user.User;
import com.heo.jinstargramstart.domain.user.UserRepository;
import com.heo.jinstargramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	
	@Transactional
	public Comment 댓글쓰기(String content, int imageId, int userId) {
		
		//Tip(객체를 넘길 때 id값만 담아서 insert할 수 있음)
		//대신 return 사이에 image객체와 user객체는 id값만 가지고 있는 빈 객체를 리턴 받는다.(나머지 값 null)
		Image image = new Image();
		image.setId(imageId);
		User userEntity = userRepository.findById(userId).orElseThrow(()->{
			throw new CustomApiException("아이디를 찾을 수 없습니다.");
		});
		
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setImage(image);
		comment.setUser(userEntity);
		return commentRepository.save(comment);
	}
	
	@Transactional
	public void 댓글삭제(int id) {
		try {
			commentRepository.deleteById(id);
		}
		catch(Exception e) {
			throw new CustomApiException(e.getMessage());	
			}
	}
}
