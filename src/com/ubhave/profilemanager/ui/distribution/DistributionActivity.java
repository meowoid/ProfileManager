package com.ubhave.profilemanager.ui.distribution;

import android.content.Intent;
import android.util.Log;
import android.widget.ListView;

import com.ubhave.profilemanager.ProfileDataStore;
import com.ubhave.profilemanager.data.Distribution;
import com.ubhave.profilemanager.ui.AbstractProfileActivity;
import com.ubhave.profilemanager.ui.LoadingThread;

public abstract class DistributionActivity extends AbstractProfileActivity
{

	protected abstract DistributionListAdapter getAdapter(final Distribution distribution);

	protected String getDistributionVariableName()
	{
		return null;
	}

	protected String getIntentKeyForDistributionData()
	{
		return null;
	}

	private void updateListView(final Distribution distribution)
	{
		if (distribution != null)
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					ListView listView = getListView();
					if (listView != null)
					{
						DistributionListAdapter adapter = (DistributionListAdapter) listView.getAdapter();
						if (adapter != null)
						{
							adapter.clear();
							adapter.notifyDataSetChanged();
						}
						listView.setAdapter(getAdapter(distribution));
					}
				}
			});
		}
	}

	@Override
	protected void loadData()
	{
		new LoadingThread(this)
		{
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

			private Distribution getDistributionFromIntent()
			{
				String intentKey = getIntentKeyForDistributionData();
				if (intentKey != null)
				{
					Intent intent = getIntent();
					if (intent.hasCategory(intentKey))
					{
						return intent.getParcelableExtra(intentKey);
					}
				}
				return null;
			}

			private Distribution loadDistribution()
			{
				Log.d(LOG_TAG, "Attempt load from intent.");
				Distribution distribution = getDistributionFromIntent();
				if (distribution == null)
				{
					Log.d(LOG_TAG, "Attempt load from data store.");
					ProfileDataStore profileManager = ProfileDataStore.getInstance(DistributionActivity.this);
					String variableName = getDistributionVariableName();
					
					Log.d(LOG_TAG, "Distribution: "+variableName);
					if (variableName != null)
					{
						if (profileManager.containsDistribution(variableName))
						{
							distribution = profileManager.getDistribution(variableName);
						}
					}
				}
				return distribution;
			}
		}.start();
	}
}
