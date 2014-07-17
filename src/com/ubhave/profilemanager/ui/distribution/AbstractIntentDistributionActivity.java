package com.ubhave.profilemanager.ui.distribution;

import android.content.Intent;

import com.ubhave.profilemanager.data.Distribution;

public abstract class AbstractIntentDistributionActivity extends AbstractDistributionActivity
{
	protected abstract String getIntentKeyForDistributionData();

	@Override
	protected void loadData()
	{
		new LoadDistributionThread(this)
		{
			protected Distribution loadDistribution()
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
		}.start();
	}
}
