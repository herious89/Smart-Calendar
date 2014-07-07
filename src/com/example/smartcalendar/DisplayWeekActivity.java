package com.example.smartcalendar;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class DisplayWeekActivity extends Activity {
	
	private Button btnWeekSettings;
	private ListView weekView;
	private WeekViewAdapter customListAdapter;
	private int yCurrentDisplay, mCurrentDisplay;
	private TextView actionBarText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_week);
		
		final ApplicationData data = (ApplicationData) this.getApplicationContext();
		mCurrentDisplay = data.getMonth();
		yCurrentDisplay = data.getYear();
		// Get the custom action bar
		ActionBar actionBarTop = getActionBar();
		actionBarTop.setCustomView(R.layout.actionbar_top_week);
		actionBarTop.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		actionBarText = (TextView) this.findViewById(R.id.weeklyEvent);
		actionBarText.setText("Weekly Event");
		
		btnWeekSettings = (Button) this.findViewById(R.id.btnWeekSettings);
		
		weekView = (ListView) this.findViewById(R.id.weekView);
		customListAdapter = new WeekViewAdapter(getApplicationContext(), R.id.weekView, 
				mCurrentDisplay + 1, yCurrentDisplay, true);
		customListAdapter.notifyDataSetChanged();
		weekView.setAdapter(customListAdapter);
	}

	

}
