package com.lathia.profilemanager.db.tables;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lathia.surveymanager.data.answers.AbstractAnswer;
import com.lathia.surveymanager.data.answers.StringListAnswer;
import com.lathia.surveymanager.data.questions.AbstractQuestion;


public class CategoricalQuestionTable extends AbstractQuestionTable
{
	private final static String categoryDescription = "categoryDescription";
	private final static String categoryFrequency = "categoryFrequency";
	
	public CategoricalQuestionTable(final String questionId, final AbstractQuestion question)
	{
		super(questionId, question);
	}

	@Override
	public void createTable(final SQLiteDatabase database)
	{
		database.execSQL("CREATE TABLE IF NOT EXISTS " + tableName
				+ " ("
				+ categoryDescription + " TEXT NOT NULL, "
				+ categoryFrequency + " INTEGER DEFAULT 0"
				+ ");");
	}
	
	@Override
	protected String getCategoryDescription(Cursor cursor)
	{
		int descriptionIndex = cursor.getColumnIndex(categoryDescription);
		return cursor.getString(descriptionIndex);
	}
	
	@Override
	protected int getCategoryFrequency(Cursor cursor)
	{
		int frequencyIndex = cursor.getColumnIndex(categoryFrequency);
		return cursor.getInt(frequencyIndex);
	}
	
	@Override
	public void addEntry(final SQLiteDatabase database, final AbstractAnswer answer)
	{
		ArrayList<String> selected_categories = ((StringListAnswer) answer).getAnswer();
		for (String selected_category : selected_categories)
		{
			incrementField(database, categoryFrequency, categoryDescription, selected_category);
		}
	}
}
