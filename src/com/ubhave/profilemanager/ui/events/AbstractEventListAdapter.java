package com.ubhave.profilemanager.ui.events;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public abstract class AbstractEventListAdapter extends ArrayAdapter<HashMap<String, String>>
{
	private final int layoutId;

	public AbstractEventListAdapter(Context context, final List<HashMap<String, String>> data, int layoutId)
	{
		super(context, layoutId, data);
		this.layoutId = layoutId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View row = convertView;
		if (row == null)
		{
			row = View.inflate(getContext(), layoutId, null);
		}
		
		HashMap<String, String> entry = getItem(position);
		for (String key : entry.keySet())
		{
			setValue(row, key, entry.get(key));
		}
		return row;
	}
	
	protected abstract void setValue(final View row, final String key, final String value);
}
