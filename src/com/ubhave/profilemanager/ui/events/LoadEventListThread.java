package com.ubhave.profilemanager.ui.events;

import android.widget.ListView;

import com.ubhave.profilemanager.data.FrequencyDistribution;
import com.ubhave.profilemanager.ui.AbstractProfileActivity;
import com.ubhave.profilemanager.ui.LoadingThread;

public abstract class LoadEventListThread extends LoadingThread
{
	private FrequencyDistribution distribution;
	
	public LoadEventListThread(final AbstractProfileActivity ui)
	{
		super(ui);
	}
	
	@Override
	protected boolean loadData()
	{
		distribution = loadDistribution();
		return (distribution != null);
	}
	
	protected abstract FrequencyDistribution loadDistribution();

	@Override
	protected void updateAdapter(final ListView listView)
	{
		
	}
	
	@Override
	protected void setClickActions(final ListView listView, final boolean hasHeader)
	{
		
	}
}
