package com.ubhave.profilemanager.ui.events;

import java.util.HashMap;
import java.util.List;

import com.ubhave.profilemanager.ProfileDataStore;
import com.ubhave.profilemanager.ui.AbstractProfileActivity;

public abstract class AbstractEventListActivity extends AbstractProfileActivity
{
	protected abstract String getEventListName();
	
	protected abstract int getDaysInPast();
	
	public abstract AbstractEventListAdapter getAdapter(final List<HashMap<String, String>> events);

	@Override
	protected void loadData()
	{
		new LoadEventListThread(this)
		{
			@Override
			protected List<HashMap<String, String>> loadEvents()
			{
				List<HashMap<String, String>> events = null;
				ProfileDataStore profileManager = ProfileDataStore.getInstance(ui);
				String listName = getEventListName();
				if (listName != null)
				{
					events = profileManager.getEvents(listName, getDaysInPast());
				}
				return events;
			}
		}.start();
	}
}
