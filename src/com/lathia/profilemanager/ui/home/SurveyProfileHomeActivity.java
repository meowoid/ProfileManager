package com.lathia.profilemanager.ui.home;


public abstract class SurveyProfileHomeActivity extends AbstractProfileHomeActivity
{
//	public final static String SURVEY_FILE_NAME = "surveyFileName";
//	private final static String TIMES_OF_DAY = "Times of Day";
//	
//	private QuestionList questionList;
//	private QuestionListFrequencyDatabase database;
//
//	@Override
//	protected void loadData()
//	{
//		String questionFile = getIntent().getStringExtra(SURVEY_FILE_NAME);
//		SurveyDataPortal dataPortal = new SurveyDataPortal(this, null, questionFile);
//		dataPortal.requestDataPush();
//	}
//	
//	protected abstract String getIntentKeyForDistributionData();
//
//	@Override
//	public void onQuestionListReceived(QuestionList data)
//	{
//		super.onQuestionListReceived(data);
//		showLoadingInto(getLoadingProgressBar(), getListView(), false);
//		questionList = (QuestionList) data;
//		database = new QuestionListFrequencyDatabase(this, questionList);
//		
//		final ProfileListAdapter adapter = getAdapter(getEntryTitles());
//		showList(adapter, new OnItemClickListener()
//		{
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
//			{
//				String item = adapter.getItem(position);
//				AbstractQuestion question = getQuestion(item);
//				Distribution distribution = null;
//				if (question != null)
//				{
//					distribution = database.getFrequency(question);
//				}
//				else
//				{
//					distribution = database.getTimeOfDayFrequency();
//				}
//				
//				if (distribution != null && distribution.hasData())
//				{
//					Intent intent = new Intent(SurveyProfileHomeActivity.this, getDistributionActivityClass());
//					intent.putExtra(getIntentKeyForDistributionData(), distribution);
//					startActivity(intent);
//				}
//				else
//				{
//					Toast.makeText(SurveyProfileHomeActivity.this, getNoDataMessage(), Toast.LENGTH_SHORT).show();
//				}
//			}
//		});
//	}
//	
//	private AbstractQuestion getQuestion(String profileTitle)
//	{
//		for (AbstractQuestion question : questionList.getQuestions().values())
//		{
//			if (question.getType().equals(AbstractQuestion.TYPE_RATING_LIST))
//			{
//				RatingQuestion[] ratings = ((RatingList) question).getQuestions();
//				for (RatingQuestion rating : ratings)
//				{
//					if (profileTitle.equals(rating.getProfileEntryTitle()))
//					{
//						return rating;
//					}
//				}
//			}
//			else if (profileTitle.equals(question.getProfileEntryTitle()))
//			{
//				return question;
//			}
//		}
//		return null;
//	}
//	
//	private ArrayList<String> getEntryTitles()
//	{
//		ArrayList<String> titles = new ArrayList<String>();
//		titles.add(TIMES_OF_DAY);
//		for (AbstractQuestion question : questionList.getQuestions().values())
//		{
//			if (question.getType().equals(AbstractQuestion.TYPE_RATING_LIST))
//			{
//				RatingQuestion[] ratings = ((RatingList) question).getQuestions();
//				for (RatingQuestion rating : ratings)
//				{
//					String title = rating.getProfileEntryTitle();
//					if (title != null)
//					{
//						titles.add(title);
//					}
//				}
//			}
//			else
//			{
//				String title = question.getProfileEntryTitle();
//				if (title != null)
//				{
//					titles.add(title);
//				}
//			}
//		}
//		return titles;
//	}
}
