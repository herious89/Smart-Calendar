package com.example.smartcalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WeekViewAdapter extends BaseAdapter{
	
	private Context mContext;
	private ArrayList<String> items;
	private ApplicationData data;
	private int pMonth, pYear;
	
	public WeekViewAdapter(Context context, int viewId,int month, int year, boolean viewFlag) {
		this.mContext = context;
		this.pMonth = month;
		this.pYear = year;
		// Get the number of week
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		calendar.setMinimalDaysInFirstWeek(1);
		int week = calendar.get(Calendar.WEEK_OF_MONTH);
		data = (ApplicationData) this.mContext.getApplicationContext();
		items = new ArrayList<String>(data.createMonth(pMonth, pYear, viewFlag).size());
		for (int i = week * 7; i < week * 14; i++) {
			Log.d("Here", "*****" + data.createMonth(month, year, viewFlag).get(i));
			items.add(data.createMonth(pMonth, pYear, viewFlag).get(i));
		}
			
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		WeekHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.day_list_row, parent, false);
			holder = new WeekHolder();
			holder.day = (TextView) row.findViewById(R.id.DateField);
			holder.event = (TextView) row.findViewById(R.id.EventList);
			row.setTag(holder);
		} else 
			holder = (WeekHolder) row.getTag();
		String[] day_color = items.get(position).split("-");
		String theDay = day_color[0];
		Log.d("Here", "*****" + day_color[2] + "/" + theDay + "/" + day_color[3]);
		if (day_color[1].equals("CURRENT")) 
			holder.day.setText(day_color[2] + " " + theDay + ", " + day_color[3] + " - Today");
		else
			holder.day.setText(day_color[2] + " " + theDay + ", " + day_color[3]);
		holder.day.setBackgroundColor(Color.MAGENTA);
		holder.event.setText("No event");
		
		return row;
	}
	
	static class WeekHolder {
		TextView day;
		TextView event;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 7;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
}
