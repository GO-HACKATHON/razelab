package com.razelab.hackathon.dashboard.admin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.razelab.hackathon.dashboard.admin.model.EngineEntity;
import com.razelab.hackathon.dashboard.admin.model.EngineEntityInput;
import com.razelab.hackathon.dashboard.admin.repository.EntityRepository;

@RestController
public class AdminRestController {
	
	@Autowired
	EntityRepository entityRepository;
	

	@RequestMapping(path = "/training/entity/record", method = RequestMethod.POST)
	public ModelAndView  entityRecord(Model model, @ModelAttribute EngineEntityInput entityInput) throws JSONException {
		List<String> members = Arrays.asList(entityInput.getInputMembers().split(","));
		
		EngineEntity engineEntity = new EngineEntity(entityInput.getInputEntity(),members);
		entityRepository.insertNewEntity(engineEntity);
		
		//Access Trainer Server
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		JSONObject obj = new JSONObject();
		JSONArray jsonArray = new JSONArray();
	    for(String member : members) {
	        jsonArray.put(member);
	    }
		obj.put("entity", entityInput.getInputEntity());
		obj.put("member", jsonArray);
		
		HttpEntity<String> entity = new HttpEntity<String>(obj.toString(),headers);
		String url = "http://localhost:8088/entity/";
		//restTemplate.put(url, entity);
		
		// send request and parse result
	    ResponseEntity<String> response = restTemplate
	            .exchange(url, HttpMethod.POST, entity, String.class);
		
		return new ModelAndView("redirect:/training/entity");
	}
	
}
