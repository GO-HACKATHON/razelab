package com.razelab.hackathon.dashboard.admin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.razelab.hackathon.dashboard.admin.model.EngineEntity;
import com.razelab.hackathon.dashboard.admin.model.EngineEntityInput;
import com.razelab.hackathon.dashboard.admin.repository.EntityRepository;

@RestController
public class AdminRestController {
	
	@Autowired
	EntityRepository entityRepository;
	

	@RequestMapping(path = "/training/entity/record", method = RequestMethod.POST)
	public ModelAndView  entityRecord(Model model, @ModelAttribute EngineEntityInput entityInput) {
		List<String> member = Arrays.asList(entityInput.getInputMembers().split(","));
		
		EngineEntity engineEntity = new EngineEntity(entityInput.getInputEntity(),member);
		entityRepository.insertNewEntity(engineEntity);
		return new ModelAndView("redirect:/training/entity");
	}
	
}
