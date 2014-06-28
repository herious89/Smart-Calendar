package com.example.smartcalendar;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class YearViewAdapter extends BaseAdapter{
	
	private Context mContext;
	private int mYear;
	private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private TextView monthName;
	private GridView yearlyMonthView;
	private MonthViewAdapter customGridAdapter;
	private DisplayMetrics metrics;
	public final static String SELECTED_YEAR = "", SELECTED_MONTH = "";
	
	public YearViewAdapter(Context context, int viewID, int year) {
		this.mYear = year;
		this.mContext = context;
		this.metrics = new DisplayMetrics();
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
		monthName = (TextView) row.findViewById(R.id.monthName);
		monthName.setText(months[position]);
		monthName.setClickable(true);
		final int month = position;
		monthName.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "monthName is clicked", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(mContext, DisplayMonthActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				ApplicationData data = (ApplicationData) mContext.getApplicationContext();
				data.setFlaq(true);
				data.setMonth(month);
				data.setYear(mYear);
				mContext.startActivity(intent);
			}
		});
		yearlyMonthView = (GridView) row.findViewById(R.id.yearlyMonthView);
		customGridAdapter = new MonthViewAdapter(mContext, R.layout.day_grid_cell, 
				position + 1, this.mYear, metrics, false);
		customGridAdapter.notifyDataSetChanged();
		yearlyMonthView.setAdapter(customGridAdapter);
	    
		return row;
	}

}
