package com.razelab.hackathon.dashboard.admin.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.razelab.hackathon.dashboard.admin.model.EngineEntity;
import com.razelab.hackathon.dashboard.admin.model.EngineIntent;

@Repository
public class IntentRepository {

	@Autowired
    MongoTemplate mongoTemplate;
	
	public long countAllEntity() {
        return mongoTemplate.count(null, EngineEntity.class);
    }
	
	public List<EngineIntent> findAll(){
		return mongoTemplate.findAll(EngineIntent.class);
	}
	
	public void insertNewEntity(EngineEntity entity){
		mongoTemplate.insert(entity);
	}
	
	public void updateEntity(EngineEntity entity){
	}
	
}
