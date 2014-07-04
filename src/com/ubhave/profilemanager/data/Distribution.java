package com.ubhave.profilemanager.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.os.Parcel;
import android.os.Parcelable;

public class Distribution extends HashMap<String, Integer> implements Parcelable
{
	private static final long serialVersionUID = -6343668736434104839L;
	
	public Distribution()
	{
		super();
	}
	
	@Override
	public Integer get(final Object key)
	{
		if (containsKey(key))
		{
			return super.get(key);
		}
		else
		{
			return 0;
		}
	}
	
	public void increment(final String key, final int amount)
	{
		Integer currentValue = get(key);
		put(key, currentValue + amount);
	}
	
	public ArrayList<String> getKeys()
	{
		ArrayList<String> keys = new ArrayList<String>();
		keys.addAll(keySet());
		Collections.sort(keys);
		return keys;
	}
	
	public int frequencySum()
	{
		int sum = 0;
		for (Integer value : values())
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
		super();
		int size = in.readInt();
		for (int i=0; i<size; i++)
		{
			String key = in.readString();
			Integer value = in.readInt();
			put(key, value);
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
		dest.writeInt(keySet().size());
		for (String key : keySet())
		{
			dest.writeString(key);
			dest.writeInt(get(key));
		}
	}
}
