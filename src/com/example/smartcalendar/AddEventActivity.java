package com.example.smartcalendar;

import java.util.List;

import android.app.Activity;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddEventActivity extends Activity {
	
	private String currentSelectedDate;
	private TextView fromField, toField;
	private Button btnCancel, btnAdd;
	private EditText editTitle, editDescription;
	private CalendarEventHandler handler;
	private List<CalendarEvent> events;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);
		
		
		handler = new CalendarEventHandler(this);

		
		// Get the current display year from intent
		Intent intent = getIntent();
		currentSelectedDate = intent.getStringExtra(DisplayMonthActivity.CURRENT_SELECTED_DATE);
		
		// Set the custom action bar
		ActionBar actionBarTop = getActionBar();
		actionBarTop.setCustomView(R.layout.actionbar_top_add_events);
		actionBarTop.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		fromField = (TextView) this.findViewById(R.id.eventDateFrom);
		fromField.setText("From: " + currentSelectedDate);
		
		toField = (TextView) this.findViewById(R.id.eventDateTo);
		toField.setText("To: " + currentSelectedDate);
		
		editTitle = (EditText) this.findViewById(R.id.eventTitle_box);
		
		editDescription = (EditText) this.findViewById(R.id.eventDescription_box);

		
		btnCancel = (Button) this.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		btnAdd = (Button) this.findViewById(R.id.btnAddEvent);
		btnAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("Here", currentSelectedDate);
				handler.addEvent(new CalendarEvent(editTitle.getText().toString(), 
						currentSelectedDate, editDescription.getText().toString()));
				events = handler.getAllEvent();
				for (CalendarEvent e : events) {
					String log = "Id: " + e.getEventID() + " title: " + e.getEventTitle() 
							+ " date: " + e.getEventDate() + " des: " + e.getEventDescription();
					Log.d("Here", log);
				}
				finish();
			}
		});
	}

	
}
