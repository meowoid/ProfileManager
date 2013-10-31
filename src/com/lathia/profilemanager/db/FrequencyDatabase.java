package com.lathia.profilemanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.lathia.profilemanager.data.Distribution;
import com.lathia.profilemanager.db.tables.FrequencyTable;

public class FrequencyDatabase extends AbstractProfileDatabase
{
	private final FrequencyTable frequencyTable;
	
	public FrequencyDatabase(final Context context, final String databaseId)
	{
		super(context, databaseId);
		frequencyTable = new FrequencyTable(databaseId);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database)
	{
		frequencyTable.createTable(database);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		frequencyTable.upgradeTable(database);
	}
	
	@Override
	public void deleteAll()
	{
		SQLiteDatabase database = getWritableDatabase();
		reset(frequencyTable, database);
		database.close();
	}
	
	public void increment(final String variableId, final int amount)
	{
		SQLiteDatabase database = getWritableDatabase();
		frequencyTable.incrementField(database, variableId, amount);
		database.close();
	}
	
	public Distribution getDistribution()
	{
		SQLiteDatabase database = getReadableDatabase();
		Distribution distribution = getDistribution(database, frequencyTable);
		database.close();
		return distribution;
	}
	
	private Distribution getDistribution(SQLiteDatabase database, FrequencyTable table)
	{
		Distribution distribution;
		try
		{
			distribution = table.getDistribution(database);
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
}
