package com.lathia.profilemanager.unused.ui.home;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public abstract class ProfileListAdapter extends ArrayAdapter<String>
{
	public ProfileListAdapter(Context context, ArrayList<String> entries, int layoutId)
	{
		super(context, layoutId, entries);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View row = super.getView(position, convertView, parent);
		TextView entryText = getEntryTextView(row);
		entryText.setText(getItem(position));
		return row;
	}
	
	protected abstract TextView getEntryTextView(View row);
}
