package com.example.smartcalendar;



import android.app.Activity;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class DisplayYearActivity extends Activity {

	private TextView actionBarText;
	private GridView yearView;
	private YearViewAdapter yearGridAdapter;
	private Button btnNextYear, btnPrevYear;
	private int yCurrentDisplay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_year);
		
		final GestureDetector swipeDetector = new GestureDetector(this, new SwipeGesture(this));
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
						yCurrentDisplay);
		yearGridAdapter.notifyDataSetChanged();
		yearView.setAdapter(yearGridAdapter);
		yearView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return swipeDetector.onTouchEvent(event);
			}
		});
		
		// Set the buttons
		btnNextYear = (Button) this.findViewById(R.id.btnNextYear);
		btnPrevYear = (Button) this.findViewById(R.id.btnPrevYear);
		
		btnNextYear.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				yCurrentDisplay++;
				setGridCellAdapterToDate(yCurrentDisplay);
			}
		});
		
		btnPrevYear.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				yCurrentDisplay--;
				setGridCellAdapterToDate(yCurrentDisplay);
			}
		});
	}
	
	private void setGridCellAdapterToDate(int year)
    {
		yearGridAdapter = new YearViewAdapter(getApplicationContext(), R.layout.month_grid_cell, 
				year);
		yearGridAdapter.notifyDataSetChanged();
		yearView.setAdapter(yearGridAdapter);
		actionBarText.setText(String.valueOf(year));
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
	            yCurrentDisplay++;
				setGridCellAdapterToDate(yCurrentDisplay);
	        }  else if (e2.getX() - e1.getX() > swipeMinDistance && Math.abs(velocityX) > swipeThresholdVelocity) {
	            Toast.makeText(getApplicationContext(), "Prev", Toast.LENGTH_SHORT).show();
	            yCurrentDisplay--;
				setGridCellAdapterToDate(yCurrentDisplay);
	        }
	        return false;
		}
	}

}
