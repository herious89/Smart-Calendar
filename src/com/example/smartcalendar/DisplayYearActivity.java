
package com.example.smartcalendar;




import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnTouchListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;




public class DisplayYearActivity extends Activity {
	public final static String CURRENT_DISPLAY_YEAR = "";
	private TextView actionBarText;
	private GridView yearView;
	private YearViewAdapter yearGridAdapter;
	private Button btnYearSettings;
	private int yCurrentDisplay;
	private DisplayMetrics metrics;
	private final String[] views = {"Year View", "Month View", "Week View"};
	private GestureDetector swipeDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_year);
		
		// Object for gesture detection
		swipeDetector = new GestureDetector(this, new YearSwipeGesture(this));
		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		// Set custom action bar
		ActionBar actionBarTop = getActionBar();
		actionBarTop.setCustomView(R.layout.actionbar_top_year);
		actionBarTop.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	
		// Get the current display year from intent
		Intent intent = getIntent();
		yCurrentDisplay = intent.getIntExtra(DisplayMonthActivity.CURRENT_DISPLAY_YEAR, 0);
	
		// Set the title of the action bar to the current year
		actionBarText = (TextView) this.findViewById(R.id.titleYear);
		actionBarText.setText(String.valueOf(yCurrentDisplay));
		
		// Set the grid view
		yearView = (GridView) this.findViewById(R.id.yearView);
		yearGridAdapter = new YearViewAdapter(getApplicationContext(), R.layout.month_grid_cell,
						yCurrentDisplay, metrics);
		yearGridAdapter.notifyDataSetChanged();
		yearView.setAdapter(yearGridAdapter);
		yearView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return swipeDetector.onTouchEvent(event);
			}
		});
		
		
		btnYearSettings = (Button) this.findViewById(R.id.btnYearSettings);
		btnYearSettings.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_LONG).show();
				final ArrayList<Integer> selectedItem = new ArrayList<Integer>();
				selectedItem.add(0);
				final AlertDialog.Builder viewDialog = new AlertDialog.Builder(DisplayYearActivity.this);
				viewDialog.setTitle("Switch to...");
				viewDialog.setSingleChoiceItems(views, 0, new DialogInterface.OnClickListener() {
					
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
						if (selectedItem.get(0) == 1) {
							Toast.makeText(getApplicationContext(), "Change to monthView", Toast.LENGTH_LONG).show();
							Intent intent = new Intent(getApplicationContext(), DisplayMonthActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							ApplicationData data = (ApplicationData) getApplicationContext();
							data.setFlaq(true);
							data.setMonth(0);
							data.setYear(yCurrentDisplay);
							startActivity(intent);
						} else if (selectedItem.get(0) == 2) {
							Toast.makeText(getApplicationContext(), "Change to weekView", Toast.LENGTH_LONG).show();
							Intent intent = new Intent(getApplicationContext(), DisplayWeekActivity.class);
							ApplicationData data = (ApplicationData) getApplicationContext();
							Calendar calendar = Calendar.getInstance();
							data.setMonth(calendar.get(Calendar.MONTH));
							data.setYear(calendar.get(Calendar.YEAR));
							startActivity(intent);
						} else
							Toast.makeText(getApplicationContext(), "Stay at yearView", Toast.LENGTH_LONG).show();
						dialog.cancel();
					}
					
				});
				
				AlertDialog dialog = viewDialog.create();
				dialog.show();
			}
		});
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev){
	    super.dispatchTouchEvent(ev);
	    return swipeDetector.onTouchEvent(ev);
	} 
	
	private class SetGridCellAdapterToDate extends AsyncTask<Integer, Void, Void> {
		
		private int year;
		private Handler myHandler;
		
		@Override
		protected void onPreExecute() {
			myHandler = new Handler();
			year = yCurrentDisplay;
	    }
		
		@Override
		protected Void doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			 myHandler.post(new Runnable() {
		            public void run() {
//		            	setGridCellAdapterToDate(year);
		        		actionBarText.setText(String.valueOf(year));
		        		yearGridAdapter = new YearViewAdapter(getApplicationContext(), R.layout.month_grid_cell, 
		        				year, metrics);
		    			yearGridAdapter.notifyDataSetChanged();
		        		yearView.setAdapter(yearGridAdapter);
		            }
		    });
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			
		}		
	}
	
	private final class YearSwipeGesture extends SimpleOnGestureListener {
		private final int swipeMinDistance;
		private final int swipeThresholdVelocity;
		public YearSwipeGesture(Context context) {
			final ViewConfiguration viewConfig = ViewConfiguration.get(context);
			swipeMinDistance = viewConfig.getScaledTouchSlop();
			swipeThresholdVelocity = viewConfig.getScaledMinimumFlingVelocity();
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	        if (e1.getX() - e2.getX() > swipeMinDistance && Math.abs(velocityX) > swipeThresholdVelocity) {
	            Toast.makeText(getApplicationContext(), "Next", Toast.LENGTH_SHORT).show();
	            yCurrentDisplay++;
	            new SetGridCellAdapterToDate().execute();
	        }  else if (e2.getX() - e1.getX() > swipeMinDistance && Math.abs(velocityX) > swipeThresholdVelocity) {
	            Toast.makeText(getApplicationContext(), "Prev", Toast.LENGTH_SHORT).show();
	            yCurrentDisplay--;
	            new SetGridCellAdapterToDate().execute();
	        }
	        return false;
		}
	}

}
