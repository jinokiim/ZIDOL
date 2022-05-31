package com.zidol.fc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zidol.fc.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByUserEmail(String userId);
	public User findByUserName(String userName);
}
