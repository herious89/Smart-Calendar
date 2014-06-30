package com.example.smartcalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
	private ArrayList<String> items;
	private Context mContext;
	private DisplayMetrics mDisplayMetrics;
	private int mTitleHeight, mDayHeight;
	private boolean viewFlag = false;
	ApplicationData data;
	
	public MonthViewAdapter(Context context, int textViewID, 
			int pMonth, int pYear, DisplayMetrics metrics, boolean flaq) {
		month = pMonth;
		year = pYear;
		this.mContext = context;
		mDisplayMetrics = metrics;
		this.viewFlag = flaq;
		data = (ApplicationData) this.mContext.getApplicationContext();
		items = new ArrayList<String>(data.createMonth(month, year).size());
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
		for (String s : data.createMonth(pMonth, pYear))
			items.add(s);
		// Set the height for each cell of grid view
		mTitleHeight = 40;
		mDayHeight = (mDisplayMetrics.heightPixels - mTitleHeight 
				- (6 * 13) - getBarHeight())/ (6 - 1);
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
			row.setBackground(this.mContext.getResources().getDrawable(R.drawable.textborder));
        }
		if (day_color[1].equals("DAYS"))
        {
            holder.day.setTextColor(Color.BLACK);
            holder.day.setText(theDay);
            row.setBackground(this.mContext.getResources().getDrawable(R.drawable.textborder));

        }
		if (day_color[1].equals("CURRENT"))
        {
			holder.day.setTextColor(Color.BLUE);
			holder.day.setText(theDay);
			row.setBackground(this.mContext.getResources().getDrawable(R.drawable.textborder));
        }
		if (day_color[1].equals("WEEKDAYS"))
        {
			holder.day.setVisibility(View.GONE);
			holder.day.setVisibility(View.VISIBLE);
			holder.day.setHeight(mTitleHeight);
			holder.day.setText(theDay);
			holder.day.setTextColor(Color.BLACK);
			holder.day.setBackgroundColor(Color.YELLOW);
			holder.day.setTypeface(null, Typeface.BOLD);
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
