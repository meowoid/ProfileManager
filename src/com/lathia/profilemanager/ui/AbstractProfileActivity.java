package com.lathia.profilemanager.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

public abstract class AbstractProfileActivity extends Activity
{
	protected abstract int getLayoutId();

	protected abstract ListView getListView();

	protected abstract ProgressBar getLoadingProgressBar();

	protected void showLoadingInto(View bar, View view, boolean loading)
	{
		if (bar != null)
		{
			bar.setVisibility(loading ? View.VISIBLE : View.GONE);
		}
		if (view != null)
		{
			view.setVisibility(loading ? View.INVISIBLE : View.VISIBLE);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
		showLoadingInto(getLoadingProgressBar(), getListView(), true);
	}
}
