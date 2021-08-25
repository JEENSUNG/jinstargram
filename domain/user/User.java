package com.heo.jinstargramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.heo.jinstargramstart.domain.image.Image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//JPA - Java Persistence API(자바로 데이터를 영구적으로 저장(db)할 수 있는 api를 제공)

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // 디비에 테이블을 생성
public class User {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 db 따라감
	private int id;
	
	@Column(length = 20, unique = true)
	private String username;
	private String password;
	private String name;
	private String website; // 웹사이트
	private String bio; // 자기소개
	private String email;
	private String phone;
	private String gender;
	
	private String profileImageUrl;
	private String role;
	
	// 나는 연관관계의 주인이 아니다. 그러므로 테이블에 컬럼을 만들지 마라
	//User를 select할 때 해당 user id로 등록된 image들을 다 들고와.
	// Lazy : User를 select할 때 해당 user id로 등록된 image로 가져오지마(대신 getimages()일 때 가져와)
	// eager : User를 select할 때 해당 user id로 등록된 image를 전부 join해서 가져와
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"user"}) // 무한참조 방지(Image 내부에있는 User user참조 방지)
	private List<Image> images; //양방향매핑
	
	private LocalDateTime createDate;
	
	@PrePersist // 디비에 insert되기 전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
		
	}
}
