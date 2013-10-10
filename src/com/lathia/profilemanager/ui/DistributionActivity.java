package com.lathia.profilemanager.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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
		String title = intent.getStringExtra(DISTRIBUTION_TITLE);
		if (title != null)
		{
			getScreenTitle().setText(title);
		}
		loadData(intent);
	}

	protected void loadData(final Intent intent)
	{
		new AsyncTask<Void, Void, Distribution>()
		{
			@Override
			protected void onPreExecute()
			{
				super.onPreExecute();
				System.err.println("Loading data...");
				View noData = getNoDataView();
				noData.setVisibility(View.GONE);
				showLoadingInto(getLoadingProgressBar(), getListView(), true);
			}

			@Override
			protected Distribution doInBackground(Void... params)
			{
				Distribution distribution = null;
				if (intent.hasCategory(DISTRIBUTION_DATA))
				{
					distribution = intent.getParcelableExtra(DISTRIBUTION_DATA);
				}
				else
				{
					ProfileManager profileManager = ProfileManager.getInstance(DistributionActivity.this);
					String variableName = intent.getStringExtra(DISTRIBUTION_VARIABLE);
					System.err.println("Distribution variable is: "+variableName);
					if (variableName != null)
					{
						distribution = profileManager.getDistribution(variableName);
					}
				}
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
