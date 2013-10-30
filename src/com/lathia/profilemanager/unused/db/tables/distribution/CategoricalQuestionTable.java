package com.lathia.profilemanager.unused.db.tables.distribution;


public class CategoricalQuestionTable extends AbstractQuestionTable
{
	
//	public CategoricalQuestionTable(final String tableId, final AbstractQuestion question)
//	{
//		super(tableId, question);	
//	}
//	
//	@Override
//	public void createTable(SQLiteDatabase database)
//	{
//		super.createTable(database);
//		AbstractCategoricalQuestion categoricalQuestion = (AbstractCategoricalQuestion) currentQuestion;
//		ArrayList<String> options = categoricalQuestion.getChoices();
//		for (String option : options)
//		{
//			incrementField(database, option, 0);
//		}
//	}
//
//	@Override
//	public void addEntry(final SQLiteDatabase database, final AbstractAnswer answer)
//	{
//		String[] selected_categories = ((StringListAnswer) answer).getAnswer();
//		for (String selected_category : selected_categories)
//		{
//			incrementField(database, selected_category, 1);
//		}
//	}
}
