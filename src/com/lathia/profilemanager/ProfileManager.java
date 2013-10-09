package com.lathia.profilemanager;

import java.util.HashMap;

import android.content.Context;

import com.lathia.profilemanager.data.Distribution;
import com.lathia.profilemanager.db.FrequencyDatabase;
import com.lathia.surveymanager.data.AnswerList;
import com.lathia.surveymanager.data.QuestionList;

public class ProfileManager
{
	private static ProfileManager instance;
	
	public static ProfileManager getInstance(final Context context)
	{
		if (instance == null)
		{
			instance = new ProfileManager(context);
		}
		return instance;
	}
	
	private final Context context;
	private final HashMap<String, FrequencyDatabase> databaseMap;
	
	public ProfileManager(final Context context)
	{
		this.context = context;
		this.databaseMap = new HashMap<String, FrequencyDatabase>();
	}
	
	public void add(final String groupName, final String variableName, final int value)
	{
		FrequencyDatabase database = databaseMap.get(groupName);
		if (database == null)
		{
			database = new FrequencyDatabase(context, groupName);
		}
		database.increment(variableName, value);
	}
	
	public Distribution getDistribution(final String groupName)
	{
		FrequencyDatabase database = databaseMap.get(groupName);
		if (database != null)
		{
			return database.getDistribution();
		}
		else
		{
			return null;
		}
	}
	
	public void add(final QuestionList questions, final AnswerList answers)
	{
		// TODO
	}
}
