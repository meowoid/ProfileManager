package com.lathia.profilemanager.db;

import java.util.HashMap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.lathia.profilemanager.data.Distribution;
import com.lathia.profilemanager.db.tables.AbstractQuestionTable;
import com.lathia.profilemanager.db.tables.CategoricalQuestionTable;
import com.lathia.profilemanager.db.tables.RatingQuestionTable;
import com.lathia.profilemanager.db.tables.TimeOfDayTable;
import com.lathia.surveymanager.data.AnswerList;
import com.lathia.surveymanager.data.QuestionList;
import com.lathia.surveymanager.data.answers.AbstractAnswer;
import com.lathia.surveymanager.data.answers.RatingAnswer;
import com.lathia.surveymanager.data.answers.RatingListAnswer;
import com.lathia.surveymanager.data.questions.AbstractQuestion;
import com.lathia.surveymanager.data.questions.RatingList;
import com.lathia.surveymanager.data.questions.RatingQuestion;

public class QuestionListFrequencyDatabase extends SQLiteOpenHelper
{
	private final static int dbVersion = 1;
	
	private final String surveyId;
	private final TimeOfDayTable timeOfDayTable;
	private final HashMap<String, AbstractQuestionTable> frequencyTables;
	
	public QuestionListFrequencyDatabase(final Context context, final QuestionList questions)
	{
		super(context, questions.getSurveyIdentifier(), null, dbVersion);
		surveyId = questions.getSurveyIdentifier();
		timeOfDayTable = new TimeOfDayTable(surveyId);
		frequencyTables = new HashMap<String, AbstractQuestionTable>();
		for (AbstractQuestion question : questions.getQuestions().values())
		{
			if (question.getType().equals(AbstractQuestion.TYPE_RATING_LIST))
			{
				RatingQuestion[] ratings = ((RatingList) question).getQuestions();
				for (RatingQuestion rating : ratings)
				{
					addTable(surveyId, rating);
				}
			}
			else
			{
				addTable(surveyId, question);
			}
		}
	}
	
	public void deleteAll()
	{
		SQLiteDatabase database = getWritableDatabase();
		timeOfDayTable.dropTable(database);
		for (AbstractQuestionTable table : frequencyTables.values())
		{
			table.dropTable(database);
		}
		database.close();
	}
	
	private void addTable(final String surveyId, final AbstractQuestion question)
	{
		AbstractQuestionTable table = getTable(surveyId, question);
		if (table != null)
		{
			frequencyTables.put(question.getId(), table);
		}
	}
	
	private AbstractQuestionTable getTable(final String surveyId, AbstractQuestion question)
	{
		String type = question.getType();
		String tableId = surveyId+"_"+question.getId();
		if (type.equals(AbstractQuestion.TYPE_RATING_ENTRY))
		{
			return new RatingQuestionTable(tableId, question);
		}
		else if (type.equals(AbstractQuestion.TYPE_CATEGORICAL_MULTIPLE_CHOICE) || type.equals(AbstractQuestion.TYPE_CATEGORICAL_SINGLE_CHOICE))
		{
			return new CategoricalQuestionTable(tableId, question);
		}
		else // TODO other question types
		{
			return null;
		}
	}

	@Override
	public void onCreate(SQLiteDatabase database)
	{
		timeOfDayTable.createTable(database);
		for (AbstractQuestionTable table : frequencyTables.values())
		{
			table.createTable(database);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		timeOfDayTable.upgradeTable(database);
		for (AbstractQuestionTable table : frequencyTables.values())
		{
			table.upgradeTable(database);
		}
	}
	
	private void addEntry(final SQLiteDatabase database, final AbstractAnswer answer)
	{
		AbstractQuestionTable table = frequencyTables.get(answer.getId());
		if (table != null)
		{
			try
			{
				table.addEntry(database, answer);
			}
			catch (SQLiteException e)
			{
				if (e.getMessage().toString().contains("no such table"))
				{
					table.createTable(database);
					addEntry(database, answer);
				}
			}
		}
	}
	
	public void addResponse(final AnswerList answers)
	{
		SQLiteDatabase database = getWritableDatabase();
		timeOfDayTable.addEntry(database);
		for (AbstractAnswer answer : answers.getAnswers())
		{
			if (answer.getType().equals(AbstractQuestion.TYPE_RATING_LIST))
			{
				RatingAnswer[] ratings = ((RatingListAnswer) answer).getRatings();
				for (RatingAnswer rating : ratings)
				{
					addEntry(database, rating);
				}
			}
			else
			{
				addEntry(database, answer);
			}
		}
		database.close();
	}
	
	public Distribution getFrequency(final AbstractQuestion question)
	{
		AbstractQuestionTable table = frequencyTables.get(question.getId());
		if (table != null)
		{
			SQLiteDatabase database = getReadableDatabase();
			Distribution distribution = getDistribution(database, table);
			database.close();
			return distribution;
		}
		else
		{
			return null;
		}
	}
	
	private Distribution getDistribution(SQLiteDatabase database, AbstractQuestionTable table)
	{
		Distribution distribution;
		try
		{
			distribution = table.getFrequencyCount(database);
		}
		catch (SQLiteException e)
		{
			if (e.getMessage().toString().contains("no such table"))
			{
				distribution = null;
			}
			else throw e;
		}
		return distribution;
	}
	
	public Distribution getTimeOfDayFrequency()
	{
		SQLiteDatabase database = getReadableDatabase();
		Distribution distribution = getDistribution(database, timeOfDayTable);
		database.close();
		return distribution;
	}
}
