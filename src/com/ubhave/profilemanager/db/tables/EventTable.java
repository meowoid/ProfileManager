package com.ubhave.profilemanager.db.tables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EventTable extends AbstractTable
{
	public final static int NOT_FOUND = -1;
	protected final static String key = "primaryKey";
	protected final static String timeStamp = "timeStamp";
	protected final static String variableMap = "variableMap";
	
	public EventTable(final String tableName)
	{
		super(tableName);
	}
	
	@Override
	public void createTable(final SQLiteDatabase database)
	{
		database.execSQL("CREATE TABLE IF NOT EXISTS " + tableName
				+ " ("
				+ key + " INTEGER PRIMARY KEY, "
				+ timeStamp + " INTEGER, "
				+ variableMap + " TEXT NOT NULL"
				+ ");");
	}
	
	public void add(final SQLiteDatabase database, final long entryTime, final HashMap<String, String> values)
	{
		JSONObject data = toJSON(values);
		add(database, entryTime, data);
	}
	
	public void add(final SQLiteDatabase database, final long entryTime, final JSONObject data)
	{
		ContentValues content = new ContentValues();
		content.put(timeStamp, entryTime);
		content.put(variableMap, data.toString());
		database.insert(tableName, null, content);
	}
	
	public int getKey(final SQLiteDatabase database, final HashMap<String, String> values)
	{
		int eventKey = NOT_FOUND;
		Cursor cursor = database.query(tableName, new String[]{key, variableMap}, null, null, null, null, null);
		if (cursor != null)
		{
			int keyColumn = cursor.getColumnIndex(key);
			int eventColumn = cursor.getColumnIndex(variableMap);
			
			cursor.moveToFirst();
			while (!cursor.isAfterLast())
			{
				try
				{
					String map = cursor.getString(eventColumn);
					JSONObject event = new JSONObject(map);
					@SuppressWarnings("unchecked")
					Iterator<String> keys = event.keys();
					boolean equal = true;
					while (keys.hasNext())
					{
						String key = keys.next();
						String value = event.getString(key);
						String comparison = values.get(key);
						if (!value.equals(comparison))
						{
							equal = false;
							break;
						}
					}
					if (equal)
					{
						eventKey = cursor.getInt(keyColumn);
						break;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				cursor.moveToNext();
			}
			cursor.close();
		}
		return eventKey;
	}
	
	public void remove(final SQLiteDatabase database, final HashMap<String, String> values)
	{
		int eventKey = getKey(database, values);
		int rows = database.delete(tableName, key + " == ?", new String[]{""+eventKey});
		Log.d(tableName, "Deleted "+rows+" rows.");
	}
	
	public void update(final SQLiteDatabase database, final int key, final HashMap<String, String> values)
	{
		JSONObject data = toJSON(values);
		update(database, key, data);
	}
	
	public void update(final SQLiteDatabase database, final int eventKey, final JSONObject data)
	{
		int rows = 0;
		if (eventKey != NOT_FOUND)
		{
			ContentValues content = new ContentValues();
			content.put(variableMap, data.toString());
			rows = database.update(tableName, content, key + " == ?", new String[]{""+eventKey});
		}
		Log.d(tableName, "Updated "+rows+" rows (Key found: "+(eventKey != NOT_FOUND)+").");
	}
	
	public int countEvents(final SQLiteDatabase database)
	{
		Cursor cursor = database.query(tableName, null, null, null, null, null, null);
		if (cursor != null)
		{
			return cursor.getCount();
		}
		else
		{
			return 0;
		}
	}
	
	public List<JSONObject> getJSONEvents(final SQLiteDatabase database, final int daysInPast)
	{
		ArrayList<JSONObject> events = new ArrayList<JSONObject>();
		try
		{
			long one_day = 1000L * 60 * 60 * 24;
			long timeLimit = System.currentTimeMillis() - (daysInPast * one_day);
			String orderBy = timeStamp + " DESC";
			Cursor cursor = database.query(tableName, new String[]{variableMap}, timeStamp+" > ?", new String[]{""+timeLimit}, null, null, orderBy);
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
