package com.razelab.hackathon.server.usage.controller;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;
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
		String filename = "usage/inputChat.txt";
		FileWriter fw = new FileWriter(filename);
		fw.write(request.getSentence());
		fw.close();
		UsageUtil.runTerminal("python preprocess.py");
		return UsageUtil.readFile("usage/outputChat.txt");
	}

}
