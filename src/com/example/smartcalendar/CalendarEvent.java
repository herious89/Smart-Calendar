package com.example.smartcalendar;

public class CalendarEvent {
	private int eventID;
	private String eventName;
	
	public int getEventID() {
		return eventID;
	}
	
	public void setEventID(int id) {
		eventID = id;
	}
	
	public String getEventName() {
		return eventName;
	}
	
	public void setEventName(String name) {
		this.eventName = name;
	}
	
	@Override 
	public String toString() {
		return eventName;
	}
}
