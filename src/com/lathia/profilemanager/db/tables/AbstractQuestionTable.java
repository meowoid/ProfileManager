package com.lathia.profilemanager.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lathia.profilemanager.data.Distribution;
import com.lathia.surveymanager.data.answers.AbstractAnswer;
import com.lathia.surveymanager.data.questions.AbstractQuestion;

public abstract class AbstractQuestionTable
{
	private final static int NOT_FOUND = -1;
	protected final String tableName;
	protected final AbstractQuestion currentQuestion;
	
	public AbstractQuestionTable(final String tableName, final AbstractQuestion question)
	{
		this.tableName = tableName;
		this.currentQuestion = question;
	}
	
	public abstract void createTable(final SQLiteDatabase database);
	
	public void dropTable(final SQLiteDatabase database)
	{
		database.execSQL("DROP TABLE IF EXISTS " + tableName);
	}
	
	public void upgradeTable(final SQLiteDatabase database)
	{
		dropTable(database);
		createTable(database);
	}
	
	protected void incrementField(final SQLiteDatabase database, final String counterField, final String rowKey, final String rowValue)
	{
		ContentValues values = new ContentValues();
		int currentFrequency = getCurrentFrequency(database, counterField, rowKey, rowValue);
		if (currentFrequency == NOT_FOUND)
		{
			values.put(rowKey, rowValue);
			values.put(counterField, 1);
			database.insert(tableName, null, values);
		}
		else
		{
			values.put(counterField,  currentFrequency + 1);
			database.update(tableName, values, rowKey+" = ?", new String[]{rowValue});
		}
	}
	
	private int getCurrentFrequency(final SQLiteDatabase database, final String counterField, final String rowKey, final String rowValue)
	{
		Cursor cursor = database.query(tableName, new String[]{counterField}, rowKey+" = ? ", new String[]{rowValue}, null, null, null);
		if (cursor != null)
		{
			int frequency;
			if (cursor.getCount() > 0)
			{
				cursor.moveToFirst();
				frequency = cursor.getInt(cursor.getColumnIndex(counterField));
			}
			else
			{
				frequency = NOT_FOUND;
			}
			cursor.close();
			return frequency;
		}
		else
		{
			return NOT_FOUND;
		}
	}
	
	public Distribution getFrequencyCount(final SQLiteDatabase database)
	{
		Distribution result = new Distribution();
		Cursor cursor = database.query(tableName, null, null, null, null, null, null);
		if (cursor != null)
		{
			cursor.moveToFirst();
			while (!cursor.isAfterLast())
			{
				String key = getCategoryDescription(cursor);
				Integer frequency = getCategoryFrequency(cursor);
				result.put(key, frequency);
				cursor.moveToNext();
			}
			cursor.close();
		}
		return result;
	}
	
	protected abstract String getCategoryDescription(Cursor cursor);
	
	protected abstract int getCategoryFrequency(Cursor cursor);
	
	public abstract void addEntry(final SQLiteDatabase database, final AbstractAnswer answer);
	
	
}
