package com.lathia.profilemanager.unused.ui.home;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.lathia.profilemanager.ui.AbstractProfileActivity;

public abstract class AbstractProfileHomeActivity extends AbstractProfileActivity
{	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		showLoadingInto(getLoadingProgressBar(), getListView(), true);
		loadData();
	}
	
	protected abstract void loadData();

	protected abstract Class<?> getDistributionActivityClass();
	
	protected abstract String getNoDataMessage();
	
	protected abstract ProfileListAdapter getAdapter(ArrayList<String> entryTitles);

	protected void showList(BaseAdapter adapter, OnItemClickListener listener)
	{
		showLoadingInto(getLoadingProgressBar(), getListView(), false);
		ListView list = getListView();
		list.setAdapter(adapter);
		if (listener != null)
		{
			list.setOnItemClickListener(listener);			
		}
		else
		{
			list.setClickable(false);
		}
	}
}
