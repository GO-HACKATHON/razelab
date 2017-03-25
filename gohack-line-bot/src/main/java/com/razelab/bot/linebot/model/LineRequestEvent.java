package com.razelab.bot.linebot.model;

import java.util.List;

import com.linecorp.bot.model.event.Event;

public class LineRequestEvent {
	
	List<Event> events;

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

}
