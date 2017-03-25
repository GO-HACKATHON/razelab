package com.razelab.hackathon.dashboard.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.razelab.hackathon.dashboard.admin.model.EngineEntity;
import com.razelab.hackathon.dashboard.admin.repository.EntityRepository;

@Service("entityService")
public class EntityService {

	@Autowired
	private EntityRepository entityRepository;
	
	public void addEntity(EngineEntity entity){
		entityRepository.insertNewEntity(entity);
		
	}

	public List<EngineEntity> getAllEntity() {
		return entityRepository.findAll();
	}
}
