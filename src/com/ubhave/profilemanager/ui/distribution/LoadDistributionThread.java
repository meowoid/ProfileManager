package com.ubhave.profilemanager.ui.distribution;

import android.widget.ListView;

import com.ubhave.profilemanager.data.Distribution;
import com.ubhave.profilemanager.ui.LoadingThread;

public abstract class LoadDistributionThread extends LoadingThread
{
	
	public LoadDistributionThread(final AbstractDistributionActivity ui)
	{
		super(ui);
	}
	
	@Override
	protected boolean loadData()
	{
		Distribution distribution = loadDistribution();
		if (distribution != null)
		{
			updateListView(distribution);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	protected abstract Distribution loadDistribution();
	
	private void updateListView(final Distribution distribution)
	{
		if (distribution != null)
		{
			ui.runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					AbstractDistributionActivity distributionUI = (AbstractDistributionActivity) ui;
					ListView listView = distributionUI.getListView();
					if (listView != null)
					{
						DistributionListAdapter adapter = (DistributionListAdapter) listView.getAdapter();
						if (adapter != null)
						{
							adapter.clear();
							adapter.notifyDataSetChanged();
						} 
						listView.setAdapter(distributionUI.getAdapter(distribution));
					}
				}
			});
		}
	}
}
