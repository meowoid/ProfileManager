package com.ubhave.profilemanager.ui.distribution;

import android.widget.ListView;

import com.ubhave.profilemanager.data.FrequencyDistribution;
import com.ubhave.profilemanager.ui.LoadingThread;

public abstract class LoadDistributionThread extends LoadingThread
{
	private FrequencyDistribution distribution;
	
	public LoadDistributionThread(final AbstractDistributionActivity ui)
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
		AbstractDistributionActivity distributionUI = (AbstractDistributionActivity) ui;
		listView.setAdapter(distributionUI.getAdapter(distribution));
	}
	
	@Override
	protected void setClickActions(final ListView listView, final boolean hasHeader)
	{
		
	}
}
