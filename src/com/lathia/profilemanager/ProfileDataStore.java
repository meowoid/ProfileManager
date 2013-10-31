package com.lathia.profilemanager;

import java.util.HashMap;
import java.util.Random;

import android.content.Context;

import com.lathia.profilemanager.data.Distribution;
import com.lathia.profilemanager.db.EventDatabase;
import com.lathia.profilemanager.db.FrequencyDatabase;
import com.lathia.profilemanager.db.VariableDatabase;

public class ProfileDataStore
{
	protected static String DISTRIBUTIONS = "distributions";
	protected static String EVENTS = "events";
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
	protected final HashMap<String, FrequencyDatabase> distributionMap;
	protected final HashMap<String, EventDatabase> eventMap;
	protected final VariableDatabase distributionDB, eventDB;

	public ProfileDataStore(final Context context)
	{
		this.context = context;
		this.distributionMap = new HashMap<String, FrequencyDatabase>();
		this.eventMap = new HashMap<String, EventDatabase>();
		this.distributionDB = new VariableDatabase(context, DISTRIBUTIONS);
		this.eventDB = new VariableDatabase(context, EVENTS);
	}

	/*
	 * Variables that are stored in the profile
	 */

	public String[] getDistributionVariables()
	{
		return distributionDB.getVariables();
	}
	
	public String[] getEventVariables()
	{
		return eventDB.getVariables();
	}

	/*
	 * Feedback based on (Group Name, Variable Name)
	 */

	public void addToDistribution(final String groupName, final String variableName, final int value)
	{
		FrequencyDatabase database = distributionMap.get(groupName);
		if (database == null)
		{
			database = new FrequencyDatabase(context, groupName);
			distributionMap.put(groupName, database);
		}
		database.increment(variableName, value);
		distributionDB.addVariable(groupName);
	}

	public Distribution getDistribution(final String groupName)
	{
		FrequencyDatabase database = distributionMap.get(groupName);
		if (database == null)
		{
			Random random = new Random();
			Distribution distribution = new Distribution();
			for (int i=0; i<10; i++)
			{ // TODO remove
				distribution.put("Test Value "+i, random.nextInt(20));
			}
			return distribution;
//			return null;
		}
		else
		{
			return database.getDistribution();
		}
	}

	public void remove(final String groupName)
	{
		FrequencyDatabase database = distributionMap.get(groupName);
		if (database == null)
		{
			database = new FrequencyDatabase(context, groupName);
			distributionMap.put(groupName, database);
		}
		database.deleteAll();
		distributionDB.removeVariable(groupName);
	}
}
