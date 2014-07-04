package com.ubhave.profilemanager;

import java.util.HashMap;

public interface ProfileInterface
{

	/*
	 * Storing distributions
	 * Group Name | Variable Variable Name | Variable Value
	 */
	
	/*
	 * Storing Events
	 */
	
	/*
	 * Storing a mapping
	 */
	
	public void setMapVariableValue(final String group, final String name, final String value);
	public String getMapVariableValue(final String group, final String name);
	public HashMap<String, String> getMap(final String group);
}
