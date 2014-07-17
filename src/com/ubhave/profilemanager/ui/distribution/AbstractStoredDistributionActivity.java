package com.ubhave.profilemanager.ui.distribution;

import com.ubhave.profilemanager.ProfileDataStore;
import com.ubhave.profilemanager.data.Distribution;

public abstract class AbstractStoredDistributionActivity extends AbstractDistributionActivity
{
	protected abstract String getDistributionVariableName();

	@Override
	protected void loadData()
	{
		new LoadDistributionThread(this)
		{
			protected Distribution loadDistribution()
			{
				Distribution distribution = null;
				ProfileDataStore profileManager = ProfileDataStore.getInstance(ui);
				String variableName = getDistributionVariableName();
				
				if (variableName != null)
				{
					if (profileManager.containsDistribution(variableName))
					{
						distribution = profileManager.getDistribution(variableName);
					}
				}
				return distribution;
			}
		}.start();
	}
}
