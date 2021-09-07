package com.heo.jinstargramstart.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heo.jinstargramstart.domain.subscribe.SubscribeRepository;
import com.heo.jinstargramstart.handler.ex.CustomApiException;
import com.heo.jinstargramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;
	private final EntityManager em; //Repository는 entitymanager를 구현해서 만들어져있는 구현체
	
	@Transactional
	public void 구독하기(int fromUserId, int toUserId) {
		try{
			subscribeRepository.mSubscribe(fromUserId, toUserId);
		}catch(Exception e) {
			throw new CustomApiException("이미 구독 중입니다.");
		}
	}
	@Transactional
	public void 구독취소하기(int fromUserId, int toUserId) {
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}
	
	@Transactional(readOnly = true)
	public List<SubscribeDto> 구독리스트(int principalId, int pageUserId){
		StringBuffer sb = new 	StringBuffer();
		
		// 한칸씩 끝에 안띄우면 붙여서 인식됨
		//쿼리 준비
		sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
		sb.append("if((SELECT 1 FROM subscribe WHERE fromUserId =? AND toUserId=u.id), 1,0)subscribeState, ");
		sb.append("if((?=u.id), 1,0) equalUserState ");
		sb.append("FROM user u INNER JOIN subscribe s ");
		sb.append("ON u.id = s.toUserId ");
		sb.append("WHERE s.fromUserId = ?"); // 세미콜론 첨부되면 안됨
		//1. principalId 2. principalId 3. pageUserId 
		
		//쿼리 완성
		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1, principalId)
				.setParameter(2, principalId)
				.setParameter(3, pageUserId);
		
		//쿼리 실행(qlrm라이브러리 필요 = DTO에 db결과를 매핑하기 위해서)
		JpaResultMapper result = new JpaResultMapper();
		List<SubscribeDto>subscribeDtos = result.list(query, SubscribeDto.class); //여러개받으므로 list(한개 받을때는 uniqueresult)
		return subscribeDtos;
	}
	//팔로워수 구현
	@Transactional(readOnly = true)
	public List<SubscribeDto> 구독자리스트(int principalId, int pageUserId){
		StringBuffer sb = new 	StringBuffer();
		
		// 한칸씩 끝에 안띄우면 붙여서 인식됨
		//쿼리 준비
		sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
		sb.append("if((SELECT 1 FROM subscribe WHERE fromUserId =? AND toUserId=u.id), 1,0)subscribeState, ");
		sb.append("if((?=u.id), 1,0) equalUserState ");
		sb.append("FROM user u INNER JOIN subscribe s ");
		sb.append("ON u.id = s.fromUserId ");
		sb.append("WHERE s.toUserId = ?"); // 세미콜론 첨부되면 안됨
		//1. principalId 2. principalId 3. pageUserId 
		
		//쿼리 완성
		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1, principalId)
				.setParameter(2, principalId)
				.setParameter(3, pageUserId);
		
		//쿼리 실행(qlrm라이브러리 필요 = DTO에 db결과를 매핑하기 위해서)
		JpaResultMapper result = new JpaResultMapper();
		List<SubscribeDto>subscribeDtoDo = result.list(query, SubscribeDto.class); //여러개받으므로 list(한개 받을때는 uniqueresult)
		return subscribeDtoDo;
	}
}
