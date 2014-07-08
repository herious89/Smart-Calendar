package com.example.smartcalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TreeSet;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WeekViewAdapter extends BaseAdapter{
	
	private final int TYPE_ITEM = 0;
	private final int TYPE_HEADER = 1;
	private Context mContext;
	private ArrayList<String> items, events;
	private TreeSet<Integer> headers;
	private ApplicationData data;
	private int pMonth, pYear;
	
	public WeekViewAdapter(Context context, int viewId,int month, int year, boolean viewFlag) {
		this.mContext = context;
		this.pMonth = month;
		this.pYear = year;
		this.events = new ArrayList<String>();
		this.headers = new TreeSet<Integer>();
		events.add("Event #1");
		events.add("Event #2");
		events.add("Event #3");
		// Get the number of week
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		calendar.setMinimalDaysInFirstWeek(1);
		int week = calendar.get(Calendar.WEEK_OF_MONTH);
		Log.d("Here", "Week = " + week);
		data = (ApplicationData) this.mContext.getApplicationContext();
		items = new ArrayList<String>();
		for (int i = (week * 7); i < (week * 7 + 7); i++) {
			Log.d("Here", "*****" + data.createMonth(month, year, viewFlag).get(i));
			items.add(data.createMonth(pMonth, pYear, viewFlag).get(i)); 
			headers.add(items.size() - 1);
			for (int j = 0; j < events.size(); j++)
				items.add(events.get(j));
		}
			
	}
	
	@Override
	public int getItemViewType(int position) {
		return headers.contains(position) ? TYPE_HEADER : TYPE_ITEM;
	}
 
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		WeekHolder holder = null;
		int rowType = getItemViewType(position);
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.day_list_row, parent, false);
			holder = new WeekHolder();
			holder.day = (TextView) row.findViewById(R.id.DateField);
			holder.event = (TextView) row.findViewById(R.id.EventList);
			row.setTag(holder);
		} else 
			holder = (WeekHolder) row.getTag();
		if (rowType == TYPE_HEADER) {
			String[] day_color = items.get(position).split("-");
			String theDay = day_color[0];
			if (day_color[1].equals("CURRENT")) 
				holder.day.setText(day_color[2] + " " + theDay + ", " + day_color[3] + " - Today");
			else
				holder.day.setText(day_color[2] + " " + theDay + ", " + day_color[3]);
			holder.day.setBackgroundColor(Color.MAGENTA);
			holder.event.setVisibility(View.GONE);
		} else {
			holder.event.setText(items.get(position));
			holder.day.setVisibility(View.GONE);
		}
		return row;
	}
	
	static class WeekHolder {
		TextView day;
		TextView event;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
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
