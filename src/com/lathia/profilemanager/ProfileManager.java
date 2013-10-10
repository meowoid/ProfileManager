package com.lathia.profilemanager;

import java.util.HashMap;

import android.content.Context;

import com.lathia.profilemanager.data.Distribution;
import com.lathia.profilemanager.db.FrequencyDatabase;
import com.lathia.profilemanager.db.QuestionListFrequencyDatabase;
import com.lathia.surveymanager.data.AnswerList;
import com.lathia.surveymanager.data.QuestionList;
import com.lathia.surveymanager.data.questions.AbstractQuestion;

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
	
	public void add(final String groupName, final String variableName, final int value, final boolean incrementTime)
	{
		FrequencyDatabase database = databaseMap.get(groupName);
		if (database == null)
		{
			database = new FrequencyDatabase(context, groupName);
			databaseMap.put(groupName, database);
		}
		database.increment(variableName, value, incrementTime);
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
	
	public void reset(final String groupName)
	{
		FrequencyDatabase database = databaseMap.get(groupName);
		if (database == null)
		{
			database = new FrequencyDatabase(context, groupName);
			databaseMap.put(groupName, database);
		}
		database.deleteAll();
	}
	
	public void add(final QuestionList questions, final AnswerList answers)
	{
		String tableName = questions.getSurveyIdentifier();
		QuestionListFrequencyDatabase database;
		if (databaseMap.containsKey(tableName))
		{
			database = (QuestionListFrequencyDatabase) databaseMap.get(questions.getSurveyIdentifier());
		}
		else
		{
			database = new QuestionListFrequencyDatabase(context, questions);
		}
		database.addResponse(answers);
	}
	
	public Distribution getDistribution(final QuestionList questions, final AbstractQuestion question)
	{
		String tableName = questions.getSurveyIdentifier();
		if (databaseMap.containsKey(tableName))
		{
			QuestionListFrequencyDatabase database = (QuestionListFrequencyDatabase) databaseMap.get(questions.getSurveyIdentifier());
			return database.getFrequency(question);
		}
		else
		{
			return null;
		}
	}
}
