package com.ubhave.profilemanager.ui.distribution;

import com.ubhave.profilemanager.data.Distribution;
import com.ubhave.profilemanager.ui.AbstractProfileActivity;

public abstract class AbstractDistributionActivity extends AbstractProfileActivity
{
	public abstract AbstractDistributionListAdapter getAdapter(final Distribution distribution);	
}
