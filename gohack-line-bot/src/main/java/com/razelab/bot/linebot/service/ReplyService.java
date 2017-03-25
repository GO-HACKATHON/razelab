package com.razelab.bot.linebot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.ConfirmTemplate;
import com.razelab.bot.linebot.model.AiReply;

@Service("replyService")
public class ReplyService {
	@Autowired
	private LineMessagingClient lineMessagingClient;

	public void compose(MessageEvent<TextMessageContent> event, AiReply reply) {
		String replyContext = reply.getIntent();
		String replyMessage = reply.getMessage();
		
		TemplateMessage template = confirmationProfileReply("nomer telepon", "092734704");
		this.reply(event.getReplyToken(), new TextMessage(replyMessage));
	}

	private void reply(String replyToken, Message messages) {
		try {
			lineMessagingClient.replyMessage(new ReplyMessage(replyToken, messages)).get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private TemplateMessage confirmationProfileReply(String objectName, String objectValue) {
		ConfirmTemplate confirmTemplate = new ConfirmTemplate(
				"Apakah benar " + objectName + " kamu " + objectValue + "?", new MessageAction("Benar!", "ya"),
				new MessageAction("Salah!", "tidak"));
		TemplateMessage templateMessage = new TemplateMessage("Buka pesan di smartphone kamu", confirmTemplate);
		return templateMessage;
	}

}
