package com.razelab.hackathon.server.trainer.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainerRestController {

	static Logger log = Logger.getLogger(TrainerRestController.class.getName());

	@RequestMapping(path = "/train", method = RequestMethod.GET)
	public String trainerEntry(/* @RequestBody String body */) throws IOException {
		// TODO access python script
		String[] command = new String[2];
		command[0] = "python";
		command[1] = "test.py";
		Process process = Runtime.getRuntime().exec("python echo.py");

		BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

		BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		
		String s = null;
		// read the output from the command
		System.out.println("Here is the standard output of the command:\n");
		while ((s = stdInput.readLine()) != null) {
			return s;
		}

		// read any errors from the attempted command
		System.out.println("Here is the standard error of the command (if any):\n");
		while ((s = stdError.readLine()) != null) {
			return s;
		}

		return "Faail!";
	}

}
