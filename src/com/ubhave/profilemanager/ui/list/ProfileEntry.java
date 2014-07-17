package com.ubhave.profilemanager.ui.list;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileEntry
{
	private final static String DISPLAY_KEY = "display";
	private final static String PROFILE_VAR = "variable";
	
	private final String displayText;
	private final String profileVariable;
	
	public ProfileEntry(String displayText, String profileVariable)
	{
		this.displayText = displayText;
		this.profileVariable = profileVariable;
	}
	
	public ProfileEntry(final JSONObject json) throws JSONException
	{
		if (json.has(DISPLAY_KEY))
		{
			this.displayText = json.getString(DISPLAY_KEY);
		}
		else
		{
			this.displayText = null;
		}
		
		if (json.has(PROFILE_VAR))
		{
			this.profileVariable = json.getString(PROFILE_VAR);
		}
		else
		{
			this.profileVariable = null;
		}
	}
	
	public String getDisplayText()
	{
		return displayText;
	}
	
	public String getProfileVariable()
	{
		return profileVariable;
	}
}
