package com.example.smartcalendar;

import java.util.Calendar;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class YearViewAdapter extends BaseAdapter{
	
	private Context mContext;
	private int mYear;
	private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private TextView monthName;
	private GridView yearlyMonthView;
	private MonthViewAdapter customGridAdapter;
	private DisplayMetrics metrics;
	
	public YearViewAdapter(Context context, int viewID, int year) {
		this.mYear = year;
		this.mContext = context;
		metrics = new DisplayMetrics();
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return months.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return months[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.month_grid_cell, parent, false);
		}
		Log.d("Here", position + "****************");
		monthName = (TextView) row.findViewById(R.id.monthName);
		monthName.setText(months[position]);
		yearlyMonthView = (GridView) row.findViewById(R.id.yearlyMonthView);
		customGridAdapter = new MonthViewAdapter(mContext, R.layout.day_grid_cell, 
				position + 1, this.mYear, metrics, false);
		customGridAdapter.notifyDataSetChanged();
		yearlyMonthView.setAdapter(customGridAdapter);
	    
		return row;
	}

}
