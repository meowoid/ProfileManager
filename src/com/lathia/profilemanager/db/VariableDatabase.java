package com.lathia.profilemanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lathia.profilemanager.db.tables.StringListTable;

public class VariableDatabase extends AbstractProfileDatabase
{
	private final static String databaseId = "com.lathia.profilemanager.db.VARIABLE_DB";
	private final StringListTable variableListTable;
	
	public VariableDatabase(final Context context, final String name)
	{
		super(context, databaseId+"."+name);
		variableListTable = new StringListTable(databaseId);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database)
	{
		variableListTable.createTable(database);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		variableListTable.upgradeTable(database);
	}
	
	@Override
	public void deleteAll()
	{
		SQLiteDatabase database = getWritableDatabase();
		reset(variableListTable, database);
		database.close();
	}
	
	public void addVariable(final String variableId)
	{
		SQLiteDatabase database = getWritableDatabase();
		variableListTable.addVariable(database, variableId);
		database.close();
	}
	
	public void removeVariable(final String variableId)
	{
		SQLiteDatabase database = getWritableDatabase();
		variableListTable.removeVariable(database, variableId);
		database.close();
	}
	
	public String[] getVariables()
	{
		SQLiteDatabase database = getReadableDatabase();
		String[] variables = variableListTable.getVariables(database);
		database.close();
		return variables;
	}
}
