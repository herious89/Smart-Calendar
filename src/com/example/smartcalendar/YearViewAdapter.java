package com.example.smartcalendar;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
	private DisplayMetrics metrics;
	private ArrayList<String> items;
	private ApplicationData data;
	public final static String SELECTED_YEAR = "", SELECTED_MONTH = "";
	
	
	public YearViewAdapter(Context context, int viewID, int year) {
		this.mYear = year;
		this.mContext = context;
		this.metrics = new DisplayMetrics();
		data = (ApplicationData) this.mContext.getApplicationContext();
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
		YearHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.month_grid_cell, parent, false);
			holder = new YearHolder();
			holder.month = (TextView) row.findViewById(R.id.monthName);
			holder.monthView = (TextView) row.findViewById(R.id.yearlyMonthView);
			row.setTag(holder);
		}
		else 
			holder = (YearHolder) row.getTag();
		
		holder.month.setText(months[position]);
		holder.month.setClickable(true);
		final int month = position;
		holder.month.setOnClickListener(new View.OnClickListener() {
			
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
		
		items = new ArrayList<String>(data.createMonth(position + 1, mYear).size());
		for (String s : data.createMonth(position + 1, mYear))
			items.add(s);
		String temp = "";
		String result = "";
		for (int i = 7; i < items.size(); i++) {
			String[] day = items.get(i).split("-");
			if (Integer.parseInt(day[0]) <= 9) {
				if (day[1].equals("NEXT") ||  day[1].equals("PREV"))
					temp =String.format("%1$4s", " ");
				else
					temp = String.format("%1$4s", day[0]);
			}
			else {
				if (day[1].equals("NEXT") ||  day[1].equals("PREV"))
					temp =String.format("%1$3s", "     ");
				else
					temp = String.format("%1$3s", day[0]);
			}
			if (i % 7 == 6)
				temp += "\n";
			result += temp;
		}

		holder.monthView.setText(result);
		holder.monthView.setTextSize(10);
		holder.monthView.setTextColor(Color.BLACK);
		holder.monthView.setOnClickListener(new View.OnClickListener() {
			
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
		return row;
	}
	
	static class YearHolder {
		TextView month;
		TextView monthView;
		MonthViewAdapter monthAdapter;
	}

}
