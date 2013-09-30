package com.lathia.profilemanager.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.lathia.surveymanager.data.QuestionList;
import com.lathia.surveymanager.ui.AbstractActivity;
import com.lathia.surveymanager.ui.SurveyReceiver;

public abstract class AbstractProfileActivity extends AbstractActivity implements SurveyReceiver
{	
	protected abstract int getLayoutId();
	
	protected abstract ListView getListView();
	
	protected abstract ProgressBar getLoadingProgressBar();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
		showLoadingInto(getLoadingProgressBar(), getListView(), true);
	}

	@Override
	public Activity getActivity()
	{
		return this;
	}
	
	@Override
	public void onQuestionListReceived(QuestionList data)
	{
		showLoadingInto(getLoadingProgressBar(), getListView(), false);
	}
	
	@Override
	public void onQuestionListErrorReceived(String errorString)
	{
		showLoadingInto(getLoadingProgressBar(), getListView(), false);
		finish();
	}
}
