package com.example.smartcalendar;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.DisplayMetrics;
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
	private ArrayList<String> items;
	private ApplicationData data;
	private MonthViewAdapter customGridAdapter;
	public final static String SELECTED_YEAR = "", SELECTED_MONTH = "";
	private DisplayMetrics metrics;
	
	public YearViewAdapter(Context context, int viewID, int year, DisplayMetrics m) {
		this.mYear = year;
		this.mContext = context;
		data = (ApplicationData) this.mContext.getApplicationContext();
		metrics = m;
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
			holder.monthView = (GridView) row.findViewById(R.id.yearlyMonthView);
			row.setTag(holder);
		}
		else 
			holder = (YearHolder) row.getTag();
		
		holder.month.setText(months[position]);
		holder.month.setClickable(true);
		holder.month.setBackgroundColor(Color.MAGENTA);
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
		
		items = new ArrayList<String>(data.createMonth(position + 1, mYear, false).size());
		for (String s : data.createMonth(position + 1, mYear, false))
			items.add(s);

		customGridAdapter = new MonthViewAdapter(mContext, R.layout.day_grid_cell, 
				position + 1, this.mYear, metrics, false);
		customGridAdapter.notifyDataSetChanged();
		holder.monthView.setAdapter(customGridAdapter);
		return row;
	}
	
	static class YearHolder {
		TextView month;
		//TextView monthView;
		GridView monthView;
		MonthViewAdapter monthAdapter;
	}

}
