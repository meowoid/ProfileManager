package com.ubhave.profilemanager.ui.list;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.widget.ListView;

import com.ubhave.profilemanager.ui.LoadingThread;

public class LoadJSONThread extends LoadingThread
{

	public LoadJSONThread(final AbstractProfileListActivity ui)
	{
		super(ui);
	}

	protected boolean loadData()
	{
		try
		{
			JSONObject config = new JSONObject(loadFileContents());

			String profileKey = ((AbstractProfileListActivity) ui).getProfileListKey();
			JSONArray profile = config.getJSONArray(profileKey);

			ArrayList<ProfileEntry> data = new ArrayList<ProfileEntry>();
			for (int i = 0; i < profile.length(); i++)
			{
				data.add(new ProfileEntry((JSONObject) profile.get(i)));
			}

			updateListView(data);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public String loadFileContents() throws Exception
	{
		String fileName = ((AbstractProfileListActivity) ui).getJSONConfigFileName();
		try
		{
			return getContent(new BufferedReader(new InputStreamReader(ui.openFileInput(fileName))));
		}
		catch (Exception e)
		{
			return getContent(new BufferedReader(new InputStreamReader(ui.getAssets().open(fileName))));
		}
	}

	private String getContent(final BufferedReader in) throws Exception
	{
		StringBuffer fileContents = new StringBuffer();
		if (in != null)
		{
			String line;
			while ((line = in.readLine()) != null)
			{
				fileContents.append(line);
			}
			in.close();
		}
		return fileContents.toString();
	}

	private void updateListView(final ArrayList<ProfileEntry> data)
	{
		if (data != null)
		{
			ui.runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					AbstractProfileListActivity profileHome = (AbstractProfileListActivity) ui;
					ListView listView = profileHome.getListView();
					if (listView != null)
					{
						AbstractProfileListAdapter adapter = (AbstractProfileListAdapter) listView.getAdapter();
						if (adapter != null)
						{
							adapter.clear();
							adapter.notifyDataSetChanged();
						}
						listView.setAdapter(profileHome.getAdapter(data));
					}
				}
			});
		}
	}
}
