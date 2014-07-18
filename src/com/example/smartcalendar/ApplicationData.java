package com.example.smartcalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Application;
import android.util.Log;


public class ApplicationData extends Application{
	private int year = 0, month = 0, day = 0;
	private boolean flaq = false;
	
	public int getYear() { return year; }
	public int getMonth() { return month; }
	public int getDay() { return day; }
	public boolean getFlaq() { return flaq; }
	public void setYear(int y) { year = y; }
	public void setMonth(int m) { month = m; }
	public void setDay(int d) { day = d; }
	public void setFlaq(boolean f) { flaq = f; }
	
	public String convertDate(String d) {
		String[] date = d.split("-");
		String result = date[2] + " " + date[0] + ", " + date[3];
		return result;
	}
	
	public String[] convertRawDate(String d) {
		String[] result = d.split("-");
		return result;
	}
	
	public ArrayList<String> createMonth(int pMonth, int pYear, boolean flag) {
		String[] weekdays = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
		String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		int trailingSpaces = 0;
		int daysInPrevMonth = 0;
		int prevMonth = 0;
		int nextMonth = 0;		
		int currentMonth = pMonth - 1;
		int daysInMonth = daysOfMonth[currentMonth];
		ArrayList<String> items = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		if (!flag)
			weekdays = new String[] {"S", "M", "T", "W", "Th", "F", "S"};
		for (String day : weekdays) {
			day += "-WEEKDAYS";
			items.add(day);
		}
		
		// Gregorian Calendar : MINUS 1, set to FIRST OF MONTH
		GregorianCalendar cal = new GregorianCalendar(pYear, currentMonth, 1);
		trailingSpaces = cal.get(Calendar.DAY_OF_WEEK) - 1;
		
		// Check for current previous month and next month
		if (currentMonth == 11)
        {
            prevMonth = currentMonth - 1;
            nextMonth = 0;
            daysInPrevMonth = daysOfMonth[prevMonth];
        }
		else if (currentMonth == 0)
        {
            prevMonth = 11;
            nextMonth = 1;
            daysInPrevMonth = daysOfMonth[prevMonth];
        }
		else
        {
            prevMonth = currentMonth - 1;
            nextMonth = currentMonth + 1;
            daysInPrevMonth = daysOfMonth[prevMonth];
        }
		
		// Check for leap year
		if (cal.isLeapYear(Calendar.YEAR) && pMonth == 1) 
			daysInMonth++;
		
		// Calculate trailing spaces 
		for (int i = 0; i < trailingSpaces; i++) {
			items.add(String.valueOf(daysInPrevMonth - trailingSpaces + 1 + i) + "-PREV"
					+ "-" + months[prevMonth] + "-" + pYear);
		}
		
		
		// Calculate number of days to display in the current month
		for (int i = 1; i <= daysInMonth; i++) {
			// Check for the current day of the month
			if (i == currentDayOfMonth && pYear == calendar.get(Calendar.YEAR)
					&& currentMonth == calendar.get(Calendar.MONTH)) {
				items.add(String.valueOf(i) + "-CURRENT" + "-" +
						months[currentMonth] + "-" + pYear);
			}
			else  
				items.add(String.valueOf(i) + "-DAYS" + "-" +
						months[currentMonth] + "-" + pYear);
			if (currentMonth == 8)
				Log.d("Here", "****" + items.get(i));
		}
		
		// Calculate number of days to display for the next month
		for (int i = 0; i < items.size() % 7; i++) 
			items.add(String.valueOf(i + 1) + "-NEXT" + "-" + 
					months[nextMonth] + "-" +pYear);
		return items;
	}
}
