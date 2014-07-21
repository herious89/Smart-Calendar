package com.example.smartcalendar;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment 
	implements DatePickerDialog.OnDateSetListener {
	private Context mContext;

	public DatePickerFragment() {
		mContext = null;
	}
	
	public DatePickerFragment(Context context) {
		mContext = context;
	}
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final ApplicationData data = (ApplicationData) mContext;
        int year = data.getYear();
        int month = data.getMonth();
        int day = data.getDay();

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
	
	@Override
	public void onDateSet(DatePicker dPicker, int year, int month, int day) {
		Log.d("Here", year + " " + (month + 1) + " " + day);
	}
	
}
