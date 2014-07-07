
package com.example.smartcalendar;




import java.util.ArrayList;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


public class DisplayYearActivity extends Activity {
	public final static String CURRENT_DISPLAY_YEAR = "";
	private TextView actionBarText;
	private GridView yearView;
	private YearViewAdapter yearGridAdapter;
	private Button btnNextYear, btnPrevYear, btnYearSettings;
	private int yCurrentDisplay;
	private DisplayMetrics metrics;
	private final String[] views = {"Year View", "Month View"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_year);
		
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
		
		// Set the buttons
		btnNextYear = (Button) this.findViewById(R.id.btnNextYear);
		btnPrevYear = (Button) this.findViewById(R.id.btnPrevYear);
		
		btnNextYear.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				yCurrentDisplay++;
				new SetGridCellAdapterToDate().execute();
			}
		});
		
		btnPrevYear.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				yCurrentDisplay--;
				new SetGridCellAdapterToDate().execute();
			}
		});
		
		btnYearSettings = (Button) this.findViewById(R.id.btnYearSettings);
		btnYearSettings.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_LONG).show();
				final ArrayList selectedItem = new ArrayList();
				selectedItem.add(1);
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
						if (selectedItem.get(0).equals(1)) {
							Toast.makeText(getApplicationContext(), "Change to monthView", Toast.LENGTH_LONG).show();
							Intent intent = new Intent(getApplicationContext(), DisplayMonthActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							ApplicationData data = (ApplicationData) getApplicationContext();
							data.setFlaq(true);
							data.setMonth(0);
							data.setYear(yCurrentDisplay);
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
	
	private void setGridCellAdapterToDate(int year)
    {
		yearGridAdapter = new YearViewAdapter(getApplicationContext(), R.layout.month_grid_cell, 
				year, metrics);
		yearGridAdapter.notifyDataSetChanged();
		yearView.setAdapter(yearGridAdapter);
		actionBarText.setText(String.valueOf(year));
    }
	
	private class SetGridCellAdapterToDate extends AsyncTask<Integer, Void, Void> {
		
		private int year;
		private ProgressDialog dialog = new ProgressDialog(DisplayYearActivity.this);
		private Handler myHandler;
		
		@Override
		protected void onPreExecute() {
			myHandler = new Handler();
			year = yCurrentDisplay;
	        this.dialog.setMessage("Please wait...");
	        this.dialog.show();
	    }
		
		@Override
		protected Void doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			 myHandler.post(new Runnable() {
		            public void run() {
		            	setGridCellAdapterToDate(year);
		            }
		    });
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			this.dialog.dismiss();
		}		
	}

}
