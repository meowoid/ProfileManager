package com.lathia.profilemanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.lathia.profilemanager.data.Distribution;
import com.lathia.profilemanager.db.tables.FrequencyTable;
import com.lathia.profilemanager.db.tables.TimeOfDayTable;

public class FrequencyDatabase extends SQLiteOpenHelper
{
	private final static int dbVersion = 1;
	private final TimeOfDayTable timeOfDayTable;
	private final FrequencyTable frequencyTable;
	
	public FrequencyDatabase(final Context context, final String databaseId)
	{
		super(context, databaseId, null, dbVersion);
		timeOfDayTable = new TimeOfDayTable(databaseId);
		frequencyTable = new FrequencyTable(databaseId);
	}
	
	public void deleteAll()
	{
		SQLiteDatabase database = getWritableDatabase();
		timeOfDayTable.dropTable(database);
		frequencyTable.dropTable(database);
		database.close();
	}
	
	public void increment(final String variableId, final int amount)
	{
		SQLiteDatabase database = getWritableDatabase();
		timeOfDayTable.incrementNow(database, amount);
		frequencyTable.incrementField(database, variableId, amount);
		database.close();
	}

	@Override
	public void onCreate(SQLiteDatabase database)
	{
		timeOfDayTable.createTable(database);
		frequencyTable.createTable(database);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		timeOfDayTable.upgradeTable(database);
		frequencyTable.upgradeTable(database);
	}
	
	public Distribution getDistribution()
	{
		if (frequencyTable != null)
		{
			SQLiteDatabase database = getReadableDatabase();
			Distribution distribution = getDistribution(database, frequencyTable);
			database.close();
			return distribution;
		}
		else
		{
			return null;
		}
	}
	
	private Distribution getDistribution(SQLiteDatabase database, FrequencyTable table)
	{
		Distribution distribution;
		try
		{
			distribution = table.getFrequencyCount(database);
		}
		catch (SQLiteException e)
		{
			if (e.getMessage().toString().contains("no such table"))
			{
				distribution = null;
			}
			else throw e;
		}
		return distribution;
	}
	
	public Distribution getTimeOfDayFrequency()
	{
		SQLiteDatabase database = getReadableDatabase();
		Distribution distribution = getDistribution(database, timeOfDayTable);
		database.close();
		return distribution;
	}
}
