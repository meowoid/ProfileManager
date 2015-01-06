package com.ubhave.profilemanager.ui.events;

import java.util.HashMap;
import java.util.List;

import android.widget.ListView;

import com.ubhave.profilemanager.ui.AbstractProfileActivity;
import com.ubhave.profilemanager.ui.LoadingThread;

public abstract class LoadEventListThread extends LoadingThread
{
	protected List<HashMap<String, String>> events;
	
	public LoadEventListThread(final AbstractProfileActivity ui)
	{
		super(ui);
	}
	
	@Override
	protected boolean loadData()
	{
		events = loadEvents();
		return (events != null);
	}
	
	protected abstract List<HashMap<String, String>> loadEvents();

	@Override
	protected void updateAdapter(final ListView listView)
	{
		AbstractEventListActivity eventUI = (AbstractEventListActivity) ui;
		listView.setAdapter(eventUI.getAdapter(events));
	}
	
	@Override
	protected void setClickActions(final ListView listView, final boolean hasHeader)
	{
	
	}
}
