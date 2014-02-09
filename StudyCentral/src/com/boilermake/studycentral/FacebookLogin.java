/*
 * Copyright 2010-2013 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 * 
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.boilermake.studycentral;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;

import com.amazonaws.auth.WebIdentityFederationSessionCredentialsProvider;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

public class FacebookLogin extends Activity {

	/**
	 * For help understanding the Facebook login flow, and objects such as
	 * Session and UiLifecycleHelper please see Facebook's getting started guide
	 * for Android, in particular
	 * https://developers.facebook.com/docs/android/login-with-facebook/ is a
	 * quick resource to understand this sample.
	 */
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	private static final String LOG_TAG = "FB_LOGIN";
	public static final String TOKEN_KEY = "com.boilermake.studycentral.FacebookLogin:token_key";
	private UiLifecycleHelper uiHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(LOG_TAG, "onCreate()");
		uiHelper = new UiLifecycleHelper(this, statusCallback);
		uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(LOG_TAG, "onResume");

		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					getPackageName(), PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d(LOG_TAG,
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}

		// We do not have a UI for this activity but the Login widget provided
		// by Facebook can still be used to control the session
		Button loginButton = new LoginButton(this);

		// For scenarios where the main activity is launched and user
		// session is not null, the session state change notification
		// may not be triggered. Trigger it if it's open/closed.
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened())) {
			onSessionStateChange(session, session.getState(), null);
		} else if (session != null && session.isClosed()) {
			onSessionStateChange(session, session.getState(), null);
			loginButton.performClick();
		} else {
			loginButton.performClick();
		}

		uiHelper.onResume();

	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(LOG_TAG, "onPause");
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(LOG_TAG, "onDestroy");
		uiHelper.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(LOG_TAG, "onActivityResult");
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(LOG_TAG, "onSaveInstanceState()");
		uiHelper.onSaveInstanceState(outState);
	}

	public void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (session.isOpened()) {
			Log.d(LOG_TAG, "session is open");

			setResult(Activity.RESULT_OK, new Intent().putExtra(TOKEN_KEY, session.getAccessToken()));
			finish();
		}
	}

	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	}
}
