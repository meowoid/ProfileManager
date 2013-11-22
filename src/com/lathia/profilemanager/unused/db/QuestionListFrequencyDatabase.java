package com.lathia.profilemanager.unused.db;


public class QuestionListFrequencyDatabase// extends FrequencyDatabase
{
	/*
	 * Old Code - Reference Only
	 */
//	private final HashMap<String, FrequencyTable> frequencyTableMap;
//	
//	public QuestionListFrequencyDatabase(final Context context, final QuestionList questions)
//	{
//		super(context, questions.getSurveyIdentifier());
//		frequencyTableMap = new HashMap<String, FrequencyTable>();
//		for (AbstractQuestion question : questions.getQuestions().values())
//		{
//			String type = question.getType();
//			if (type.equals(AbstractQuestion.TYPE_RATING_LIST))
//			{
//				RatingQuestion[] ratings = ((RatingList) question).getQuestions();
//				for (RatingQuestion rating : ratings)
//				{
//					String tableName = getTableName(rating);
//					frequencyTableMap.put(tableName, new RatingQuestionTable(tableName, rating));
//				}
//			}
//			else if (type.equals(AbstractQuestion.TYPE_CATEGORICAL_MULTIPLE_CHOICE) || type.equals(AbstractQuestion.TYPE_CATEGORICAL_SINGLE_CHOICE))
//			{
//				String tableName = getTableName(question);
//				frequencyTableMap.put(tableName, new CategoricalQuestionTable(tableName, question));
//			}
//			else
//			{
//				// other question types?
//			}
//		}
//	}
//	
//	@Override
//	public void deleteAll()
//	{
//		super.deleteAll();
//		SQLiteDatabase database = getWritableDatabase();
//		for (FrequencyTable table : frequencyTableMap.values())
//		{
//			reset(table, database);
//		}
//		database.close();
//	}
//	
//	@Override
//	public void onCreate(SQLiteDatabase database)
//	{
//		super.onCreate(database);
//		for (FrequencyTable table : frequencyTableMap.values())
//		{
//			table.createTable(database);
//		}
//	}
//	
//	@Override
//	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
//	{
//		super.onUpgrade(database, oldVersion, newVersion);
//		for (FrequencyTable table : frequencyTableMap.values())
//		{
//			table.upgradeTable(database);
//		}
//	}
//
//	private String getTableName(AbstractQuestion question)
//	{
//		return getDatabaseName()+"_"+question.getId();
//	}
//	
//	private String getTableName(String questionId)
//	{
//		return getDatabaseName()+"_"+questionId;
//	}
//	
//	private void addEntry(final SQLiteDatabase database, final AbstractAnswer answer)
//	{
//		FrequencyTable table = frequencyTableMap.get(getTableName(answer.getId()));
//		if (table != null)
//		{
//			try
//			{
//				String type = answer.getType();
//				if (type.equals(AbstractQuestion.TYPE_RATING_ENTRY))
//				{
//					RatingQuestionTable ratingTable = (RatingQuestionTable) table;
//					ratingTable.addEntry(database, answer);
//				}
//				else if (type.equals(AbstractQuestion.TYPE_CATEGORICAL_MULTIPLE_CHOICE) || type.equals(AbstractQuestion.TYPE_CATEGORICAL_SINGLE_CHOICE))
//				{
//					CategoricalQuestionTable categoricalTable = (CategoricalQuestionTable) table;
//					categoricalTable.addEntry(database, answer);
//				}
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
//	
//	public void addResponse(final AnswerList answers)
//	{
//		SQLiteDatabase database = getWritableDatabase();
//		timeOfDayTable.incrementNow(database, 1);
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
//	
//	public Distribution getFrequency(final AbstractQuestion question)
//	{
//		String tableName = getTableName(question);
//		FrequencyTable table = frequencyTableMap.get(tableName);
//		if (table != null)
//		{
//			SQLiteDatabase database = getReadableDatabase();
//			Distribution distribution = table.getDistribution(database);
//			database.close();
//			return distribution;
//		}
//		else
//		{
//			return null;
//		}
//	}
//	
//	@Override
//	public Distribution getDistribution()
//	{
//		throw new NullPointerException("No generic distribution for question list database!");
//	}
}
