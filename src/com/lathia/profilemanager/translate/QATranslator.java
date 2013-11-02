package com.lathia.profilemanager.translate;

import com.lathia.surveymanager.data.answers.AbstractAnswer;
import com.lathia.surveymanager.data.answers.RatingAnswer;
import com.lathia.surveymanager.data.answers.RatingListAnswer;
import com.lathia.surveymanager.data.answers.StringListAnswer;
import com.lathia.surveymanager.data.questions.AbstractCategoricalQuestion;
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
			return getCategoricalValues((AbstractCategoricalQuestion) question, (StringListAnswer) answer);
		}
		else if (type.equals(AbstractQuestion.TYPE_RATING_LIST) || type.equals(AbstractQuestion.TYPE_RANDOM_SAMPLE))
		{
			return getRatingValues((RatingList) question, (RatingListAnswer) answer);
		}
		return null;
	}
	
	private static QuestionListProfile getCategoricalValues(AbstractCategoricalQuestion question, StringListAnswer categoricalAnswer)
	{
		if (question.includeInScoring())
		{
			String variableName = question.getVariable();
			QuestionListProfile variableMap = new QuestionListProfile();
			
			System.err.println("Adding variable: "+variableName);
			variableMap.addResponse(variableName, categoricalAnswer.getAnswerList());
			variableMap.addCategory(variableName, question.getChoices());
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
						variableMap.addResponse(variableName, variableValue);
						for (int i=rating.getMinValue(); i<=rating.getMaxValue(); i++)
						{
							variableMap.addCategory(variableName, getRatingValue(i, rating));
						}
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
	
	private static String getRatingValue(int ratingValue, RatingQuestion ratingQuestion)
	{
		return ratingValue+" " +ratingQuestion.getDescriptionForRating(ratingValue);
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
				return getRatingValue(ratingValue, ratingQuestion);
			}
		}
		return null;
	}
}
