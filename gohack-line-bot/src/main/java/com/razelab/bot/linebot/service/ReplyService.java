package com.razelab.bot.linebot.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.message.template.ConfirmTemplate;
import com.razelab.bot.linebot.model.AiReply;
import com.razelab.bot.linebot.model.LineMovie;

@Service("replyService")
public class ReplyService {
	
	@Autowired
	private LineMessagingClient lineMessagingClient;

	public void composeTextReply(MessageEvent<TextMessageContent> event, AiReply reply) {
		String replyIntent = reply.getIntent();
		String replyMessage = reply.getMessage();
		this.reply(event.getReplyToken(), new TextMessage(replyMessage + " [intent] "+replyIntent));
	}
	
	public void composeCarouselReply(MessageEvent<TextMessageContent> event, CarouselTemplate template) {
		this.reply(event.getReplyToken(), new TemplateMessage("Cek di handphone", template));
	}
	
	public CarouselTemplate composeCarouselTemplate(List<LineMovie> lineMovie) {
		//String 
		List<CarouselColumn> columnList = new ArrayList<>();
		CarouselColumn column;
		List<Action> actions;
		for(int i = 0; i<lineMovie.size();i++){
			actions = new ArrayList<>();
			actions.add(new MessageAction("trailer", "trailer"));
			actions.add(new MessageAction("beli", "beli"));
			
			
			column = new CarouselColumn(lineMovie.get(i).getMovieThumbnail(), lineMovie.get(i).getMovieTitle(), "synopsis",Arrays.asList(
                    new URIAction("Tonton Trailer",
                    		lineMovie.get(i).getMovieTrailer()),
              new PostbackAction("Beli Tiket",
                                 "beli")));
			columnList.add(column);
		}
		
		/*String imageUrl = "https://www.cgv.id/uploads/movie/compressed/MOV3104.jpg";
        CarouselTemplate carouselTemplate = new CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn(imageUrl, "hoge", "fuga", Arrays.asList(
                                new URIAction("Go to line.me",
                                              "https://line.me"),
                                new PostbackAction("Say hello1",
                                                   "hello こんにちは")
                        )),
                        new CarouselColumn(imageUrl, "hoge", "fuga", Arrays.asList(
                                new PostbackAction("言 hello2",
                                                   "hello こんにちは",
                                                   "hello こんにちは"),
                                new MessageAction("Say message",
                                                  "Rice=米")
                        ))
                ));*/
		
		return new CarouselTemplate(columnList);
	}

	
	private TemplateMessage confirmationProfileReply(String objectName, String objectValue) {
		ConfirmTemplate confirmTemplate = new ConfirmTemplate(
				"Apakah benar " + objectName + " kamu " + objectValue + "?", new MessageAction("Benar!", "ya"),
				new MessageAction("Salah!", "tidak"));
		TemplateMessage templateMessage = new TemplateMessage("Buka pesan di smartphone kamu", confirmTemplate);
		return templateMessage;
	}
	
	private void reply(String replyToken, Message messages) {
		try {
			lineMessagingClient.replyMessage(new ReplyMessage(replyToken, messages)).get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}
