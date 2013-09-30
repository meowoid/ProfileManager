package com.lathia.profilemanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.lathia.profilemanager.data.Distribution;

public abstract class DistributionActivity extends AbstractProfileActivity
{
	public static final String DISTRIBUTION_TITLE = "distribution_title";
	public static final String DISTRIBUTION_DATA = "distribution_data";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		
		Intent intent = getIntent();
		getScreenTitle().setText(intent.getStringExtra(DISTRIBUTION_TITLE));
		
		Distribution distribution = intent.getParcelableExtra(DISTRIBUTION_DATA);
		getListView().setAdapter(getAdapter(distribution));
		showLoadingInto(getLoadingProgressBar(), getListView(), false);
	}
	
	protected abstract DistributionListAdapter getAdapter(Distribution distribution);
	
	protected abstract TextView getScreenTitle();
}
