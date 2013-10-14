package com.lathia.profilemanager.db.tables.sequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;
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
	
	public List<JSONObject> getJSONEvents(final SQLiteDatabase database, final int daysInPast)
	{
		ArrayList<JSONObject> events = new ArrayList<JSONObject>();
		try
		{
			long one_day = 1000L * 60 * 60 * 24;
			long timeLimit = System.currentTimeMillis() - (daysInPast * one_day);
			Cursor cursor = database.query(tableName, new String[]{variableMap}, timeStamp+" > ", new String[]{""+timeLimit}, null, null, null);
			if (cursor != null)
			{
				int eventIndex = cursor.getColumnIndex(variableMap);
				cursor.moveToFirst();
				while (!cursor.isAfterLast())
				{
					try
					{
						String event = cursor.getString(eventIndex);
						events.add(new JSONObject(event));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					cursor.moveToNext();
				}
				cursor.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return events;
	}
	
	public List<HashMap<String, String>> getEvents(final SQLiteDatabase database, final int daysInPast)
	{
		List<JSONObject> jsonEvents = getJSONEvents(database, daysInPast);
		ArrayList<HashMap<String, String>> events = new ArrayList<HashMap<String, String>>();
		for (JSONObject jsonEvent : jsonEvents)
		{
			HashMap<String, String> map = toMap(jsonEvent);
			events.add(map);
		}
		return events;
	}
	
	private HashMap<String, String> toMap(final JSONObject json)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
		Iterator<String> keys = json.keys();
		while (keys.hasNext())
		{
			try
			{
				String key = keys.next();
				map.put(key, json.getString(key));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return map;
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
