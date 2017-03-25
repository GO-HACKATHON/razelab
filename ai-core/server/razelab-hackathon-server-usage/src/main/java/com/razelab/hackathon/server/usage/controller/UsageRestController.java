package com.razelab.hackathon.server.usage.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.razelab.hackathon.server.usage.model.ChatInput;
import com.razelab.hackathon.server.usage.util.UsageUtil;


@RestController
public class UsageRestController {

	static Logger log = Logger.getLogger(UsageRestController.class.getName());

	@RequestMapping(path = "/chatInput", method = RequestMethod.POST)
	public String chat(@RequestBody ChatInput request) throws IOException {
		String filename = "../../../trainer/usage/inputChat.txt";
		Writer output;
		output = new BufferedWriter(new FileWriter(filename, false));
		output.append(request.getSentence());
		output.close();
		
		return UsageUtil.runTerminal("python ../../../trainer/preprocess.py");
	}

	

}
