package com.razelab.hackathon.dashboard.admin.controller;



import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.razelab.hackathon.dashboard.admin.model.EngineEntity;
import com.razelab.hackathon.dashboard.admin.model.EngineEntityInput;
import com.razelab.hackathon.dashboard.admin.repository.EntityRepository;
import com.razelab.hackathon.dashboard.admin.service.EntityService;


@Controller
public class AdminWebController {
	
	@Autowired
	EntityRepository entityRepository;
	
	@Autowired
	EntityService entityService;
	
	@RequestMapping("/")
	public String index() {
		return "home";
	}
	
	/*@RequestMapping("/intent")
	public String intentIndex() {
		return "intent";
	}
	
	@RequestMapping("/entity")
	public String entityIndex() {
		return "entity";
	}*/
	
	@RequestMapping(path = "/training/entity", method = RequestMethod.GET)
	public String trainingEntity(Model model, @ModelAttribute EngineEntityInput entityInput) {
		List<EngineEntity> entityList= entityService.getAllEntity();
		model.addAttribute("totalEntity", String.valueOf(entityList.size()));
		model.addAttribute("entities", entityService.getAllEntity());
		model.addAttribute("entityInput", entityInput);
		return "trainingEntity";
	}
	
	
	
	
	
	@RequestMapping("/training/intent")
	public String trainingIntent() {
		return "trainingIntent";
	}
}
