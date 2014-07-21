package com.example.smartcalendar;

import java.util.List;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class AddEventActivity extends FragmentActivity {
	
	private static String currentSelectedDate;
	private static Button btnDateFrom, btnDateTo, btnTimeFrom, 
		btnTimeTo, btnCancel, btnAdd;
	private static boolean btnFlag = false;
	private EditText editTitle, editDescription;
	private CalendarEventHandler handler;
	private List<CalendarEvent> events;
	private static ApplicationData data;
	private static String[] months = {"January", "February", "March", "April", "May", "June", 
			"July", "August", "September", "October", "November", "December"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);
		
		data = (ApplicationData) this.getApplicationContext();
		
		// Call the event database
		handler = new CalendarEventHandler(this);
		
		// Get the current display year from intent
		Intent intent = getIntent();
		currentSelectedDate = intent.getStringExtra(DisplayMonthActivity.CURRENT_SELECTED_DATE);
		
		// Set the custom action bar
		ActionBar actionBarTop = getActionBar();
		actionBarTop.setCustomView(R.layout.actionbar_top_add_events);
		actionBarTop.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		// Set the date fields
		btnDateFrom = (Button) this.findViewById(R.id.btnDateFrom);
		btnDateFrom.setText("From: " + currentSelectedDate);
		btnDateTo = (Button) this.findViewById(R.id.btnDateTo);
		btnDateTo.setText("To: " + currentSelectedDate);
		btnTimeFrom = (Button) this.findViewById(R.id.btnFromTime);
		btnTimeFrom.setText("12:00AM");
		btnTimeTo = (Button) this.findViewById(R.id.btnToTime);
		btnTimeTo.setText("12:00AM");
		
		btnDateFrom.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnFlag = true;
				showDatePickerDialog(v);
			}
		});
		
		btnDateTo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnFlag = false;
				showDatePickerDialog(v);
			}
		});
		
		// Set the title and description text box
		editTitle = (EditText) this.findViewById(R.id.eventTitle_box);
		editDescription = (EditText) this.findViewById(R.id.eventDescription_box);

		// Set the cancel button
		btnCancel = (Button) this.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		// Set the add button
		btnAdd = (Button) this.findViewById(R.id.btnAddEvent);
		btnAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (editTitle.getText().toString().equals("")) {
					AlertDialog.Builder alert = new AlertDialog.Builder(AddEventActivity.this);
		            alert.setTitle("Error");
		            alert.setMessage("Please enter a valid title!");
		            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int id) {
		                    dialog.cancel();
		                }
		            });
		            alert.show();
				}
				else {
					Log.d("Here", currentSelectedDate);
					// Add the event to the database
					handler.addEvent(new CalendarEvent(editTitle.getText().toString(), 
							currentSelectedDate, editDescription.getText().toString()));
					// Display events in logcat
					events = handler.getAllEvents();
					for (CalendarEvent e : events) {
						String log = "Id: " + e.getEventID() + " title: " + e.getEventTitle() 
								+ " date: " + e.getEventDate() + " des: " + e.getEventDescription();
						Log.d("Here", log);
					}
					finish();
				}
			}
		});
	}
	
	
	@Override
    public void onBackPressed() {
		finish();
    }
	
	
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "datePicker");
	}


	
	
	public static class DatePickerFragment extends DialogFragment 
		implements DatePickerDialog.OnDateSetListener {
		
		private int year, month, day, eYear, eMonth, eDay;	
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			year = data.getYear();
			month = data.getMonth();
			day = data.getDay();
			eYear = data.getYear();
			eMonth = data.getMonth();
			eDay = data.getDay();

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		
		@Override
		public void onDateSet(DatePicker view, int year, int month,
				int day) {
			// TODO Auto-generated method stub
			Log.d("Here", currentSelectedDate);
			currentSelectedDate = months[month] + " " + day + ", " + year;
			if (btnFlag) {
				data.setYear(year);
				data.setMonth(month);
				data.setDay(day);
				if (!compareDate(eYear, eMonth, eDay)) {
					btnDateFrom.setText("From: " + currentSelectedDate);
					btnDateTo.setText("To: " + currentSelectedDate);
				} else
					btnDateFrom.setText("From: " + currentSelectedDate);
			}
			else {
				String toDate = months[month] + " " + day + ", " + year;
				if (compareDate(year, month, day)) {
					btnDateTo.setText("To: " + toDate);
					eDay = day;
					eMonth = month;
					eYear = year;
				}
				else
					Toast.makeText(getActivity().getApplicationContext(), 
							"Start date cannot be after end date", Toast.LENGTH_SHORT).show();
			}
		}
		
		/**
		 * The compareDate method checks whether start date is before
		 * end date
		 * @param y The input year
		 * @param m The input month
		 * @param d The input day
		 * @return true If start date is before end date, 
		 * false otherwise
		 */
		public boolean compareDate(int y, int m, int d) {
			if (y < data.getYear())
				return false;
			else if (y == data.getYear()) {
				if (m < data.getMonth())
					return false;
				else if (m == data.getMonth()) {
					if (d < data.getDay())
						return false;
					else 
						return true;
				} else
					return true;
			} else
				return true;
		} 
	}
}
