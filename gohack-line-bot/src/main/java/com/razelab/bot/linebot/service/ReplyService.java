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
	
	static final String CGV_CINEMA = "CGV Cinema";
	static final String CGV_URL = "https://www.cgv.id";
	
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
		/*List<CarouselColumn> columnList = new ArrayList<>();
		CarouselColumn column;
		for(int i = 0; i<lineMovie.size();i++){
			
			column = new CarouselColumn(lineMovie.get(i).getMovieThumbnail(), lineMovie.get(i).getMovieTitle(), CGV_CINEMA, Arrays.asList(
                    new URIAction("Tonton Trailer",
                    		lineMovie.get(i).getMovieTrailer()),
              new PostbackAction("Beli Tiket",
            		  CGV_URL)));
			columnList.add(column);
		}*/
		
        CarouselTemplate carouselTemplate = new CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn(lineMovie.get(0).getMovieThumbnail(), lineMovie.get(0).getMovieTitle(), CGV_CINEMA, Arrays.asList(
                                new URIAction("Tonton Trailer",
                                		lineMovie.get(0).getMovieTrailer()),
                                new URIAction("Beli tiket",
                                                   "https://www.cgv.id")
                        )),
                        new CarouselColumn(lineMovie.get(1).getMovieThumbnail(), lineMovie.get(1).getMovieTitle(), CGV_CINEMA, Arrays.asList(
                        		new URIAction("Tonton Trailer",
                        				lineMovie.get(1).getMovieTrailer()),
                          new URIAction("Beli tiket",
                                             "https://www.cgv.id")
                        )),
                        new CarouselColumn(lineMovie.get(2).getMovieThumbnail(), lineMovie.get(2).getMovieTitle(), CGV_CINEMA, Arrays.asList(
                                new URIAction("Tonton Trailer",
                                		lineMovie.get(2).getMovieTrailer()),
                                new URIAction("Beli tiket",
                                                   "https://www.cgv.id")
                        )),
                        new CarouselColumn(lineMovie.get(3).getMovieThumbnail(), lineMovie.get(3).getMovieTitle(), CGV_CINEMA, Arrays.asList(
                        		new URIAction("Tonton Trailer",
                        				lineMovie.get(3).getMovieTrailer()),
                          new URIAction("Beli tiket",
                                             "https://www.cgv.id")
                        )),
                        new CarouselColumn(lineMovie.get(4).getMovieThumbnail(), lineMovie.get(4).getMovieTitle(), CGV_CINEMA, Arrays.asList(
                                new URIAction("Tonton Trailer",
                                		lineMovie.get(4).getMovieTrailer()),
                                new URIAction("Beli tiket",
                                                   "https://www.cgv.id")
                        )),
                        new CarouselColumn(lineMovie.get(5).getMovieThumbnail(), lineMovie.get(5).getMovieTitle(), CGV_CINEMA, Arrays.asList(
                        		new URIAction("Tonton Trailer",
                        				lineMovie.get(5).getMovieTrailer()),
                          new URIAction("Beli tiket",
                                             "https://www.cgv.id")
                        ))
                ));
		
		return carouselTemplate;
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
