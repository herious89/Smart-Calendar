package com.example.smartcalendar;

public class CalendarEvent {
	private int eventID;
	private String eventTitle;
	private String eventDate;
	private String eventDescription;
	
	public CalendarEvent(String title, String date, String des) {
		this.eventTitle = title;
		this.eventDate = date;
		this.eventDescription = des;
	}
	
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
	
	public String getEventTitle() {
		return eventTitle;
	}
	
	public void setEventTitle(String title) {
		this.eventTitle = title;
	}
	
	public void setEventDescription(String des) {
		this.eventDescription = des;
	}
	
	public String getEventDescription() {
		return this.eventDescription;
	}
	
	@Override 
	public String toString() {
		return eventDescription;
	}
}
