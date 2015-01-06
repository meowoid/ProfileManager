package com.ubhave.profilemanager.ui.config;

import java.util.ArrayList;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.ubhave.profilemanager.ui.AbstractProfileActivity;

public abstract class AbstractProfileListActivity extends AbstractProfileActivity
{
	protected abstract String getJSONConfigFileName();

	protected abstract String getProfileListKey();

	@Override
	protected void loadData()
	{
		new LoadJSONThread(this, getJSONConfigFileName(), getProfileListKey()).start();
	}
	
	public OnItemClickListener getOnItemClickListener(final ArrayList<ProfileEntry> data, final boolean hasHeader)
	{
		return new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				if (hasHeader)
				{
					position --;
				}
				onItemClicked(data.get(position));
			}
		};
	}
	
	public OnItemLongClickListener getOnItemLongClickListener(final ArrayList<ProfileEntry> data, final boolean hasHeader)
	{
		return new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{
				if (hasHeader)
				{
					position --;
				}
				onItemLongClicked(data.get(position));
				return true;
			}
		};
	}
	
	protected abstract AbstractProfileListAdapter getAdapter(final ArrayList<ProfileEntry> data);
	
	public abstract void onItemClicked(final ProfileEntry entry);
	
	public abstract void onItemLongClicked(final ProfileEntry entry);
}
