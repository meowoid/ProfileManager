package com.lathia.profilemanager.ui.distribution;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.lathia.profilemanager.ProfileDataStore;
import com.lathia.profilemanager.data.Distribution;
import com.lathia.profilemanager.ui.AbstractProfileActivity;

public abstract class DistributionActivity extends AbstractProfileActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		Intent intent = getIntent();
		String title = getDistributionTitle();
		if (title != null)
		{
			TextView textView = getScreenTitle();
			if (textView != null)
			{
				textView.setText(title);
			}
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
			if (intent.hasCategory(intentKey))
			{
				return intent.getParcelableExtra(intentKey);
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
				if (distribution == null)
				{
					ProfileDataStore profileManager = ProfileDataStore.getInstance(DistributionActivity.this);
					String variableName = getDistributionVariableName();
					if (variableName != null)
					{
						if (profileManager.containsDistributionVariable(variableName))
						{
							distribution = profileManager.getDistribution(variableName);
						}
					}
				}
				return distribution;
			}

			@Override
			protected void onPostExecute(Distribution distribution)
			{
				super.onPostExecute(distribution);
				View noData = getNoDataView();
				if (distribution != null)
				{
					ListView listView = getListView();
					if (listView != null)
					{
						listView.setAdapter(getAdapter(distribution));
					}
					if (noData != null)
					{
						noData.setVisibility(View.GONE);
					}
				}
				else
				{
					if (noData != null)
					{
						noData.setVisibility(View.VISIBLE);
					}
					onNoDataAvailable();
				}
				showLoadingInto(getLoadingProgressBar(), getListView(), false);
			}
		}.execute();
	}
	
	protected abstract void onNoDataAvailable();

	protected abstract DistributionListAdapter getAdapter(Distribution distribution);

	protected abstract TextView getScreenTitle();

	protected abstract View getNoDataView();
}
