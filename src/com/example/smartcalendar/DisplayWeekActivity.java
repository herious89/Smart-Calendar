package com.example.smartcalendar;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayWeekActivity extends Activity {
	
	public final static String CURRENT_DISPLAY_YEAR = "";
	private Button btnWeekSettings;
	private ListView weekView;
	private WeekViewAdapter customListAdapter;
	private int yCurrentDisplay, mCurrentDisplay, dCurrentDisplay, wCurrentDisplay;
	private TextView actionBarText;
	private final String[] views = {"Year View", "Month View", "Week View"};
	private ApplicationData data;
	private Calendar calendar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_week);
		
		
		
		// Receive application's data
		data = (ApplicationData) this.getApplicationContext();
		mCurrentDisplay = data.getMonth();
		yCurrentDisplay = data.getYear();
		dCurrentDisplay = data.getDay();
		
		// Get the current week number
		calendar = Calendar.getInstance();
		calendar.set(yCurrentDisplay, mCurrentDisplay, dCurrentDisplay);
		wCurrentDisplay =  calendar.get(Calendar.WEEK_OF_MONTH);
        int totalWeeks = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
        Log.d("Here: ", "Number of weeks: " + totalWeeks);
		// Get the custom action bar
		ActionBar actionBarTop = getActionBar();
		actionBarTop.setCustomView(R.layout.actionbar_top_week);
		actionBarTop.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		
		// Set the action bar's text and click event
		actionBarText = (TextView) this.findViewById(R.id.weeklyEvent);
		actionBarText.setText(data.displayWeekPeriod(mCurrentDisplay + 1, yCurrentDisplay, wCurrentDisplay));
		actionBarText.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "event is clicked", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplicationContext(), DisplayMonthActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				ApplicationData data = (ApplicationData) getApplicationContext();
				data.setFlaq(true);
				data.setMonth(mCurrentDisplay);
				data.setYear(yCurrentDisplay);
				startActivity(intent);
			}
		});
		
		// Set the setting button 
		btnWeekSettings = (Button) this.findViewById(R.id.btnWeekSettings);
		btnWeekSettings.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "setting is clicked", Toast.LENGTH_SHORT).show();
				final ArrayList<Integer> selectedItem = new ArrayList<Integer>();
				selectedItem.add(2);
				final AlertDialog.Builder viewDialog = new AlertDialog.Builder(DisplayWeekActivity.this);
				viewDialog.setTitle("Switch to...");
				viewDialog.setSingleChoiceItems(views, 2, new DialogInterface.OnClickListener() {
					
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
						} else if (selectedItem.get(0) == 1) {
							Toast.makeText(getApplicationContext(), "Change to monthView", Toast.LENGTH_LONG).show();
							Intent intent = new Intent(getApplicationContext(), DisplayMonthActivity.class);
							data.setMonth(mCurrentDisplay);
							data.setYear(yCurrentDisplay);
							data.setFlaq(true);
							startActivity(intent);
						} else
							Toast.makeText(getApplicationContext(), "Stay at weekView", Toast.LENGTH_LONG).show();
						dialog.cancel();
					}
					
				});
				
				AlertDialog dialog = viewDialog.create();
				dialog.show();
			}
		});
		
		// Create and set the custom view
		weekView = (ListView) this.findViewById(R.id.weekView);
		customListAdapter = new WeekViewAdapter(getApplicationContext(), R.id.weekView, 
				mCurrentDisplay + 1, yCurrentDisplay, dCurrentDisplay, wCurrentDisplay, true);
		customListAdapter.notifyDataSetChanged();
		weekView.setAdapter(customListAdapter);
	}
	
	private void setListAdapterToDate(int month, int year, int day, int week) {
		customListAdapter = new WeekViewAdapter(getApplicationContext(), R.id.weekView, 
				month + 1, year, day, week, true);
		customListAdapter.notifyDataSetChanged();
		weekView.setAdapter(customListAdapter);
		calendar.set(month, year, day);
		actionBarText.setText(data.displayWeekPeriod(month + 1, year, 
				calendar.get(Calendar.WEEK_OF_MONTH)));
    }
	
	@Override
    public void onBackPressed() {
            super.onBackPressed();
            Intent intent = new Intent(getApplicationContext(), DisplayMonthActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			ApplicationData data = (ApplicationData) getApplicationContext();
			data.setFlaq(true);
			data.setMonth(mCurrentDisplay);
			data.setYear(yCurrentDisplay);
			startActivity(intent);
    }
	
	private class SwitchToYearView extends AsyncTask<Integer, Void, Void> {
		
		private int year;
		private ProgressDialog dialog = new ProgressDialog(DisplayWeekActivity.this);
		
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
				setListAdapterToDate(mCurrentDisplay, yCurrentDisplay, dCurrentDisplay, wCurrentDisplay);
	        }  else if (e2.getX() - e1.getX() > swipeMinDistance && Math.abs(velocityX) > swipeThresholdVelocity) {
	            Toast.makeText(getApplicationContext(), "Prev", Toast.LENGTH_SHORT).show();
	            if(mCurrentDisplay == 0) {
					mCurrentDisplay = 11;
					yCurrentDisplay--;
				}
				else 
					mCurrentDisplay--;
	            setListAdapterToDate(mCurrentDisplay, yCurrentDisplay, dCurrentDisplay, wCurrentDisplay);
	        }
	        return false;
		}
	}
}
