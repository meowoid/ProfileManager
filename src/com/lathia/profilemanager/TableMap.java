package com.lathia.profilemanager;

import java.util.HashMap;

import android.content.Context;

import com.lathia.profilemanager.db.AbstractProfileDatabase;
import com.lathia.profilemanager.db.VariableDatabase;

public class TableMap <T extends AbstractProfileDatabase>
{
	protected final HashMap<String, T> tableMap;
	protected final VariableDatabase variableNameDB;
	
	public TableMap(final Context context, final String name)
	{
		tableMap = new HashMap<String, T>();
		variableNameDB = new VariableDatabase(context, name);
	}
	
	public String[] getVariables()
	{
		return variableNameDB.getVariables();
	}
	
	public T get(final String tableName)
	{
		return tableMap.get(tableName);
	}
	
	public void put(final String tableName, final T table)
	{
		variableNameDB.addVariable(tableName);
		tableMap.put(tableName, table);
	}
	
	public void remove(final String tableName)
	{
		T table = tableMap.remove(tableName);
		if (table != null)
		{
			table.deleteAll();
		}
		variableNameDB.removeVariable(tableName);
	}
}