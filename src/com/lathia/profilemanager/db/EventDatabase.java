package com.lathia.profilemanager.db;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lathia.profilemanager.db.tables.EventTable;

public class EventDatabase extends AbstractProfileDatabase
{
	private final EventTable eventTable;
	
	public EventDatabase(final Context context, final String databaseId)
	{
		super(context, databaseId);
		eventTable = new EventTable(databaseId);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database)
	{
		eventTable.createTable(database);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		eventTable.upgradeTable(database);
	}
	
	@Override
	public void deleteAll()
	{
		SQLiteDatabase database = getWritableDatabase();
		reset(eventTable, database);
		database.close();
	}
	
	public void add(final long entryTime, final HashMap<String, String> values)
	{
		SQLiteDatabase database = getWritableDatabase();
		eventTable.add(database, entryTime, values);
		database.close();
	}
	
	public void add(final long entryTime, final JSONObject values)
	{
		SQLiteDatabase database = getWritableDatabase();
		eventTable.add(database, entryTime, values);
		database.close();
	}
	
	public List<HashMap<String, String>> getEvents(int daysInPast)
	{
		SQLiteDatabase database = getReadableDatabase();
		List<HashMap<String, String>> events = eventTable.getEvents(database, daysInPast);
		database.close();
		return events;
	}
	
	public int countEvents()
	{
		SQLiteDatabase database = getReadableDatabase();
		int numEvents = eventTable.countEvents(database);
		database.close();
		return numEvents;	
	}
}
