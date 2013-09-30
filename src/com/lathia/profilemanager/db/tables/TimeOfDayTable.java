package com.lathia.profilemanager.db.tables;

import java.util.Calendar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.lathia.surveymanager.data.answers.AbstractAnswer;


public class TimeOfDayTable extends AbstractQuestionTable
{
	private final static String timeBinDescription = "timeBinDescription";
	private final static String timeBinFrequency = "timeBinFrequency";
	
	public TimeOfDayTable(final String surveyId)
	{
		super(surveyId+"_TimeOfDayTable", null);
	}

	@Override
	public void createTable(final SQLiteDatabase database)
	{
		database.execSQL("CREATE TABLE IF NOT EXISTS " + tableName
				+ " ("
				+ timeBinDescription + " TEXT NOT NULL, "
				+ timeBinFrequency + " INTEGER DEFAULT 0"
				+ ");");
	}
	
	public void addEntry(final SQLiteDatabase database)
	{
		try
		{
			Calendar calendar = Calendar.getInstance();
			String hour_of_day = ""+calendar.get(Calendar.HOUR_OF_DAY);
			incrementField(database, timeBinFrequency, timeBinDescription, hour_of_day);
		}
		catch (SQLiteException e)
		{
			if (e.getMessage().toString().contains("no such table"))
			{
				createTable(database);
				Calendar calendar = Calendar.getInstance();
				String hour_of_day = ""+calendar.get(Calendar.HOUR_OF_DAY);
				incrementField(database, timeBinFrequency, timeBinDescription, hour_of_day);
			}
		}
	}

	@Override
	protected String getCategoryDescription(Cursor cursor)
	{
		int descriptionIndex = cursor.getColumnIndex(timeBinDescription);
		int hour_of_day = Integer.valueOf(cursor.getString(descriptionIndex));
		return getTime(hour_of_day)+"-"+getTime(hour_of_day + 1);
	}
	
	private String getTime(int hour)
	{
		if (hour < 10)
		{
			return "0"+hour+":00";
		}
		else
		{
			return hour+":00";
		}
	}

	@Override
	protected int getCategoryFrequency(Cursor cursor)
	{
		int frequencyIndex = cursor.getColumnIndex(timeBinFrequency);
		return cursor.getInt(frequencyIndex);
	}

	@Override
	public void addEntry(SQLiteDatabase database, AbstractAnswer answer)
	{
		throw new NullPointerException("Do not call addEntry(SQLiteDatabase db, AbstractAnswer ans) on time of day table");
	}
}
