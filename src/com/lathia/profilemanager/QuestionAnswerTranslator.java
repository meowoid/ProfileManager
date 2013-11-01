package com.lathia.profilemanager;

import java.util.HashMap;
import java.util.List;

import com.lathia.surveymanager.data.answers.AbstractAnswer;
import com.lathia.surveymanager.data.answers.StringListAnswer;
import com.lathia.surveymanager.data.questions.AbstractQuestion;

public class QuestionAnswerTranslator
{
	public static HashMap<String, List<String>> getVariableValue(AbstractQuestion question, AbstractAnswer answer)
	{
		String type = answer.getType();
		if (type.equals(AbstractQuestion.TYPE_CATEGORICAL_SINGLE_CHOICE) || type.equals(AbstractQuestion.TYPE_CATEGORICAL_MULTIPLE_CHOICE))
		{
			return getCategoricalValues(question, (StringListAnswer) answer);
		}
		else if (type.equals(AbstractQuestion.TYPE_RANDOM_SAMPLE))
		{
			// TODO
			// TODO reverse scoring
		}
		else if (type.equals(AbstractQuestion.TYPE_RATING_LIST))
		{
			// TODO
			// TODO reverse scoring
		}
		return null;
	}
	
	private static HashMap<String, List<String>> getCategoricalValues(AbstractQuestion question, StringListAnswer categoricalAnswer)
	{
		if (question.includeInScoring())
		{
			String variableName = question.getVariable();
			HashMap<String, List<String>> variableMap = new HashMap<String, List<String>>();
			
			variableMap.put(variableName, categoricalAnswer.getAnswerList());
			return variableMap;
		}
		else
		{
			return null;
		}
	}
	
//	private static HashMap<String, List<String>> getRatingValues(RatingList ratingListQuestion, RatingListAnswer ratingListAnswer)
//	{
//		HashMap<String, List<String>> variableMap = new HashMap<String, List<String>>();
//		RatingAnswer[] ratingAnswers = ratingListAnswer.getRatings();
//		RatingQuestion[] ratingQuestions = ratingListQuestion.getQuestions();
//		for (RatingQuestion rating : ratingQuestions)
//		{
//			if (rating.includeInScoring())
//			{
//				String variableName = rating.getVariable();
//				String variableValue = getValue(rating.getId(), ratingAnswers);
//				if (variableValue != null)
//				{
//					
//				}
//			}
//		}
//		return variableMap;
//	}
//	
//	private static String getValue(String ratingId, RatingAnswer[] ratingAnswers)
//	{
//		for (RatingAnswer rating : ratingAnswers)
//		{
//			if (rating.getId().equals(ratingId))
//			{
//				return rating.getAnswerValue()+" " +rating.getAnswerDescription();
//			}
//		}
//		return null;
//	}
}
