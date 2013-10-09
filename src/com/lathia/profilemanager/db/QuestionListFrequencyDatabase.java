package com.lathia.profilemanager.db;

import android.content.Context;

import com.lathia.profilemanager.data.Distribution;
import com.lathia.profilemanager.db.tables.AbstractQuestionTable;
import com.lathia.profilemanager.db.tables.CategoricalQuestionTable;
import com.lathia.surveymanager.data.QuestionList;
import com.lathia.surveymanager.data.questions.AbstractQuestion;
import com.lathia.surveymanager.data.questions.RatingList;
import com.lathia.surveymanager.data.questions.RatingQuestion;

public class QuestionListFrequencyDatabase extends FrequencyDatabase
{
	
	public QuestionListFrequencyDatabase(final Context context, final QuestionList questions)
	{
		super(context, questions.getSurveyIdentifier());
		for (AbstractQuestion question : questions.getQuestions().values())
		{
			if (question.getType().equals(AbstractQuestion.TYPE_RATING_LIST))
			{
				RatingQuestion[] ratings = ((RatingList) question).getQuestions();
				for (RatingQuestion rating : ratings)
				{
//					addTable(surveyId, rating); // TODO
				}
			}
			else
			{
//				addTable(surveyId, question); // TODO
			}
		}
	}
	
	private AbstractQuestionTable getTable(final String surveyId, AbstractQuestion question)
	{
		String type = question.getType();
		String tableId = surveyId+"_"+question.getId();
		if (type.equals(AbstractQuestion.TYPE_RATING_ENTRY))
		{
			return null;//new RatingQuestionTable(tableId, question);
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
	
//	private void addEntry(final SQLiteDatabase database, final AbstractAnswer answer)
//	{
//		AbstractQuestionTable table = frequencyTables.get(answer.getId());
//		if (table != null)
//		{
//			try
//			{
//				table.addEntry(database, answer);
//			}
//			catch (SQLiteException e)
//			{
//				if (e.getMessage().toString().contains("no such table"))
//				{
//					table.createTable(database);
//					addEntry(database, answer);
//				}
//			}
//		}
//	}
	
//	public void addResponse(final AnswerList answers)
//	{
//		SQLiteDatabase database = getWritableDatabase();
//		timeOfDayTable.addEntry(database);
//		for (AbstractAnswer answer : answers.getAnswers())
//		{
//			if (answer.getType().equals(AbstractQuestion.TYPE_RATING_LIST))
//			{
//				RatingAnswer[] ratings = ((RatingListAnswer) answer).getRatings();
//				for (RatingAnswer rating : ratings)
//				{
//					addEntry(database, rating);
//				}
//			}
//			else
//			{
//				addEntry(database, answer);
//			}
//		}
//		database.close();
//	}
	
	public Distribution getFrequency(final AbstractQuestion question)
	{
		return null; // TODO
//		return getFrequency(question.getId());
	}
}
