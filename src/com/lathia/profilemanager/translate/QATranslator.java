package com.lathia.profilemanager.translate;

import com.lathia.surveymanager.data.answers.AbstractAnswer;
import com.lathia.surveymanager.data.answers.RatingAnswer;
import com.lathia.surveymanager.data.answers.RatingListAnswer;
import com.lathia.surveymanager.data.answers.StringListAnswer;
import com.lathia.surveymanager.data.questions.AbstractQuestion;
import com.lathia.surveymanager.data.questions.RatingList;
import com.lathia.surveymanager.data.questions.RatingQuestion;

public class QATranslator
{
	public static QuestionListProfile getVariableValue(AbstractQuestion question, AbstractAnswer answer)
	{
		String type = answer.getType();
		if (type.equals(AbstractQuestion.TYPE_CATEGORICAL_SINGLE_CHOICE) || type.equals(AbstractQuestion.TYPE_CATEGORICAL_MULTIPLE_CHOICE))
		{
			return getCategoricalValues(question, (StringListAnswer) answer);
		}
		else if (type.equals(AbstractQuestion.TYPE_RATING_LIST) || type.equals(AbstractQuestion.TYPE_RANDOM_SAMPLE))
		{
			return getRatingValues((RatingList) question, (RatingListAnswer) answer);
		}
		return null;
	}
	
	private static QuestionListProfile getCategoricalValues(AbstractQuestion question, StringListAnswer categoricalAnswer)
	{
		if (question.includeInScoring())
		{
			String variableName = question.getVariable();
			QuestionListProfile variableMap = new QuestionListProfile();
			
			System.err.println("Adding variable: "+variableName);
			variableMap.add(variableName, categoricalAnswer.getAnswerList());
			return variableMap;
		}
		else
		{
			return null;
		}
	}
	
	private static QuestionListProfile getRatingValues(RatingList ratingListQuestion, RatingListAnswer ratingListAnswer)
	{
		QuestionListProfile variableMap = new QuestionListProfile();
		
		RatingAnswer[] ratingAnswers = ratingListAnswer.getRatings();
		RatingQuestion[] ratingQuestions = ratingListQuestion.getQuestions();
		for (RatingQuestion rating : ratingQuestions)
		{
			if (rating.includeInScoring())
			{
				try
				{
					String variableName = rating.getVariable();
					String variableValue = getValue(rating.getId(), rating, ratingAnswers);
					if (variableValue != null)
					{
						variableMap.add(variableName, variableValue);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return variableMap;
	}
	
	private static String getValue(String ratingId, RatingQuestion ratingQuestion, RatingAnswer[] ratingAnswers)
	{
		for (RatingAnswer rating : ratingAnswers)
		{
			if (rating.getId().equals(ratingId))
			{
				int ratingValue = rating.getAnswerValue();
				if (ratingQuestion.isReverseScored())
				{
					ratingValue = (ratingQuestion.getMaxValue() - ratingValue) + ratingQuestion.getMinValue();
				}
				return ratingValue+" " +ratingQuestion.getDescriptionForRating(ratingValue);
			}
		}
		return null;
	}
}
