package com.ubhave.profilemanager.ui.events;

import com.ubhave.profilemanager.ui.AbstractProfileActivity;

public abstract class AbstractEventListActivity extends AbstractProfileActivity
{
	protected abstract String getDistributionVariableName();

	@Override
	protected void loadData()
	{
//		new LoadEventListThread(this)
//		{
//			protected FrequencyDistribution loadDistribution()
//			{
//				FrequencyDistribution distribution = null;
//				ProfileDataStore profileManager = ProfileDataStore.getInstance(ui);
//				String variableName = getDistributionVariableName();
//				if (variableName != null)
//				{
//					if (profileManager.containsDistribution(variableName))
//					{
//						distribution = profileManager.getDistribution(variableName);
//					}
//				}
//				return distribution;
//			}
//		}.start();
	}
}
