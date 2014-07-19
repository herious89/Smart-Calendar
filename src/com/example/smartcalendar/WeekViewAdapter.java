package com.example.smartcalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
	
	private final int TYPE_ITEM = 0;
	private final int TYPE_HEADER = 1;
	private Context mContext;
	private List<String> items;
	private List<CalendarEvent> events;
	private List<Integer> headers;
	private CalendarEventHandler handler;
	private ApplicationData data;
	private int pMonth, pYear, pDay, pWeek;
	
	public WeekViewAdapter(Context context, int viewId,int month, int year, int day, int week, boolean viewFlag) {
		this.mContext = context;
		this.pMonth = month;
		this.pYear = year;
		this.pDay = day;
		this.pWeek = week;
		this.headers = new ArrayList<Integer>();
		
		// Call the event database
		handler = new CalendarEventHandler(mContext);
		
		// Get the number of week
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		Log.d("Here", pYear + ", " + pMonth + ", " + calendar.get(Calendar.DAY_OF_MONTH));
		calendar.set(pYear, pMonth - 1, pDay);
		calendar.setMinimalDaysInFirstWeek(1);
		pWeek = calendar.get(Calendar.WEEK_OF_MONTH);
		Log.d("Here", "Week = " + week);
		data = (ApplicationData) this.mContext.getApplicationContext();
		items = new ArrayList<String>();
		// Add the headers and events
		for (int i = (pWeek * 7), index = 0; i < (pWeek * 7 + 7); i++, index++) {
			items.add(data.createMonth(pMonth, pYear, viewFlag).get(i)); 
			headers.add(items.size() - 1);
			// Get the event list for each date of the week
			events = handler.getEventByDate(data.convertDate(items.get(headers.get(index)), false));
			if (events.isEmpty())
				// Display "No Events"
				items.add("No Events");
			else {
				// Add all events of the day to item list 
				for (CalendarEvent e : events) {
					items.add(e.getEventTitle());
				}
			}
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
