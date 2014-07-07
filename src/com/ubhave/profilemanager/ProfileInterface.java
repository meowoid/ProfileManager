package com.ubhave.profilemanager;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.ubhave.profilemanager.data.Distribution;

public interface ProfileInterface
{
	/*
	 * Variables that are stored in the profile
	 */
	public String[] getDistributionVariables();
	public boolean containsDistributionVariable(final String variableName);
	
	public String[] getEventVariables();

	/*
	 * Storing distributions
	 * Group Name | Variable Variable Name | Variable Value
	 */
	
	public Distribution getDistribution(final String variableName);
	public void removeDistributionTable(final String variableName);
	public void addToDistribution(final String variableName, final String variableValue, final int variableFrequency);
	
	/*
	 * Storing Events
	 */
	
	public void removeEventTable(final String groupName);
	
	public void addEvent(final String groupName, final long entryTimeInMillis, final HashMap<String, String> event);
	public void addEvent(final String groupName, final long entryTimeInMillis, final JSONObject event);
	
	public List<HashMap<String, String>> getEvents(final String groupName, final int daysInPast);
	public int countEvents(final String groupName);
	
	/*
	 * Storing a mapping
	 */
	
	public void setMapVariableValue(final String group, final String name, final String value);
	public String getMapVariableValue(final String group, final String name);
	public HashMap<String, String> getMap(final String group);
}
