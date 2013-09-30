package com.lathia.profilemanager.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lathia.surveymanager.data.answers.AbstractAnswer;
import com.lathia.surveymanager.data.answers.RatingAnswer;
import com.lathia.surveymanager.data.questions.AbstractQuestion;
import com.lathia.surveymanager.data.questions.RatingQuestion;

public class RatingQuestionTable extends AbstractQuestionTable
{
	private final static String ratingValue = "ratingValue";
	private final static String ratingDescription = "ratingDescription";
	private final static String ratingFrequency = "ratingFrequency";
	
	public RatingQuestionTable(final String questionId, final AbstractQuestion question)
	{
		super(questionId, question);
	}

	@Override
	public void createTable(final SQLiteDatabase database)
	{
		database.execSQL("CREATE TABLE IF NOT EXISTS " + tableName
				+ " ("
				+ ratingValue + " TEXT NOT NULL, "
				+ ratingDescription + " TEXT NOT NULL, "
				+ ratingFrequency + " INTEGER DEFAULT 0"
				+ ");");
		
		RatingQuestion ratingQuestion = (RatingQuestion) currentQuestion;
		for (int i=ratingQuestion.getMinValue(); i<=ratingQuestion.getMaxValue(); i++)
		{
			ContentValues values = new ContentValues();
			values.put(ratingValue, ""+i);
			values.put(ratingDescription, ratingQuestion.getDescription(i-1));
			values.put(ratingFrequency, 0);
			database.insert(tableName, null, values);
		}
	}

	@Override
	public void addEntry(final SQLiteDatabase database, final AbstractAnswer answer)
	{
		RatingAnswer rating = (RatingAnswer) answer;
		incrementField(database, ratingFrequency, ratingDescription, rating.getAnswerDescription());
	}
	
	@Override
	protected String getCategoryDescription(Cursor cursor)
	{
		int valueIndex = cursor.getColumnIndex(ratingValue);
		int descriptionIndex = cursor.getColumnIndex(ratingDescription);
		return cursor.getString(valueIndex)+" "+cursor.getString(descriptionIndex);
	}

	@Override
	protected int getCategoryFrequency(Cursor cursor)
	{
		int frequencyIndex = cursor.getColumnIndex(ratingFrequency);
		return cursor.getInt(frequencyIndex);
	}
}
