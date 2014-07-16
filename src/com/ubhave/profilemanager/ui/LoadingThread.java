package com.ubhave.profilemanager.ui;

import android.util.Log;

public abstract class LoadingThread extends Thread
{
	protected final static String LOG_TAG = "Profile-Load";
	private final AbstractProfileActivity ui;
	
	public LoadingThread(final AbstractProfileActivity ui)
	{
		this.ui = ui;
	}
	
	@Override
	public void run()
	{
		Log.d(LOG_TAG, "Set UI to loading.");
		ui.set(AbstractProfileActivity.LOADING);
		boolean loadedData = loadData();
		
		Log.d(LOG_TAG, "Load successful = "+loadedData+", reset UI.");
		ui.set(loadedData ? AbstractProfileActivity.LOADED_SUCCESS : AbstractProfileActivity.LOADED_FAIL);
		Log.d(LOG_TAG, "Done.");
	}
	
	protected abstract boolean loadData();
}
