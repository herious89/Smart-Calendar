package com.example.smartcalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.widget.AdapterView.OnItemClickListener;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.AdapterView;
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
	public final static String CURRENT_DISPLAY_MONTH = "";
	public final static String CURRENT_SELECTED_DATE = "";
	private Button btnAdd, btnMonthSettings;
	private Calendar calendar;
	private GridView monthView;
	private MonthViewAdapter customGridAdapter;
	private final String[] months = {"January", "February", "March", "April", 
									"May", "June", "July", "August", 
									"September", "October", "November", "December"};
	private final String[] views = {"Year View", "Month View", "Week View"};
	private int mCurrentDisplay, yCurrentDisplay, dCurrentDisplay;
	private DisplayMetrics metrics;
	private TextView actionBarText;
	private boolean firstTime = true;
	private String currentSelectedDate;

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
		
		// Get the current date
		mCurrentDisplay = calendar.get(Calendar.MONTH);
		yCurrentDisplay = calendar.get(Calendar.YEAR);
		dCurrentDisplay = calendar.get(Calendar.DATE);
		
		// Set the current selected date
		currentSelectedDate = calendar.get(Calendar.DATE) + "-CURRENT-" + months[mCurrentDisplay] 
				+ "-" + yCurrentDisplay;
		
		// Get the current month and year display
		final ApplicationData data = (ApplicationData) this.getApplicationContext();
		data.setDay(dCurrentDisplay);
		data.setMonth(mCurrentDisplay);
		data.setYear(yCurrentDisplay);
		if (data.getFlaq()) {
			mCurrentDisplay = data.getMonth();
			yCurrentDisplay = data.getYear();
		}
		
		// get display metrics
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
			
		// Set the custom action bar
		ActionBar actionBarTop = getActionBar();
		actionBarTop.setCustomView(R.layout.actionbar_top_month);
		actionBarTop.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		// Set the title of the action bar to the current date
		actionBarText = (TextView) this.findViewById(R.id.titleDate);
		actionBarText.setText(months[mCurrentDisplay] + ", " + String.valueOf(yCurrentDisplay));
		
		// Initialize and set event for add event button
		btnAdd = (Button) this.findViewById(R.id.btnAddEvent);
		btnAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(getApplicationContext(), AddEventActivity.class);
				intent.putExtra(CURRENT_SELECTED_DATE, data.convertDate(currentSelectedDate, false));
				startActivity(intent);
			}
		});
		
		// Initialize and set event for setting button
		btnMonthSettings = (Button) this.findViewById(R.id.btnMonthSettings);
		btnMonthSettings.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_LONG).show();
				final ArrayList<Integer> selectedItem = new ArrayList<Integer>();
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
						if (selectedItem.get(0) == 0) {
							Toast.makeText(getApplicationContext(), "Change to yearView", Toast.LENGTH_LONG).show();
							new SwitchToYearView().execute();
						} else if (selectedItem.get(0) == 2) {
							Toast.makeText(getApplicationContext(), "Change to weekView", Toast.LENGTH_LONG).show();
							Intent intent = new Intent(getApplicationContext(), DisplayWeekActivity.class);
							data.setMonth(mCurrentDisplay);
							data.setYear(yCurrentDisplay);
							data.setDay(dCurrentDisplay);
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
				new SwitchToYearView().execute();
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
		// Set event for each cell of grid view
		monthView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				MonthViewAdapter adapter = (MonthViewAdapter) parent.getAdapter();
				adapter.setSelectedItem(position);
				adapter.notifyDataSetChanged();
				data.setRawDate(adapter.getItem(position).split("-"));
				Toast.makeText(getApplicationContext(), adapter.getItem(position), Toast.LENGTH_LONG).show();
				currentSelectedDate = adapter.getItem(position);
				if (adapter.getItem(position).split("-")[1].equals("PREV")) {
					dCurrentDisplay = Integer.parseInt(data.convertRawDate(adapter.getItem(position))[0]);
					if (mCurrentDisplay == 0) {
						yCurrentDisplay--;
						mCurrentDisplay = 11;
					} else
						mCurrentDisplay--;
					setGridCellAdapterToDate(mCurrentDisplay, yCurrentDisplay);
				} else if (adapter.getItem(position).split("-")[1].equals("NEXT")) {
					dCurrentDisplay = Integer.parseInt(data.convertRawDate(adapter.getItem(position))[0]);
					if (mCurrentDisplay == 11) {
						yCurrentDisplay++;
						mCurrentDisplay = 0;
					} else
						mCurrentDisplay++;
					setGridCellAdapterToDate(mCurrentDisplay, yCurrentDisplay);
				} else
					dCurrentDisplay = Integer.parseInt(data.convertRawDate(adapter.getItem(position))[0]);
			}
			
		});
	}	
	
	
	
	private void setGridCellAdapterToDate(int month, int year) {
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
	
	private class SwitchToYearView extends AsyncTask<Integer, Void, Void> {
		
		private int year;
		private ProgressDialog dialog = new ProgressDialog(DisplayMonthActivity.this);
		
		@Override
		protected void onPreExecute() {
			year = yCurrentDisplay;
	        this.dialog.setMessage("Loading year view. Please wait...");
	        this.dialog.show();
	    }
		
		@Override
		protected Void doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getApplicationContext(), DisplayYearActivity.class);
			intent.putExtra(CURRENT_DISPLAY_YEAR, year);
			startActivity(intent);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			this.dialog.dismiss();
		}		
	}
}
