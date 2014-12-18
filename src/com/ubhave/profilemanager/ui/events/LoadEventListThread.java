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
		events = loadDistribution();
		return (events != null);
	}
	
	protected abstract List<HashMap<String, String>> loadDistribution();

	@Override
	protected void updateAdapter(final ListView listView)
	{
		
	}
	
	@Override
	protected void setClickActions(final ListView listView, final boolean hasHeader)
	{
		
	}
}
