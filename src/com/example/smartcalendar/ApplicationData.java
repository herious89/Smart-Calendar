package com.example.smartcalendar;

import android.app.Application;

public class ApplicationData extends Application{
	private int year = 0, month = 0;
	private boolean flaq = false;
	
	public int getYear() { return year; }
	public int getMonth() { return month; }
	public boolean getFlaq() { return flaq; }
	public void setYear(int y) { year = y; }
	public void setMonth(int m) { month = m; }
	public void setFlaq(boolean f) { flaq = f; }
}
