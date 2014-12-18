package com.ubhave.profilemanager.ui.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.ubhave.profilemanager.ui.LoadingThread;

public class LoadJSONThread extends LoadingThread
{
	protected ArrayList<ProfileEntry> data;
	private final AbstractProfileListActivity profileUI;
	private final String configFileName;
	private final String jsonProfileKey;

	public LoadJSONThread(final AbstractProfileListActivity ui, final String fileName, final String jsonKey)
	{
		super(ui);
		this.profileUI = ui;
		this.configFileName = fileName;
		this.jsonProfileKey = jsonKey;
	}

	@Override
	protected boolean loadData()
	{
		try
		{
			JSONObject config = new JSONObject(loadFileContents());
			JSONArray profile = config.getJSONArray(jsonProfileKey);
			data = new ArrayList<ProfileEntry>();
			for (int i = 0; i < profile.length(); i++)
			{
				data.add(build((JSONObject) profile.get(i)));
			}
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
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

	private String loadFileContents() throws Exception
	{
		try
		{
			return getContent(new BufferedReader(new InputStreamReader(ui.openFileInput(configFileName))));
		}
		catch (Exception e)
		{
			return getContent(new BufferedReader(new InputStreamReader(ui.getAssets().open(configFileName))));
		}
	}

	protected ProfileEntry build(JSONObject json) throws JSONException
	{
		return new ProfileEntry(json);
	}

	@Override
	protected void setClickActions(final ListView listView, final boolean hasHeader)
	{
		OnItemClickListener clickListener = profileUI.getOnItemClickListener(data, hasHeader);
		if (clickListener != null)
		{
			listView.setOnItemClickListener(clickListener);
		}

		OnItemLongClickListener holdListener = profileUI.getOnItemLongClickListener(data, hasHeader);
		if (holdListener != null)
		{
			listView.setOnItemLongClickListener(holdListener);
		}
	}

	@Override
	protected void updateAdapter(final ListView listView)
	{
		if (data != null)
		{
			AbstractProfileListAdapter adapter = (AbstractProfileListAdapter) listView.getAdapter();
			if (adapter != null)
			{
				adapter.clear();
				adapter.notifyDataSetChanged();
			}
			listView.setAdapter(profileUI.getAdapter(data));
		}
	}
}
