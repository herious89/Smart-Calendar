package com.example.smartcalendar;

public class CalendarEvent {
	private int eventID;
	private String eventName;
	private String eventDate;
	
	public String getEventDate() {
		return eventDate;
	}
	
	public void setEventDate(String date) {
		this.eventDate = date;
	}
	
	public int getEventID() {
		return eventID;
	}
	
	public void setEventID(int id) {
		this.eventID = id;
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
