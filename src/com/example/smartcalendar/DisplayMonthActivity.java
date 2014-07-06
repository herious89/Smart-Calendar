package com.example.smartcalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnTouchListener;


public class DisplayMonthActivity extends Activity {
	
	public final static String CURRENT_DISPLAY_YEAR = "";
	private Button btnAdd, btnMonthSettings;
	private Calendar calendar;
	private GridView monthView;
	private MonthViewAdapter customGridAdapter;
	private final String[] months = {"January", "February", "March", "April", 
									"May", "June", "July", "August", 
									"September", "October", "November", "December"};
	private final String[] views = {"Year View", "Month View"};
	private int mCurrentDisplay, yCurrentDisplay;
	private DisplayMetrics metrics;
	private TextView actionBarText;
	private boolean firstTime = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_month);
								
		// Object for gesture detection
		final GestureDetector swipeDetector = new GestureDetector(this, new SwipeGesture(this));
		
		// Get the calendar using default timezone  and locale
		calendar = Calendar.getInstance(Locale.getDefault());
		
		// Get the grid view 
		monthView = (GridView) this.findViewById(R.id.monthView);
		
		// Get the current month and year
		mCurrentDisplay = calendar.get(Calendar.MONTH);
		yCurrentDisplay = calendar.get(Calendar.YEAR);
		
		// Get the current month and year display
		ApplicationData data = (ApplicationData) this.getApplicationContext();
		if (data.getFlaq()) {
			mCurrentDisplay = data.getMonth();
			yCurrentDisplay = data.getYear();
		}
		
		// get display metrics
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		Log.d("Here", "True or false:********" + firstTime);
		if (!firstTime) {
			Intent intent = getIntent();
			mCurrentDisplay = intent.getIntExtra(YearViewAdapter.SELECTED_MONTH, 
					calendar.get(Calendar.MONTH));
			yCurrentDisplay = intent.getIntExtra(YearViewAdapter.SELECTED_YEAR,
					calendar.get(Calendar.YEAR));
			Log.d("Here", "******" + mCurrentDisplay + yCurrentDisplay);
			setGridCellAdapterToDate(mCurrentDisplay, yCurrentDisplay);
		} 
		
		// Set the custom action bar
		ActionBar actionBarTop = getActionBar();
		actionBarTop.setCustomView(R.layout.actionbar_top_month);
		actionBarTop.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		// Set the title of the action bar to the current date
		actionBarText = (TextView) this.findViewById(R.id.titleDate);
		actionBarText.setText(months[mCurrentDisplay] + ", " + String.valueOf(yCurrentDisplay));
		
		btnAdd = (Button) this.findViewById(R.id.btnAddEvent);
		btnAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_LONG).show();
			}
		});
		
		btnMonthSettings = (Button) this.findViewById(R.id.btnMonthSettings);
		btnMonthSettings.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_LONG).show();
				final ArrayList selectedItem = new ArrayList();
				selectedItem.add(1);
				final AlertDialog.Builder viewDialog = new AlertDialog.Builder(DisplayMonthActivity.this);
				viewDialog.setTitle("Switch to...");
				viewDialog.setSingleChoiceItems(views, 1, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (!selectedItem.contains(which)) {
							selectedItem.remove(0);
							selectedItem.add(which);
						}
							
					}
				});
				
				viewDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (selectedItem.get(0).equals(0)) {
							Toast.makeText(getApplicationContext(), "Change to yearView", Toast.LENGTH_LONG).show();
							Intent intent = new Intent(getApplicationContext(), DisplayYearActivity.class);
							intent.putExtra(CURRENT_DISPLAY_YEAR, yCurrentDisplay);
							startActivity(intent);
						} else
							Toast.makeText(getApplicationContext(), "Stay at monthView", Toast.LENGTH_LONG).show();
						dialog.cancel();
					}
					
				});
				
				AlertDialog dialog = viewDialog.create();
				dialog.show();
			}
		});
			
		
		// Create click event for action bar's title
		actionBarText.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), DisplayYearActivity.class);
				intent.putExtra(CURRENT_DISPLAY_YEAR, yCurrentDisplay);
				startActivity(intent);
			}
		});
		
		// Create and set adapter for grid view 
		customGridAdapter = new MonthViewAdapter(getApplicationContext(), R.layout.day_grid_cell, 
				mCurrentDisplay + 1, yCurrentDisplay, metrics, true);
		customGridAdapter.notifyDataSetChanged();
		monthView.setAdapter(customGridAdapter);
		monthView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return swipeDetector.onTouchEvent(event);
			}
		});
	}	
	
	
	
	private void setGridCellAdapterToDate(int month, int year)
    {
		customGridAdapter = new MonthViewAdapter(getApplicationContext(), R.layout.day_grid_cell, 
				month + 1, year, metrics, true);
		calendar.set(year, month, calendar.get(Calendar.DAY_OF_MONTH));
		customGridAdapter.notifyDataSetChanged();
		monthView.setAdapter(customGridAdapter);
		actionBarText.setText(months[month] + ", " + String.valueOf(year));
    }
	
	private final class SwipeGesture extends SimpleOnGestureListener {
		private final int swipeMinDistance;
		private final int swipeThresholdVelocity;

		public SwipeGesture(Context context) {
			final ViewConfiguration viewConfig = ViewConfiguration.get(context);
			swipeMinDistance = viewConfig.getScaledTouchSlop();
			swipeThresholdVelocity = viewConfig.getScaledMinimumFlingVelocity();
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	        if (e1.getX() - e2.getX() > swipeMinDistance && Math.abs(velocityX) > swipeThresholdVelocity) {
	            Toast.makeText(getApplicationContext(), "Next", Toast.LENGTH_SHORT).show();
	            if(mCurrentDisplay == 11) {
					mCurrentDisplay = 0;
					yCurrentDisplay++;
				}
				else
					mCurrentDisplay++;
				setGridCellAdapterToDate(mCurrentDisplay, yCurrentDisplay);
	        }  else if (e2.getX() - e1.getX() > swipeMinDistance && Math.abs(velocityX) > swipeThresholdVelocity) {
	            Toast.makeText(getApplicationContext(), "Prev", Toast.LENGTH_SHORT).show();
	            if(mCurrentDisplay == 0) {
					mCurrentDisplay = 11;
					yCurrentDisplay--;
				}
				else 
					mCurrentDisplay--;
				setGridCellAdapterToDate(mCurrentDisplay, yCurrentDisplay);
	        }
	        return false;
		}
	}
}
