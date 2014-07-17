package com.ubhave.profilemanager.ui.list;

import java.util.ArrayList;

import com.ubhave.profilemanager.ui.distribution.AbstractDistributionActivity;

public abstract class AbstractProfileListActivity extends AbstractDistributionActivity
{
	private final static String CONFIG_FILE_NAME = "profile-list.json";
	private final static String PROFILE_LIST_KEY = "profile";

	public String getJSONConfigFileName()
	{
		// Override this to set your own config file name
		return CONFIG_FILE_NAME;
	}
	
	public String getProfileListKey()
	{
		return PROFILE_LIST_KEY;
	}

	@Override
	protected void loadData()
	{
		new LoadJSONThread(this).start();
	}
	
	protected abstract AbstractProfileListAdapter getAdapter(final ArrayList<ProfileEntry> data);
}
