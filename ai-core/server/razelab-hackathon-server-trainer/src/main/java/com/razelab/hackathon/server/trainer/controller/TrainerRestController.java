package com.razelab.hackathon.server.trainer.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.razelab.hackathon.server.trainer.model.EntityRequest;
import com.razelab.hackathon.server.trainer.model.TrainingRequest;
import com.razelab.hackathon.server.trainer.util.TrainerUtil;

@RestController
public class TrainerRestController {

	static Logger log = Logger.getLogger(TrainerRestController.class.getName());

	@RequestMapping(path = "/train", method = RequestMethod.POST)
	public String trainerEntry(@RequestBody TrainingRequest request) throws IOException {
		// TODO parse json request
		System.out.println(request.toString());
		return TrainerUtil.runTerminal("python training_main.py");
	}

	@RequestMapping(path = "/entity", method = RequestMethod.POST)
	public void writeFile(@RequestBody EntityRequest request) throws IOException {
		// TODO write JSON file
		//String subEntity = "tempe \ntahu \nayam";
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<request.getMember().size();i++){
			sb.append(request.getMember().get(i));
			sb.append("\n");
		}

		//FileWriter fw = new FileWriter("../../../trainer/db/entity/"+entity+".rz");
		FileWriter fw = new FileWriter("E:\\Projects\\razelab\\gohackathon\\razelab\\ai-core\\trainer\\db\\entity\\"+request.getEntity()+".rz");
		fw.write(sb.toString());
		fw.close();

	}

}
