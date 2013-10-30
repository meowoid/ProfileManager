package com.lathia.profilemanager.ui.distribution;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lathia.profilemanager.data.Distribution;
import com.lathia.profilemanager.ui.AbstractProfileActivity;

public abstract class DistributionActivity extends AbstractProfileActivity
{
	public static final String DISTRIBUTION_DATA = "distribution_data";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		Intent intent = getIntent();
		String title = getDistributionTitle();
		if (title != null)
		{
			getScreenTitle().setText(title);
		}
		loadData(intent);
	}
	
	protected abstract String getDistributionTitle();
	
	protected abstract String getDistributionVariableName();
	
	protected abstract String getIntentKeyForDistributionData();
	
	protected Distribution getDistribution()
	{
		String intentKey = getIntentKeyForDistributionData();
		if (intentKey != null)
		{
			Intent intent = getIntent();
			if (intent.hasCategory(DISTRIBUTION_DATA))
			{
				return intent.getParcelableExtra(DISTRIBUTION_DATA);
			}
		}
		return null;
	}

	protected void loadData(final Intent intent)
	{
		new AsyncTask<Void, Void, Distribution>()
		{
			@Override
			protected void onPreExecute()
			{
				super.onPreExecute();
				View noData = getNoDataView();
				if (noData != null)
				{
					noData.setVisibility(View.GONE);
				}
				showLoadingInto(getLoadingProgressBar(), getListView(), true);
			}

			@Override
			protected Distribution doInBackground(Void... params)
			{
				Distribution distribution = getDistribution();
//				if (distribution == null)
//				{
//					ProfileDataStore profileManager = ProfileDataStore.getInstance(DistributionActivity.this);
//					String variableName = getDistributionVariableName();
//					if (variableName != null)
//					{
//						distribution = profileManager.getDistribution(variableName);
//					}
//				}
				return distribution;
			}

			@Override
			protected void onPostExecute(Distribution distribution)
			{
				super.onPostExecute(distribution);
				if (distribution != null)
				{
					getListView().setAdapter(getAdapter(distribution));
				}
				View noData = getNoDataView();
				if (noData != null)
				{
					noData.setVisibility(distribution == null ? View.VISIBLE : View.GONE);
				}
				showLoadingInto(getLoadingProgressBar(), getListView(), false);
			}

		}.execute();
	}

	protected abstract DistributionListAdapter getAdapter(Distribution distribution);

	protected abstract TextView getScreenTitle();

	protected abstract View getNoDataView();
}
