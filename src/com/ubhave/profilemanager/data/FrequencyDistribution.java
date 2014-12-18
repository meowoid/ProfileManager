package com.ubhave.profilemanager.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FrequencyDistribution extends HashMap<String, Integer>
{
	private static final long serialVersionUID = -6343668736434104839L;

	public FrequencyDistribution()
	{
		super();
	}
	
	public FrequencyDistribution(final HashMap<String, Integer> values)
	{
		super();
		putAll(values);
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
	
	public ArrayList<String> getKeys()
	{
		ArrayList<String> keys = new ArrayList<String>();
		keys.addAll(keySet());
		Collections.sort(keys);
		return keys;
	}
	
	public boolean hasData()
	{
		return frequencySum() > 0;
	}

	public void increment(final String key, final int amount)
	{
		Integer currentValue = get(key);
		put(key, currentValue + amount);
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
}
