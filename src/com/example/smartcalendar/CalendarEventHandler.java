package com.example.smartcalendar;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CalendarEventHandler extends SQLiteOpenHelper {
	
	private final static String DATABASE_NAME = "events.db";
	private final static int DATABASE_VERSION = 1;
	public static final String TABLE_EVENT = "events";
	public static final String EVENT_ID = "id";
	public static final String EVENT_TITLE = "title";
	public static final String EVENT_DATE = "date";
	public static final String EVENT_DESCRIPTION = "description";
	
	// SQL statement to create database
	private final String DATABASE_CREATE = "create table "
			+ TABLE_EVENT + "(" + EVENT_ID + " integer primary key autoincrement, " 
			+ EVENT_TITLE + " text not null, " 
			+ EVENT_DATE + " text not null, " 
			+ EVENT_DESCRIPTION + " text not null);"; 
	
	public CalendarEventHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
		onCreate(db);
	}
	
	public void addEvent(CalendarEvent event) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(EVENT_TITLE, event.getEventTitle());
		values.put(EVENT_DATE, event.getEventDate());
		values.put(EVENT_DESCRIPTION, event.getEventDescription());
		db.insert(TABLE_EVENT, null, values);
		db.close();
	}
	
	public CalendarEvent getEvent(int id) {
		CalendarEvent result = new CalendarEvent(null, null, null);
		return result;
	} 
	
	public List<CalendarEvent> getAllEvent() {
		List<CalendarEvent> result = new ArrayList<CalendarEvent>();
		String selectQuerry = "SELECT * FROM " + TABLE_EVENT;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuerry, null);
		
		if (cursor.moveToFirst()) {
			do {
				CalendarEvent event = new CalendarEvent(null, null, null);
				event.setEventID(Integer.parseInt(cursor.getString(0)));
				event.setEventTitle(cursor.getString(1));
				event.setEventDate(cursor.getString(2));
				event.setEventDescription(cursor.getString(3));
				result.add(event);
			} while (cursor.moveToNext());
		}
		
		return result;
	}
	
	public void deleteEvent(CalendarEvent event) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_EVENT, EVENT_ID + " = ?", new String[] {String.valueOf(event.getEventID())});
		db.close();
	}
}
