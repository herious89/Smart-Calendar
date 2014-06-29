package com.example.smartcalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class MonthViewAdapter extends BaseAdapter{
	
	private int month, year;
	private Calendar calendar;
	private ArrayList<String> items;
	private Context mContext;
	private DisplayMetrics mDisplayMetrics;
	private String[] weekdays = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	private int currentDayOfMonth, currentWeekDay;
	private TextView text;
	private int mTitleHeight, mDayHeight;
	private boolean viewFlag = false;
	
	public MonthViewAdapter(Context context, int textViewID, 
			int pMonth, int pYear, DisplayMetrics metrics, boolean flaq) {
		month = pMonth;
		year = pYear;
		this.mContext = context;
		calendar = Calendar.getInstance();
		this.items = new ArrayList<String>();
		this.currentDayOfMonth = calendar.get(calendar.DAY_OF_MONTH);
		this.currentWeekDay =calendar.get(calendar.DAY_OF_WEEK);
		mDisplayMetrics = metrics;
		this.viewFlag = flaq;
		if (!this.viewFlag)
			weekdays = new String[] {"S", "M", "T", "W", "Th", "F", "S"};
		printMonth(month, year);
	}
	
	private int getBarHeight() {
		switch (mDisplayMetrics.densityDpi) {
		case DisplayMetrics.DENSITY_HIGH:
			return 48;
		case DisplayMetrics.DENSITY_MEDIUM:
			return 32;
		case DisplayMetrics.DENSITY_LOW:
			return 24;
		default:
			return 48;
		}
	}	
	
	private void printMonth(int pMonth, int pYear) {
		int trailingSpaces = 0;
		int daysInPrevMonth = 0;
		int prevMonth = 0;
		int nextMonth = 0;		
		int currentMonth = pMonth - 1;
		int daysInMonth = daysOfMonth[currentMonth];
		
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
			if (i == this.currentDayOfMonth && pYear == calendar.get(Calendar.YEAR)
					&& currentMonth == calendar.get(Calendar.MONTH)) {
				items.add(String.valueOf(i) + "-CURRENT" + "-" +
						months[currentMonth] + "-" + pYear);
			}
			else  
				items.add(String.valueOf(i) + "-DAYS" + "-" +
						months[currentMonth] + "-" + pYear);
		}
		
		// Calculate number of days to display for the next month
		for (int i = 0; i < items.size() % 7; i++) 
			items.add(String.valueOf(i + 1) + "-NEXT" + "-" + 
					months[nextMonth] + "-" +pYear);
		
		// Set the height for each cell of grid view
		mTitleHeight = 40;
		mDayHeight = (mDisplayMetrics.heightPixels - mTitleHeight 
				- (6 * 10) - getBarHeight())/ (6 - 1);
	}
	
	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		MonthHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.day_grid_cell, parent, false);
			holder = new MonthHolder();
			holder.day = (TextView) row.findViewById(R.id.textDate);
			row.setTag(holder);
		}		
		else
			holder = (MonthHolder) row.getTag();
		
		String[] day_color = items.get(position).split("-");
		String theDay = day_color[0];

		if (day_color[1].equals("NEXT") || day_color[1].equals("PREV"))
        {
            holder.day.setTextColor(Color.LTGRAY);
            holder.day.setText(theDay);
            if (this.viewFlag) {
            	holder.day.setHeight(mDayHeight);
            	row.setBackground(this.mContext.getResources().getDrawable(R.drawable.textborder));
            }
        }
		if (day_color[1].equals("DAYS"))
        {
            holder.day.setTextColor(Color.BLACK);
            holder.day.setText(theDay);
            if (this.viewFlag) {
            	holder.day.setHeight(mDayHeight);
            	row.setBackground(this.mContext.getResources().getDrawable(R.drawable.textborder));
            }
        }
		if (day_color[1].equals("CURRENT"))
        {
			holder.day.setTextColor(Color.BLUE);
			holder.day.setText(theDay);
            if (this.viewFlag) {
            	holder.day.setHeight(mDayHeight);
            	row.setBackground(this.mContext.getResources().getDrawable(R.drawable.textborder));
            }
        }
		if (day_color[1].equals("WEEKDAYS"))
        {
			holder.day.setText(theDay);
            if (this.viewFlag) {
            	holder.day.setHeight(mTitleHeight);
            }
            holder.day.setTextColor(Color.BLACK);
            holder.day.setBackgroundColor(Color.YELLOW);
            holder.day.setTypeface(null, Typeface.BOLD);
            holder.day.setPadding(0, 0, 0, 10);
        }
		
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
	
	static class MonthHolder {
		TextView day;
	}
}
