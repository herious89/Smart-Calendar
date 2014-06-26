package com.example.smartcalendar;

import java.util.Calendar;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;


public class DisplayMonthActivity extends Activity {
	
	public final static String CURRENT_DISPLAY_YEAR = "";
	private Button btnNext, btnPrev;
	private Calendar calendar;
	private GridView monthView;
	private MonthViewAdapter customGridAdapter;
	private final String[] months = {"January", "February", "March", "April", 
									"May", "June", "July", "August", 
									"September", "October", "November", "December"};
	private int mCurrentDisplay, yCurrentDisplay;
	private DisplayMetrics metrics;
	private TextView actionBarText;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_month);
		
		// Get the calendar using default timezone  and locale
		calendar = Calendar.getInstance(Locale.getDefault());
		// Get the grid view 
		monthView = (GridView) this.findViewById(R.id.monthView);
		// Get the current month and year
		mCurrentDisplay = calendar.get(Calendar.MONTH);
		yCurrentDisplay = calendar.get(Calendar.YEAR);
		
		// get display metrics
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		// Create and set adapter for grid view 
		customGridAdapter = new MonthViewAdapter(getApplicationContext(), R.layout.day_grid_cell, 
				calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR), metrics);
		customGridAdapter.notifyDataSetChanged();
		monthView.setAdapter(customGridAdapter);
		
		// Set the custom action bar
		ActionBar actionBarTop = getActionBar();
		actionBarTop.setCustomView(R.layout.actionbar_top_month);
		actionBarTop.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		// Set the title of the action bar to the current date
		actionBarText = (TextView) this.findViewById(R.id.titleDate);
		actionBarText.setText(months[mCurrentDisplay] + ", " + String.valueOf(yCurrentDisplay));
		
		// Set the button
		btnNext = (Button) this.findViewById(R.id.btnNextMonth);
		btnPrev = (Button) this.findViewById(R.id.btnPrevMonth);
		
		// Create click event for button next
		btnNext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Next is clicked", Toast.LENGTH_SHORT).show();
				// Check for correct date display
				if(mCurrentDisplay == 11) {
					mCurrentDisplay = 0;
					yCurrentDisplay++;
				}
				else
					mCurrentDisplay++;
				setGridCellAdapterToDate(mCurrentDisplay, yCurrentDisplay);
			}
		});
		
		// Create click event for button prev
		btnPrev.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Prev is clicked", Toast.LENGTH_SHORT).show();
				// Check for correct date display
				if(mCurrentDisplay == 0) {
					mCurrentDisplay = 11;
					yCurrentDisplay--;
				}
				else 
					mCurrentDisplay--;
				setGridCellAdapterToDate(mCurrentDisplay, yCurrentDisplay);
			}
		});
		
		// Create click event for action bar's title
		actionBarText.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Title is clicked", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplicationContext(), DisplayYearActivity.class);
				intent.putExtra(CURRENT_DISPLAY_YEAR, String.valueOf(yCurrentDisplay));
				startActivity(intent);
			}
		});
	}	
	
	private void setGridCellAdapterToDate(int month, int year)
    {
		customGridAdapter = new MonthViewAdapter(getApplicationContext(), R.layout.day_grid_cell, 
				month + 1, year, metrics);
		calendar.set(year, month, calendar.get(Calendar.DAY_OF_MONTH));
		customGridAdapter.notifyDataSetChanged();
		monthView.setAdapter(customGridAdapter);
		actionBarText.setText(months[month] + ", " + String.valueOf(year));
    }


}
