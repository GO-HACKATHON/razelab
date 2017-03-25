package com.razelab.hackathon.dashboard.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
	@RequestMapping("/")
	public String index() {
		return "home";
	}
	
	@RequestMapping("/intent")
	public String intentIndex() {
		return "intent";
	}
	
	@RequestMapping("/entity")
	public String entityIndex() {
		return "intent";
	}
	
	@RequestMapping("/training/entity")
	public String trainingEntity() {
		return "trainingEntity";
		
		
	}@RequestMapping("/training/intent")
	public String trainingIntent() {
		return "trainingIntent";
	}
}
