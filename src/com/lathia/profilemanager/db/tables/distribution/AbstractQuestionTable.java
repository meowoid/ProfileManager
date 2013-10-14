package com.lathia.profilemanager.db.tables.distribution;

import android.database.sqlite.SQLiteDatabase;

import com.lathia.surveymanager.data.answers.AbstractAnswer;
import com.lathia.surveymanager.data.questions.AbstractQuestion;

public abstract class AbstractQuestionTable extends FrequencyTable
{
	protected final AbstractQuestion currentQuestion;
	
	public AbstractQuestionTable(final String tableName, final AbstractQuestion question)
	{
		super(tableName);
		this.currentQuestion = question;
	}
	
	public abstract void addEntry(final SQLiteDatabase database, final AbstractAnswer answer);
	
}
