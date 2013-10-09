package com.lathia.profilemanager.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lathia.profilemanager.data.Distribution;

public class FrequencyTable
{
	protected final static String variableName = "variableName";
	protected final static String variableFrequency = "variableFrequency";
	protected final static int NOT_FOUND = -1;
	
	protected final String tableName;
	
	public FrequencyTable(final String tableName)
	{
		this.tableName = tableName;
	}
	
	public void createTable(final SQLiteDatabase database)
	{
		database.execSQL("CREATE TABLE IF NOT EXISTS " + tableName
				+ " ("
				+ variableName + " TEXT NOT NULL, "
				+ variableFrequency + " INTEGER DEFAULT 0"
				+ ");");
	}
	
	public void dropTable(final SQLiteDatabase database)
	{
		database.execSQL("DROP TABLE IF EXISTS " + tableName);
	}
	
	public void upgradeTable(final SQLiteDatabase database)
	{
		dropTable(database);
		createTable(database);
	}
	
	public void incrementField(final SQLiteDatabase database, final String variable, final int amount)
	{
		ContentValues values = new ContentValues();
		int currentFrequency = getCurrentFrequency(database, variable);
		if (currentFrequency == NOT_FOUND)
		{
			values.put(variableName, variable);
			values.put(variableFrequency, amount);
			database.insert(tableName, null, values);
		}
		else
		{
			values.put(variableFrequency,  currentFrequency + amount);
			database.update(tableName, values, variableName+" = ?", new String[]{variable});
		}
	}
	
	private int getCurrentFrequency(final SQLiteDatabase database, final String variable)
	{
		Cursor cursor = database.query(tableName, new String[]{variableFrequency}, variableName+" = ? ", new String[]{variable}, null, null, null);
		if (cursor != null)
		{
			int frequency;
			if (cursor.getCount() > 0)
			{
				cursor.moveToFirst();
				frequency = cursor.getInt(cursor.getColumnIndex(variableFrequency));
			}
			else
			{
				frequency = NOT_FOUND;
			}
			cursor.close();
			return frequency;
		}
		else
		{
			return NOT_FOUND;
		}
	}
	
	public Distribution getFrequencyCount(final SQLiteDatabase database)
	{
		Distribution result = new Distribution();
		Cursor cursor = database.query(tableName, null, null, null, null, null, null);
		if (cursor != null)
		{
			int variableNameIndex = cursor.getColumnIndex(variableName);
			int variableValueIndex = cursor.getColumnIndex(variableFrequency);
			cursor.moveToFirst();
			while (!cursor.isAfterLast())
			{
				String key = cursor.getString(variableNameIndex);
				Integer frequency = cursor.getInt(variableValueIndex);
				result.put(key, frequency);
				cursor.moveToNext();
			}
			cursor.close();
		}
		return result;
	}
}
