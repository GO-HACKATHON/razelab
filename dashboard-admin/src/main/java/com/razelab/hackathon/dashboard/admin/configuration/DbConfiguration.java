package com.razelab.hackathon.dashboard.admin.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration(value = "dbConfiguration")
@EnableMongoRepositories
public class DbConfiguration extends AbstractMongoConfiguration  {
	
	@Override
	protected String getDatabaseName() {
		return "dashboard-admin";
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient("127.0.0.1", 27017);
	}

	@Override
	protected String getMappingBasePackage() {
		return "com.razeproject.gohackathon";
	}

}
