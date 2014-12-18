package com.ubhave.profilemanager.ui;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

public abstract class LoadingThread extends Thread
{
	protected final static String LOG_TAG = "Profile-Load";
	protected final AbstractProfileActivity ui;

	public LoadingThread(final AbstractProfileActivity ui)
	{
		this.ui = ui;
	}

	@Override
	public void run()
	{
		ui.set(AbstractProfileActivity.LOADING);	
		final boolean loadedData = loadData();
		ui.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				if (loadedData)
				{
					ListView listView = ui.getListView();
					if (listView != null)
					{
						boolean hasHeader = addHeader(listView);
						updateAdapter(listView);
						setClickActions(listView, hasHeader);
						BaseAdapter adapter = (BaseAdapter) listView.getAdapter();
						if (adapter != null)
						{
							adapter.notifyDataSetChanged();
						}
					}
				}
				else
				{
					ui.onNoDataAvailable();
				}
			}
		});
		ui.set(loadedData ? AbstractProfileActivity.LOADED_SUCCESS : AbstractProfileActivity.LOADED_FAIL);
	}
	
	private boolean addHeader(final ListView listView)
	{
		View header = ui.getListViewHeader();
		if (header != null)
		{
			listView.addHeaderView(header, null, false);
		}
		return (header != null); 
	}

	protected abstract boolean loadData();
	
	protected abstract void setClickActions(final ListView listView, final boolean hasHeader);
	
	protected abstract void updateAdapter(final ListView listView);
}
