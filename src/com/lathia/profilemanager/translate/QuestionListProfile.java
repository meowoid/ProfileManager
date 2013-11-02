package com.lathia.profilemanager.translate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lathia.profilemanager.ProfileDataStore;

public class QuestionListProfile
{
	private final HashMap<String, List<String>> variableMap;
	
	public QuestionListProfile()
	{
		variableMap = new HashMap<String, List<String>>();
	}
	
	private List<String> get(String variableName)
	{
		List<String> variableValues = variableMap.get(variableName);
		if (variableValues == null)
		{
			variableValues = new ArrayList<String>();
			variableMap.put(variableName, variableValues);
		}
		return variableValues;
	}
	
	public void add(String variableName, String variableValue)
	{
		List<String> variableValues = get(variableName);
		variableValues.add(variableValue);
	}
	
	public void add(String variableName, List<String> variableValues)
	{
		List<String> currentValues = get(variableName);
		currentValues.addAll(variableValues);
	}
	
	public void insertInto(ProfileDataStore dataStore)
	{
		if (variableMap != null)
		{
			for (String variableName : variableMap.keySet())
			{
				for (String variableValue : variableMap.get(variableName))
				{
					dataStore.addToDistribution(variableName, variableValue, 1);
				}
			}
		}
	}
}
