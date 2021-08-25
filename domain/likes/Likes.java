package com.heo.jinstargramstart.domain.likes;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.heo.jinstargramstart.domain.image.Image;
import com.heo.jinstargramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity 
@Table(uniqueConstraints = { // 서로 유니크 해야할 때 걸어줌
		@UniqueConstraint(name = "likes_uk", columnNames = {"imageId", "userId"})
})
public class Likes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	//무한 참조됨
	@JoinColumn(name = "imageId")
	@ManyToOne
	private Image image; //하나의 이미지는 여러 개의 좋아요 (1 - N)
	
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name = "userId")
	@ManyToOne
	private User user; // 하나의 유저는 여러 개의 좋아요
	
	private LocalDateTime createDate;
	
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
