package com.example.smartcalendar;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;



public class MonthViewAdapter extends BaseAdapter{
	
	private int month, year;
	private ArrayList<String> items;
	private Context mContext;
	private DisplayMetrics mDisplayMetrics;
	private static  String[] str_arr ;
	private int mTitleHeight, mDayHeight, displayWidth, 
		displayHeight, statusbar_height, required_height, column_width, column_height;
	ApplicationData data;
	private boolean viewFlag = false;
	
	public MonthViewAdapter(Context context, int textViewID, 
			int pMonth, int pYear, DisplayMetrics metrics, boolean flag) {
		month = pMonth;
		year = pYear;
		this.mContext = context;
		mDisplayMetrics = metrics;
		displayWidth = metrics.widthPixels ;
		displayHeight = metrics.heightPixels;
		statusbar_height = getStatusBarHeight(mContext.getApplicationContext());
		required_height = displayHeight - statusbar_height;
		int arrSize = 7 * 7;
		str_arr = new String[arrSize];
		for(int i=0;i<arrSize;i++){
			str_arr[i] = String.valueOf(i);
		}
		column_width = displayWidth / 7;
		column_height = required_height / 7;
		this.viewFlag = flag;
		data = (ApplicationData) this.mContext.getApplicationContext();
		items = new ArrayList<String>(data.createMonth(month, year, viewFlag).size());
		printMonth(month, year);
	}
	
	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
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
		for (String s : data.createMonth(pMonth, pYear, viewFlag))
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
			holder.lin = (LinearLayout) row.findViewById(R.id.layout);
			holder.day = (TextView) row.findViewById(R.id.textDate);
			row.setTag(holder);
		}		
		else
			holder = (MonthHolder) row.getTag();
		
		String[] day_color = items.get(position).split("-");
		String theDay = day_color[0];
		
		if (day_color[1].equals("NEXT") || day_color[1].equals("PREV")) {	
			holder.day.setTextColor(Color.LTGRAY);
			holder.day.setText(theDay);
			if (this.viewFlag) {
				holder.day.setTextSize(15);
				holder.day.setHeight(column_height);
				holder.day.setWidth(column_width);
				row.setBackground(this.mContext.getResources().getDrawable(R.drawable.textborder));
			}
        }
		if (day_color[1].equals("DAYS")) {
			if (position % 7 == 0 || position % 7 == 6)
				holder.day.setTextColor(Color.RED);
			else
				holder.day.setTextColor(Color.BLACK);
            holder.day.setText(theDay);
            if (this.viewFlag) {
				holder.day.setTextSize(15);
				holder.day.setHeight(column_height);
				holder.day.setWidth(column_width);
	            row.setBackground(this.mContext.getResources().getDrawable(R.drawable.textborder));
            }

        }
		if (day_color[1].equals("CURRENT")) {
			holder.day.setTextColor(Color.BLUE);
			holder.day.setText(theDay);
			if (this.viewFlag) {
				holder.day.setTextSize(15);
				holder.day.setHeight(column_height);
				holder.day.setWidth(column_width);
				row.setBackground(this.mContext.getResources().getDrawable(R.drawable.textborder));
			}
        }
		if (day_color[1].equals("WEEKDAYS")) {
			holder.day.setText(theDay); 
			if (day_color[0].equals("S"))
				holder.day.setTextColor(Color.RED);
			else
				holder.day.setTextColor(Color.BLUE);
			
			if (this.viewFlag) {
				holder.day.setTextSize(15);
				holder.day.setTextColor(Color.WHITE);
				holder.day.setHeight(mTitleHeight);
				holder.day.setWidth(column_width);
				holder.day.setBackgroundColor(Color.MAGENTA);
				holder.day.setTypeface(null, Typeface.BOLD);
				holder.day.setPadding(0, 0, 0, 10);
			}
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
		LinearLayout lin;
	}
}
