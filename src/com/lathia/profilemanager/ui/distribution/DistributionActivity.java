package com.lathia.profilemanager.ui.distribution;

import android.content.Intent;
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
		String title = getDistributionTitle();
		if (title != null)
		{
			TextView textView = getScreenTitle();
			if (textView != null)
			{
				textView.setText(title);
			}
		}
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		loadData(getIntent());
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
	
	private void showLoading(final boolean isLoading)
	{
		runOnUiThread(new Runnable()
		{

			@Override
			public void run()
			{
				showLoadingInto(getLoadingProgressBar(), getListView(), isLoading);
			}
		});
	}
	
	private void showNoDataView(final int visibility)
	{
		runOnUiThread(new Runnable()
		{

			@Override
			public void run()
			{
				View noData = getNoDataView();
				if (noData != null)
				{
					noData.setVisibility(visibility);
				}
			}
		});
	}
	
	private void updateListView(final Distribution distribution)
	{
		runOnUiThread(new Runnable()
		{

			@Override
			public void run()
			{
				if (distribution != null)
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
					showNoDataView(View.GONE);
				}
				else
				{
					showNoDataView(View.VISIBLE);
					onNoDataAvailable();
				}
			}
		});
	}

	protected void loadData(final Intent intent)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				onPreExecute();
				Distribution distribution = doInBackground();
				onPostExecute(distribution);
			}
			
			protected void onPreExecute()
			{
				showNoDataView(View.GONE);
				showLoading(true);
			}

			protected Distribution doInBackground()
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

			protected void onPostExecute(Distribution distribution)
			{
				updateListView(distribution);
				showLoading(false);
				if (distribution != null)
				{
					showNoDataView(View.GONE);
				}
				else
				{
					showNoDataView(View.VISIBLE);
				}
			}
		}.start();
	}
	
	protected abstract void onNoDataAvailable();

	protected abstract DistributionListAdapter getAdapter(Distribution distribution);

	protected abstract TextView getScreenTitle();

	protected abstract View getNoDataView();
}
