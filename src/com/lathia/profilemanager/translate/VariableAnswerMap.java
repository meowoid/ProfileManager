package com.lathia.profilemanager.translate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lathia.profilemanager.ProfileDataStore;

public class VariableAnswerMap
{
	private final HashMap<String, List<String>> responseMap;
	private final HashMap<String, List<String>> categoryMap;
	
	public VariableAnswerMap()
	{
		responseMap = new HashMap<String, List<String>>();
		categoryMap = new HashMap<String, List<String>>();
	}
	
	private List<String> get(String variableName, HashMap<String, List<String>> map)
	{
		List<String> variableValues = map.get(variableName);
		if (variableValues == null)
		{
			variableValues = new ArrayList<String>();
			map.put(variableName, variableValues);
		}
		return variableValues;
	}
	
	public void addResponse(String variableName, String variableValue)
	{
		List<String> variableValues = get(variableName,responseMap);
		variableValues.add(variableValue);
	}
	
	public void addResponse(String variableName, List<String> variableValues)
	{
		List<String> currentValues = get(variableName,responseMap);
		currentValues.addAll(variableValues);
	}
	
	public void addCategory(String variableName, String variableValue)
	{
		List<String> variableValues = get(variableName,categoryMap);
		variableValues.add(variableValue);
	}
	
	public void addCategory(String variableName, List<String> variableValues)
	{
		List<String> currentValues = get(variableName,categoryMap);
		currentValues.addAll(variableValues);
	}
	
	public void insertInto(ProfileDataStore dataStore)
	{
		insert(responseMap, dataStore, 1);
		insert(categoryMap, dataStore, 0);
	}
	
	private void insert(HashMap<String, List<String>> map, ProfileDataStore dataStore, int value)
	{
		for (String variableName : map.keySet())
		{
			for (String variableValue : map.get(variableName))
			{
				dataStore.addToDistribution(variableName, variableValue, value);
			}
		}
	}
}
