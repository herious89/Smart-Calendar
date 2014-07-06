package com.example.smartcalendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CalendarSQLiteHelper extends SQLiteOpenHelper {
	
	private final static String DATABASE_NAME = "events.db";
	private final static int DATABASE_VERSION = 1;
	public static final String TABLE_EVENT = "events";
	public static final String EVENT_ID = "";
	public static final String EVENT_NAME = "";
	
	// SQL statement to create database
	private final String DATABASE_CREATE = "create table "
			+ TABLE_EVENT + "(" + EVENT_ID
			+ "integer primary key autoincrement, " + EVENT_NAME 
			+ "text not null"; 
	
	public CalendarSQLiteHelper(Context context) {
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
}
