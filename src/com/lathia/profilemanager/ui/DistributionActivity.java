package com.lathia.profilemanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.lathia.profilemanager.ProfileManager;
import com.lathia.profilemanager.data.Distribution;

public abstract class DistributionActivity extends AbstractProfileActivity
{
	public static final String DISTRIBUTION_TITLE = "distribution_title";
	public static final String DISTRIBUTION_DATA = "distribution_data";
	public static final String DISTRIBUTION_VARIABLE = "distribution_variable";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		
		Intent intent = getIntent();
		getScreenTitle().setText(intent.getStringExtra(DISTRIBUTION_TITLE));
		
		Distribution distribution = null;
		if (intent.hasCategory(DISTRIBUTION_DATA))
		{
			distribution = intent.getParcelableExtra(DISTRIBUTION_DATA);
		}
		else
		{
			ProfileManager profileManager = ProfileManager.getInstance(this);
			String variableName = intent.getStringExtra(DISTRIBUTION_VARIABLE);
			distribution = profileManager.getDistribution(variableName);
		}
		getListView().setAdapter(getAdapter(distribution));
		showLoadingInto(getLoadingProgressBar(), getListView(), false);
	}
	
	protected abstract DistributionListAdapter getAdapter(Distribution distribution);
	
	protected abstract TextView getScreenTitle();
}
