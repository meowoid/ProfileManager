package com.lathia.profilemanager.db.tables;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;

import com.lathia.surveymanager.data.answers.AbstractAnswer;
import com.lathia.surveymanager.data.answers.StringListAnswer;
import com.lathia.surveymanager.data.questions.AbstractCategoricalQuestion;
import com.lathia.surveymanager.data.questions.AbstractQuestion;

public class CategoricalQuestionTable extends AbstractQuestionTable
{
	
	public CategoricalQuestionTable(final String questionId, final AbstractQuestion question)
	{
		super(questionId, question);	
	}
	
	@Override
	public void createTable(SQLiteDatabase database)
	{
		super.createTable(database);
		AbstractCategoricalQuestion categoricalQuestion = (AbstractCategoricalQuestion) currentQuestion;
		ArrayList<String> options = categoricalQuestion.getChoices();
		for (String option : options)
		{
			incrementField(database, option, 0);
		}
	}

	@Override
	public void addEntry(final SQLiteDatabase database, final AbstractAnswer answer)
	{
		ArrayList<String> selected_categories = ((StringListAnswer) answer).getAnswer();
		for (String selected_category : selected_categories)
		{
			incrementField(database, selected_category, 1);
		}
	}
}
