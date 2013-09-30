package com.lathia.profilemanager.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.os.Parcel;
import android.os.Parcelable;

public class Distribution implements Parcelable
{
	private final HashMap<String, Integer> values;
	
	public Distribution()
	{
		values = new HashMap<String, Integer>();
	}
	
	public void put(String key, Integer value)
	{
		values.put(key, value);
	}
	
	public int get(final String key)
	{
		if (values.containsKey(key))
		{
			return values.get(key);
		}
		else
		{
			return 0;
		}
	}
	
	public ArrayList<String> getKeys()
	{
		ArrayList<String> keys = new ArrayList<String>();
		keys.addAll(values.keySet());
		Collections.sort(keys);
		return keys;
	}
	
	public int frequencySum()
	{
		int sum = 0;
		for (Integer value : values.values())
		{
			sum += value;
		}
		return sum;
	}
	
	public boolean hasData()
	{
		return frequencySum() > 0;
	}
	
	/*
	 * Parcel Related
	 */
	
	public Distribution(Parcel in)
	{
		values = new HashMap<String, Integer>();
		int size = in.readInt();
		for (int i=0; i<size; i++)
		{
			String key = in.readString();
			Integer value = in.readInt();
			values.put(key, value);
		}
	}

	public static final Parcelable.Creator<Distribution> CREATOR = new Parcelable.Creator<Distribution>()
	{
		public Distribution createFromParcel(Parcel in)
		{
			return new Distribution(in);
		}

		public Distribution[] newArray(int size)
		{
			return new Distribution[size];
		}
	};

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeInt(values.keySet().size());
		for (String key : values.keySet())
		{
			dest.writeString(key);
			dest.writeInt(values.get(key));
		}
	}
}
