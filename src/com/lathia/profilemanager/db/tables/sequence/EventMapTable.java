package com.lathia.profilemanager.db.tables.sequence;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.lathia.profilemanager.db.tables.AbstractTable;

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
	
	public void add(final SQLiteDatabase database, final HashMap<String, String> values)
	{
		add(database, System.currentTimeMillis(), values);
	}
	
	public void add(final SQLiteDatabase database, final long entryTime, final HashMap<String, String> values)
	{
		JSONObject data = toJSON(values);
		add(database, entryTime, data);
	}
	
	public void add(final SQLiteDatabase database, final JSONObject data)
	{
		add(database, System.currentTimeMillis(), data);
	}
	
	public void add(final SQLiteDatabase database, final long entryTime, final JSONObject data)
	{
		ContentValues content = new ContentValues();
		content.put(timeStamp, entryTime);
		content.put(variableMap, data.toString());
		database.insert(tableName, null, content);
	}
	
	private JSONObject toJSON(final HashMap<String, String> values)
	{
		JSONObject data = new JSONObject();
		for (String key : values.keySet())
		{
			try
			{
				data.put(key, values.get(key));
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
		return data;
	}
}
