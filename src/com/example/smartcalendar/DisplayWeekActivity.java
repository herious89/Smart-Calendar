package com.example.smartcalendar;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayWeekActivity extends Activity {
	
	public final static String CURRENT_DISPLAY_YEAR = "";
	private Button btnWeekSettings;
	private ListView weekView;
	private WeekViewAdapter customListAdapter;
	private int yCurrentDisplay, mCurrentDisplay, dCurrentDisplay;
	private TextView actionBarText;
	private final String[] views = {"Year View", "Month View", "Week View"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_week);
		
		// Receive application's data
		final ApplicationData data = (ApplicationData) this.getApplicationContext();
		mCurrentDisplay = data.getMonth();
		yCurrentDisplay = data.getYear();
		dCurrentDisplay = data.getDay();
		
		// Get the custom action bar
		ActionBar actionBarTop = getActionBar();
		actionBarTop.setCustomView(R.layout.actionbar_top_week);
		actionBarTop.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		// Set the action bar's text and click event
		actionBarText = (TextView) this.findViewById(R.id.weeklyEvent);
		actionBarText.setText("Weekly Event");
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
				mCurrentDisplay + 1, yCurrentDisplay, dCurrentDisplay, true);
		customListAdapter.notifyDataSetChanged();
		weekView.setAdapter(customListAdapter);
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
	

}
