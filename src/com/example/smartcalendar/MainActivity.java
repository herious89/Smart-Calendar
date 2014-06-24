package com.example.smartcalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;


public class MainActivity extends Activity {
	private Button btnNext, btnPrev;
	private Calendar calendar;
	private GridView calendarView;
	private GridViewAdapter customGridAdapter;
	private final String[] months = {"January", "February", "March", "April", 
									"May", "June", "July", "August", 
									"September", "October", "November", "December"};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Get the calendar using default timezone  and locale
		calendar = Calendar.getInstance(Locale.getDefault());
		// Get the grid view 
		calendarView = (GridView) this.findViewById(R.id.calendar);
		
		// get display metrics
		final DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		// Create and set adapter for grid view 
		customGridAdapter = new GridViewAdapter(getApplicationContext(), R.layout.row_grid_view, 
				calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR), metrics);
		customGridAdapter.notifyDataSetChanged();
		calendarView.setAdapter(customGridAdapter);
		
		// Set the custom action bar
		ActionBar actionBarTop = getActionBar();
		actionBarTop.setCustomView(R.layout.actionbar_top_main);
		actionBarTop.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		// Set the title of the action bar to the current date
		TextView actionBarText = (TextView) this.findViewById(R.id.textViewGeneral);
		actionBarText.setText(months[calendar.get(Calendar.MONTH)] 
				+ ", " + String.valueOf(calendar.get(Calendar.YEAR)));
		
		// Set the button
		btnNext = (Button) this.findViewById(R.id.btnNext);
		btnPrev = (Button) this.findViewById(R.id.btnPrev);
		
		btnNext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Next is clicked", Toast.LENGTH_SHORT).show();
			}
		});
		
		btnPrev.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Prev is clicked", Toast.LENGTH_SHORT).show();
			}
		});
	}	

	



}
