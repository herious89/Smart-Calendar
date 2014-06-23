package com.example.smartcalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class GridViewAdapter extends BaseAdapter{
	
	private int month, year;
	private Calendar calendar;
	private ArrayList<String> items;
	private Context mContext;
	
	private final String[] weekdays = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	private int currentDayOfMonth, currentWeekDay;
	private TextView text;
	
	public GridViewAdapter(Context context, int textViewID, int pMonth, int pYear) {
		month = pMonth;
		year = pYear;
		this.mContext = context;
		calendar = Calendar.getInstance();
		this.items = new ArrayList<String>();
		this.currentDayOfMonth = calendar.get(calendar.DAY_OF_MONTH);
		this.currentWeekDay =calendar.get(calendar.DAY_OF_WEEK);
		printMonth(month, year);
	}
	

	private void printMonth(int pMonth, int pYear) {
		int trailingSpaces = 0;
		int leadSpaces = 0;
		int daysInPrevMonth = 0;
		int prevMonth = 0;
		int prevYear = 0;
		int nextMonth = 0;
		int nextYear = 0;
		
		int currentMonth = pMonth - 1;
		String currentMonthName = months[currentMonth];
		int daysInMonth = daysOfMonth[currentMonth];
		
		for (String day : weekdays) {
			day += "-RED";
			items.add(day);
		}
		
		// Gregorian Calendar : MINUS 1, set to FIRST OF MONTH
		GregorianCalendar cal = new GregorianCalendar(pYear, currentMonth, 1);
		trailingSpaces = cal.get(Calendar.DAY_OF_WEEK) - 1;
		
		if (cal.isLeapYear(Calendar.YEAR) && pMonth == 1) 
			daysInMonth++;
		for (int i = 0; i < trailingSpaces; i++) {
			items.add(String.valueOf(31 - trailingSpaces + 1 + i) + "-GREY"
					+ "-" + months[currentMonth - 1] + "-" + 2013);
		}
		
		for (int i = 1; i <= daysInMonth; i++) {
			Log.d(currentMonthName, String.valueOf(i) + " " + months[currentMonth] + " " + pYear);
			if (i == this.currentDayOfMonth) {
				items.add(String.valueOf(i) + "-BLUE" + "-" +
						months[currentMonth] + "-" + pYear);
			}
			else 
				items.add(String.valueOf(i) + "-BLACK" + "-" +
						months[currentMonth] + "-" + pYear);
		}
		
		for (int i = 0; i < items.size() % 7; i++) 
			items.add(String.valueOf(i + 1) + "-GREY" + "-" + 
					months[currentMonth + 1] + "-" +pYear);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.row_grid_view, parent, false);
		}		
		
		text = (TextView) row.findViewById(R.id.item_text);
		String[] day_color = items.get(position).split("-");
		String theDay = day_color[0];
		text.setText(theDay);
		if (day_color[1].equals("GREY"))
        {
            text.setTextColor(Color.LTGRAY);
            row.setBackgroundDrawable(this.mContext.getResources().getDrawable(R.drawable.textborder));
        }
		if (day_color[1].equals("BLACK"))
        {
            text.setTextColor(Color.BLACK);
            row.setBackgroundDrawable(this.mContext.getResources().getDrawable(R.drawable.textborder));
        }
		if (day_color[1].equals("BLUE"))
        {
            text.setTextColor(Color.BLUE);
            row.setBackgroundDrawable(this.mContext.getResources().getDrawable(R.drawable.textborder));
        }
		if (day_color[1].equals("RED"))
        {
            text.setTextColor(Color.RED);
            text.setTypeface(null, Typeface.BOLD);
        }
		row.setTag(theDay);
		return row;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	

}
