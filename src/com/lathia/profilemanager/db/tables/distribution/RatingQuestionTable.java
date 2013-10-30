package com.lathia.profilemanager.db.tables.distribution;


public class RatingQuestionTable extends AbstractQuestionTable
{
//	
//	public RatingQuestionTable(final String tableId, final AbstractQuestion question)
//	{
//		super(tableId, question);
//	}
//
//	@Override
//	public void createTable(final SQLiteDatabase database)
//	{
//		super.createTable(database);
//		RatingQuestion ratingQuestion = (RatingQuestion) currentQuestion;
//		for (int i=ratingQuestion.getMinValue(); i<=ratingQuestion.getMaxValue(); i++)
//		{
//			incrementField(database, getVariableName(i, ratingQuestion), 0);
//		}
//	}
//
//	@Override
//	public void addEntry(final SQLiteDatabase database, final AbstractAnswer answer)
//	{
//		RatingAnswer rating = (RatingAnswer) answer;
//		incrementField(database, getVariableName(rating), 1);
//	}
//	
//	private String getVariableName(final int ratingValue, final String ratingDescription)
//	{
//		return ratingValue+" "+ratingDescription;
//	}
//	
//	private String getVariableName(final int rating, final RatingQuestion question)
//	{
//		return getVariableName(rating, question.getDescriptionForRating(rating));
//	}
//	
//	private String getVariableName(final RatingAnswer answer)
//	{
//		return getVariableName(answer.getAnswerValue(), answer.getAnswerDescription());
//	}
}
