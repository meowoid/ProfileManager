package com.lathia.profilemanager;

import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.lathia.profilemanager.data.Distribution;
import com.lathia.profilemanager.db.EventDatabase;
import com.lathia.profilemanager.db.FrequencyDatabase;
import com.lathia.profilemanager.translate.QATranslator;
import com.lathia.profilemanager.translate.QuestionListProfile;
import com.lathia.surveymanager.data.answers.AbstractAnswer;
import com.lathia.surveymanager.data.questions.AbstractQuestion;

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
	protected final TableMap<FrequencyDatabase> distributionMap;
	protected final TableMap<EventDatabase> eventMap;

	public ProfileDataStore(final Context context)
	{
		this.context = context;
		this.distributionMap = new TableMap<FrequencyDatabase>(context, DISTRIBUTIONS);
		this.eventMap = new TableMap<EventDatabase>(context, EVENTS);
	}

	/*
	 * Variables that are stored in the profile
	 */

	public String[] getDistributionVariables()
	{
		return distributionMap.getVariables();
	}
	
	public String[] getEventVariables()
	{
		return eventMap.getVariables();
	}
	
	public boolean containsDistributionVariable(final String variableName)
	{
		return distributionMap.containsVariable(variableName);
	}

	/*
	 * Distribution Feedback based on (Group Name, Variable Name)
	 */
	
	private FrequencyDatabase getFrequencyDatabase(final String tableName)
	{
		FrequencyDatabase database = distributionMap.get(tableName);
		if (database == null)
		{
			database = new FrequencyDatabase(context, tableName);
			distributionMap.put(tableName, database);
		}
		return database;
	}

	public void addToDistribution(final String variableName, final String variableValue, final int variableFrequency)
	{
		FrequencyDatabase database = getFrequencyDatabase(variableName);
		database.increment(variableValue, variableFrequency);
	}
	
	public void removeDistributionTable(final String variableName)
	{
		distributionMap.remove(variableName);
	}

	public Distribution getDistribution(final String variableName)
	{
		FrequencyDatabase database = getFrequencyDatabase(variableName);
		return database.getDistribution();
	}
	
	public void addToDistribution(final AbstractQuestion question, final AbstractAnswer answer)
	{
		QuestionListProfile variableMap = QATranslator.getVariableValue(question, answer);
		if (variableMap != null)
		{
			variableMap.insertInto(this);
		}
	}

	/*
	 * Events
	 */
	
	private EventDatabase getEventDatabase(final String tableName)
	{
		EventDatabase database = eventMap.get(tableName);
		if (database == null)
		{
			database = new EventDatabase(context, tableName);
			eventMap.put(tableName, database);
		}
		return database;
	}
	
	public void addEvent(final String groupName, final long entryTimeInMillis, final HashMap<String, String> event)
	{
		EventDatabase database = getEventDatabase(groupName);
		database.add(entryTimeInMillis, event);
	}
	
	public void removeEventTable(final String groupName)
	{
		eventMap.remove(groupName);
	}
	
	public List<HashMap<String, String>> getEvents(final String groupName, final int daysInPast)
	{
		EventDatabase database = getEventDatabase(groupName);
		return database.getEvents(daysInPast);
	}
	
	public int countEvents(final String groupName)
	{
		EventDatabase database = getEventDatabase(groupName);
		return database.countEvents();
	}
}
