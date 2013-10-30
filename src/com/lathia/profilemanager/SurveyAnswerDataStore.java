package com.lathia.profilemanager;

import android.content.Context;

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
	
//	public void add(final QuestionList questions, final AnswerList answers)
//	{
//		HashMap<String, AbstractAnswer> answerMap = answers.getAnswerMap();
//		for (String questionId : answerMap.keySet())
//		{
//			AbstractQuestion question = questions.get(questionId);
//			if (question.getType().equals(AbstractQuestion.TYPE_RATING_LIST))
//			{
//				
//			}
//			// TODO
//		}
//		
//		String tableName = questions.getSurveyIdentifier();
//		QuestionListFrequencyDatabase database;
//		if (databaseMap.containsKey(tableName))
//		{
//			database = (QuestionListFrequencyDatabase) databaseMap.get(questions.getSurveyIdentifier());
//		}
//		else
//		{
//			database = new QuestionListFrequencyDatabase(context, questions);
//		}
//		database.addResponse(answers);
//	}

}
