package com.zidol.fc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zidol.fc.domain.Achievement;
import com.zidol.fc.domain.CS;
import com.zidol.fc.domain.User;
import com.zidol.fc.repository.AchievementRepository;
import com.zidol.fc.repository.CSRepository;

@Service
public class AchievementService {
	
	@Autowired
	AchievementRepository achievementRepository;
	
	@Autowired
	CSRepository csRepository;

	public List<Achievement> findbyAchievement(User csCode) {
		return achievementRepository.findByUser(csCode);
	}
	
	public CS findByCsCode(long csCode) {
		return csRepository.findByCsCode(csCode);
	}
	
	

}
