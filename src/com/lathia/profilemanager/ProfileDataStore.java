package com.lathia.profilemanager;

import java.util.HashMap;

import android.content.Context;

import com.lathia.profilemanager.data.Distribution;
import com.lathia.profilemanager.db.FrequencyDatabase;
import com.lathia.profilemanager.db.VariableDatabase;

public class ProfileDataStore
{
	private static ProfileDataStore instance;

	public static ProfileDataStore getInstance(Context context)
	{
		if (instance == null)
		{
			instance = new ProfileDataStore(context);
		}
		return instance;
	}

	protected final Context context;
	protected final HashMap<String, FrequencyDatabase> databaseMap;
	protected final VariableDatabase variableDB;

	public ProfileDataStore(final Context context)
	{
		this.context = context;
		this.databaseMap = new HashMap<String, FrequencyDatabase>();
		this.variableDB = new VariableDatabase(context);
	}

	/*
	 * Variables that are stored in the profile
	 */

	public String[] getVariables()
	{
		return variableDB.getVariables();
	}

	/*
	 * Feedback based on (Group Name, Variable Name)
	 */

	public void add(final String groupName, final String variableName, final int value, final boolean incrementTime)
	{
		FrequencyDatabase database = databaseMap.get(groupName);
		if (database == null)
		{
			database = new FrequencyDatabase(context, groupName);
			databaseMap.put(groupName, database);
		}
		database.increment(variableName, value, incrementTime);
		variableDB.addVariable(groupName);
	}

	public void add(final String groupName, final String variableName, final boolean incrementTime)
	{
		add(groupName, variableName, 1, incrementTime);
	}

	public Distribution getDistribution(final String groupName)
	{
		FrequencyDatabase database = databaseMap.get(groupName);
		if (database == null)
		{
			database = new FrequencyDatabase(context, groupName);
			databaseMap.put(groupName, database);
		}
		return database.getDistribution();
	}

	public void remove(final String groupName)
	{
		FrequencyDatabase database = databaseMap.get(groupName);
		if (database == null)
		{
			database = new FrequencyDatabase(context, groupName);
			databaseMap.put(groupName, database);
		}
		database.deleteAll();
		variableDB.removeVariable(groupName);
	}
}
