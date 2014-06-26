package com.example.smartcalendar;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.os.Build;

public class DisplayYearActivity extends Activity {

	private TextView actionBarText;
//	private GridView janView, febView, marView, aprView, mayView, junView, julView,
//	augView, sepView, octView, novView, decView;
//	private MonthViewAdapter customGridAdapter;
	private GridView yearView;
//	private CustomGridView yearView;
	private YearViewAdapter yearGridAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_year);
		
		DisplayMetrics metrics = new DisplayMetrics();
		
		// Set custom action bar
		ActionBar actionBarTop = getActionBar();
		actionBarTop.setCustomView(R.layout.actionbar_top_year);
		actionBarTop.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	
		// Get the current display year from intent
		Intent intent = getIntent();
		String yCurrentDisplay = intent.getStringExtra(DisplayMonthActivity.CURRENT_DISPLAY_YEAR);
	
		// Set the title of the action bar to the current year
		actionBarText = (TextView) this.findViewById(R.id.titleYear);
		actionBarText.setText(yCurrentDisplay);
		
		// Set the grid view
		yearView = (GridView) this.findViewById(R.id.yearView);
//		yearView = (CustomGridView) this.findViewById(R.id.yearView);
		yearGridAdapter = new YearViewAdapter(getApplicationContext(), R.layout.month_grid_cell,
						Integer.parseInt(yCurrentDisplay));
		yearGridAdapter.notifyDataSetChanged();
		yearView.setAdapter(yearGridAdapter);
		
//		janView = (GridView) this.findViewById(R.id.januaryView);
//		customGridAdapter = new MonthViewAdapter(getApplicationContext(), R.layout.day_grid_cell,
//				1, Integer.parseInt(yCurrentDisplay), metrics, false);
//		customGridAdapter.notifyDataSetChanged();
//		janView.setAdapter(customGridAdapter);
//		
//		febView = (GridView) this.findViewById(R.id.februaryView);
//		customGridAdapter = new MonthViewAdapter(getApplicationContext(), R.layout.day_grid_cell,
//				1, Integer.parseInt(yCurrentDisplay), metrics, false);
//		customGridAdapter.notifyDataSetChanged();
//		febView.setAdapter(customGridAdapter);
//		
//		marView = (GridView) this.findViewById(R.id.marchView);
//		customGridAdapter = new MonthViewAdapter(getApplicationContext(), R.layout.day_grid_cell,
//				1, Integer.parseInt(yCurrentDisplay), metrics, false);
//		customGridAdapter.notifyDataSetChanged();
//		marView.setAdapter(customGridAdapter);
	}


}
