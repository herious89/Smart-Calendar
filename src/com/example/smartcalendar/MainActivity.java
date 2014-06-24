package com.example.smartcalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;


public class MainActivity extends Activity {
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
		calendar = Calendar.getInstance(Locale.getDefault());
		calendarView = (GridView) this.findViewById(R.id.calendar);
		
		// get display metrics
		final DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		customGridAdapter = new GridViewAdapter(getApplicationContext(), R.layout.row_grid_view, 
				calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR), metrics);
		customGridAdapter.notifyDataSetChanged();
		calendarView.setAdapter(customGridAdapter);
		getActionBar().setTitle(months[calendar.get(Calendar.MONTH)] 
				+ ", " + String.valueOf(calendar.get(Calendar.YEAR)));
		
	}	

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
