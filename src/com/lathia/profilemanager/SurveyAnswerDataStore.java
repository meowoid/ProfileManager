package com.lathia.profilemanager;

import java.util.HashMap;

import android.content.Context;

import com.lathia.profilemanager.db.QuestionListFrequencyDatabase;
import com.lathia.surveymanager.data.AnswerList;
import com.lathia.surveymanager.data.QuestionList;
import com.lathia.surveymanager.data.answers.AbstractAnswer;
import com.lathia.surveymanager.data.questions.AbstractQuestion;

public class SurveyAnswerDataStore extends ProfileDataStore
{
	private static SurveyAnswerDataStore instance;
	
	public static SurveyAnswerDataStore getInstance(Context context)
	{
		if (instance == null)
		{
			instance = new SurveyAnswerDataStore(context);
		}
		return instance;
	}

	private SurveyAnswerDataStore(Context context)
	{
		super(context);
	}
	
	public void add(final QuestionList questions, final AnswerList answers)
	{
		HashMap<String, AbstractAnswer> answerMap = answers.getAnswerMap();
		for (String questionId : answerMap.keySet())
		{
			AbstractQuestion question = questions.get(questionId);
			
		}
		
		String tableName = questions.getSurveyIdentifier();
		QuestionListFrequencyDatabase database;
		if (databaseMap.containsKey(tableName))
		{
			database = (QuestionListFrequencyDatabase) databaseMap.get(questions.getSurveyIdentifier());
		}
		else
		{
			database = new QuestionListFrequencyDatabase(context, questions);
		}
		database.addResponse(answers);
	}

}
