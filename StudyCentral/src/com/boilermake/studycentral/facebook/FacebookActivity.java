package com.boilermake.studycentral.facebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.boilermake.studycentral.facebook.LoginTask.LoginTaskCallback;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;

public class FacebookActivity extends FragmentActivity implements
		StatusCallback, LoginTaskCallback {
	private UiLifecycleHelper uiHelper;
	private Session session;
	private DynamoDBMapper mapper;
	
	public static final String SHARED_PREFERENCES = "com.boilermake.studycentral";
	public static final String PREFERENCE_NAME = "name";
	public static final String PREFERENCE_ID = "id";

	protected Session getSession() {
		return session;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		uiHelper = new UiLifecycleHelper(this, this);
		uiHelper.onCreate(savedInstanceState);
	}

	@Override
	protected void onPause() {
		super.onPause();

		uiHelper.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();

		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened())) {
			call(session, session.getState(), null);
		} else if (session != null && session.isClosed()) {
			call(session, session.getState(), null);
			session = Session.openActiveSession(this, true, this);
		} else {
			session = Session.openActiveSession(this, true, this);
		}

		uiHelper.onResume();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		uiHelper.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		uiHelper.onSaveInstanceState(outState);
	}

	@Override
	protected void onStop() {
		super.onStop();

		uiHelper.onStop();
	}

	@Override
	public void call(Session session, SessionState state, Exception exception) {
		if (session.isOpened()) {
			LoginTask loginTask = new LoginTask(this, this);
			loginTask.execute(session);
		}
	}

	@Override
	public void onLoginCompleted(DynamoDBMapper mapper) {
		this.mapper = mapper;
	}

	public DynamoDBMapper getMapper() {
		return mapper;
	}
}
