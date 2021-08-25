package com.heo.jinstargramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {
	
	@Modifying // insert, delete, update를 네이티브 쿼리로 작성하려면 필요
	@Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
	void mSubscribe(int fromUserId, int toUserId);
	
	@Modifying
	@Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
	void mUnSubscribe(int fromUserId, int toUserId);
	
	
	//select만 하므로 modifying 필요 업음
	@Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId =:principalId AND toUserId = :pageUserId", nativeQuery = true)
	int mSubscribeState(int principalId, int pageUserId);
	
	//팔로워
	@Query(value = "SELECT COUNT(*) FROM subscribe WHERE toUserId = :pageUserId", nativeQuery = true)
	int mSubscribeDo(int pageUserId);
	
	//팔로잉
	@Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId =:pageUserId", nativeQuery = true)
	int mSubscribeCount(int pageUserId);
}
