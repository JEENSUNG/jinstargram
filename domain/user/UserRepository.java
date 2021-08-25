package com.heo.jinstargramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

//어노테이션 없어도 JpaRepository를 상속하면 IoC등록 자동으로 됨
//jparepository는 오브젝트랑 프라이머리키 타입(id의 타입)을 써줌
public interface UserRepository extends JpaRepository<User, Integer> {
	// JPA query method
	User findByUsername(String username);
}
