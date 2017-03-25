package com.razelab.hackathon.server.trainer.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.razelab.hackathon.server.trainer.model.EntityRequest;
import com.razelab.hackathon.server.trainer.model.IntentRequest;
import com.razelab.hackathon.server.trainer.model.TrainingRequest;
import com.razelab.hackathon.server.trainer.util.TrainerUtil;

@RestController
public class TrainerRestController {

	static Logger log = Logger.getLogger(TrainerRestController.class.getName());

	@RequestMapping(path = "/intent", method = RequestMethod.POST)
	public void intentEntry(@RequestBody IntentRequest request) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		if(!request.getParentIntent().isEmpty()){
			sb.append(request.getParentIntent()+".");
		} 
		sb.append(request.getCurrentIntent());
		//String filename = "E:\\Projects\\razelab\\gohackathon\\razelab\\ai-core\\trainer\\db\\intent\\"+sb.toString()+ ".rz";
		String filename = "../../../trainer/db/intent/"+sb.toString()+ ".rz";
		
		Writer output;
		output = new BufferedWriter(new FileWriter(filename, true));
		output.append(request.getSentence());
		output.append("\n");
		output.close();
	}

	@RequestMapping(path = "/entity", method = RequestMethod.POST)
	public void entityEntry(@RequestBody EntityRequest request) throws IOException {
		
		//String filename = "E:\\Projects\\razelab\\gohackathon\\razelab\\ai-core\\trainer\\db\\entity\\"+request.getEntity()+ ".rz";
		String filename = "../../../trainer/db/entity/"+request.getEntity()+ ".rz";
		
		Writer output;
		output = new BufferedWriter(new FileWriter(filename, true));
		
		//StringBuilder sb = new StringBuilder();
		for (int i = 0; i < request.getMember().size(); i++) {
			output.append(request.getMember().get(i));
			output.append("\n");
			//output.append(request.getSentence());
		}
		output.close();
		//FileWriter fw = new FileWriter("../../../trainer/db/entity/" + request.getEntity() + ".rz");
		// FileWriter fw = new
		// FileWriter("E:\\Projects\\razelab\\gohackathon\\razelab\\ai-core\\trainer\\db\\entity\\"+request.getEntity()+".rz");
		//fw.write(sb.toString());
		//fw.close();

	}

}
