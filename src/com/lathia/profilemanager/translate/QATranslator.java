package com.lathia.profilemanager.translate;

import com.lathia.surveymanager.data.answers.AbstractAnswer;
import com.lathia.surveymanager.data.answers.RatingAnswer;
import com.lathia.surveymanager.data.answers.RatingListAnswer;
import com.lathia.surveymanager.data.answers.StringListAnswer;
import com.lathia.surveymanager.data.questions.AbstractCategoricalQuestion;
import com.lathia.surveymanager.data.questions.AbstractQuestion;
import com.lathia.surveymanager.data.questions.RandomSampleGroup;
import com.lathia.surveymanager.data.questions.RandomSampleQuestion;
import com.lathia.surveymanager.data.questions.RatingList;
import com.lathia.surveymanager.data.questions.RatingQuestion;

public class QATranslator
{
	public static VariableAnswerMap getVariableValue(final AbstractQuestion question, final AbstractAnswer answer)
	{
		if (answer != null)
		{
			String type = question.getType();
			if (type.equals(AbstractQuestion.TYPE_CATEGORICAL_SINGLE_CHOICE) || type.equals(AbstractQuestion.TYPE_CATEGORICAL_MULTIPLE_CHOICE))
			{
				return getCategoricalValues((AbstractCategoricalQuestion) question, (StringListAnswer) answer);
			}
			else if (type.equals(AbstractQuestion.TYPE_RATING_LIST))
			{
				return getRatingValues((RatingList) question, (RatingListAnswer) answer);
			}
			else if (type.equals(AbstractQuestion.TYPE_RANDOM_SAMPLE))
			{
				return getSampleRatingValues((RandomSampleQuestion) question, (RatingListAnswer) answer);
			}
		}
		return null;
	}
	
	private static VariableAnswerMap getCategoricalValues(AbstractCategoricalQuestion question, StringListAnswer categoricalAnswer)
	{
		if (question.includeInScoring())
		{
			String variableName = question.getVariable();
			VariableAnswerMap variableMap = new VariableAnswerMap();
			variableMap.addResponse(variableName, categoricalAnswer.getAnswerList());
			variableMap.addCategory(variableName, question.getChoices());
			return variableMap;
		}
		else
		{
			return null;
		}
	}
	
	private static void getRatingValues(VariableAnswerMap variableMap, RatingQuestion[] ratingQuestions, RatingListAnswer ratingListAnswer)
	{
		RatingAnswer[] ratingAnswers = ratingListAnswer.getRatings();
		for (RatingQuestion rating : ratingQuestions)
		{
			if (rating.includeInScoring())
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
		}
	}
	
	private static VariableAnswerMap getRatingValues(RatingList ratingListQuestion, RatingListAnswer ratingListAnswer)
	{
		VariableAnswerMap variableMap = new VariableAnswerMap();
		getRatingValues(variableMap, ratingListQuestion.getQuestions(), ratingListAnswer);
		return variableMap;
	}
	
	private static VariableAnswerMap getSampleRatingValues(RandomSampleQuestion ratingListQuestion, RatingListAnswer ratingListAnswer)
	{
		VariableAnswerMap variableMap = new VariableAnswerMap();
		RandomSampleGroup[] groups = ratingListQuestion.getGroups();
		for (RandomSampleGroup group : groups)
		{
			if (group.includeInScoring())
			{
				RatingQuestion[] ratings = group.getQuestions();
				getRatingValues(variableMap, ratings, ratingListAnswer);
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
