package com.lathia.profilemanager.db.tables.sequence;

import com.lathia.profilemanager.db.tables.AbstractTable;

import android.database.sqlite.SQLiteDatabase;

public class EventMapTable extends AbstractTable
{
	protected final static String timeStamp = "timeStamp";
	protected final static String variableMap = "variableMap";
	
	public EventMapTable(final String tableName)
	{
		super(tableName);
	}
	
	@Override
	public void createTable(final SQLiteDatabase database)
	{
		database.execSQL("CREATE TABLE IF NOT EXISTS " + tableName
				+ " ("
				+ timeStamp + " INTEGER, "
				+ variableMap + " TEXT NOT NULL"
				+ ");");
	}
	
//	public void incrementField(final SQLiteDatabase database, final String variable, final int amount)
//	{
//		ContentValues values = new ContentValues();
//		int currentFrequency = getCurrentFrequency(database, variable);
//		if (currentFrequency == NOT_FOUND)
//		{
//			values.put(variableName, variable);
//			values.put(variableFrequency, amount);
//			database.insert(tableName, null, values);
//		}
//		else
//		{
//			values.put(variableFrequency,  currentFrequency + amount);
//			database.update(tableName, values, variableName+" = ?", new String[]{variable});
//		}
//	}
//	
//	private int getCurrentFrequency(final SQLiteDatabase database, final String variable)
//	{
//		try
//		{
//			Cursor cursor = database.query(tableName, new String[]{variableFrequency}, variableName+" = ? ", new String[]{variable}, null, null, null);
//			if (cursor != null)
//			{
//				int frequency;
//				if (cursor.getCount() > 0)
//				{
//					cursor.moveToFirst();
//					frequency = cursor.getInt(cursor.getColumnIndex(variableFrequency));
//				}
//				else
//				{
//					frequency = NOT_FOUND;
//				}
//				cursor.close();
//				return frequency;
//			}
//		}
//		catch (Exception e)
//		{}
//		return NOT_FOUND;
//	}
//	
//	public Distribution getDistribution(final SQLiteDatabase database)
//	{
//		Distribution result = new Distribution();
//		Cursor cursor = database.query(tableName, null, null, null, null, null, null);
//		if (cursor != null)
//		{
//			int variableNameIndex = cursor.getColumnIndex(variableName);
//			int variableValueIndex = cursor.getColumnIndex(variableFrequency);
//			cursor.moveToFirst();
//			while (!cursor.isAfterLast())
//			{
//				String key = cursor.getString(variableNameIndex);
//				Integer frequency = cursor.getInt(variableValueIndex);
//				result.put(key, frequency);
//				cursor.moveToNext();
//			}
//			cursor.close();
//		}
//		return result;
//	}
}
