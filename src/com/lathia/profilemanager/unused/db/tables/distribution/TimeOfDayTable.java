package com.lathia.profilemanager.unused.db.tables.distribution;

import java.util.Calendar;

import com.lathia.profilemanager.db.tables.FrequencyTable;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class TimeOfDayTable extends FrequencyTable
{
	private final static String timeBinDescription = "timeBinDescription";
	private final static String timeBinFrequency = "timeBinFrequency";
	
	public TimeOfDayTable(final String databaseId)
	{
		super(databaseId+"_TimeOfDayTable");
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
	
	public void incrementNow(final SQLiteDatabase database, final int amount)
	{
		try
		{
			incrementField(database, getVariableName(), amount);
		}
		catch (SQLiteException e)
		{
			if (e.getMessage().toString().contains("no such table"))
			{
				createTable(database);
				incrementField(database, getVariableName(), amount);
			}
		}
	}
	
	private String getVariableName()
	{
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		return getTime(hour)+"-"+getTime(hour+1);
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
}
