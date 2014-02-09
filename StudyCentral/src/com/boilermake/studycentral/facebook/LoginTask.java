package com.boilermake.studycentral.facebook;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;

import com.amazonaws.auth.WebIdentityFederationSessionCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.boilermake.studycentral.R;
import com.boilermake.studycentral.data.Person;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;

public class LoginTask extends AsyncTask<Session, Void, DynamoDBMapper> implements GraphUserCallback {
	private static final String TAG = "com.boilermake.studycentral.facebook.LoginTask";
	Context context;
	LoginTaskCallback callback;
	SharedPreferences prefs;
	String name;
	String id;
	
	public LoginTask(Context context, LoginTaskCallback callback) {
		this.context = context;
		this.callback = callback;
		
		prefs = context.getSharedPreferences(FacebookActivity.SHARED_PREFERENCES, Activity.MODE_PRIVATE);
	}
	
	@Override
	protected DynamoDBMapper doInBackground(Session... params) {
		Session session = params[0];

		WebIdentityFederationSessionCredentialsProvider wif = new WebIdentityFederationSessionCredentialsProvider(
				session.getAccessToken(), context.getResources().getString(
						R.string.provider), context.getResources().getString(
						R.string.fb_role_arn));

		wif.refresh();

		AmazonDynamoDBAsyncClient db = new AmazonDynamoDBAsyncClient(wif);
		db.setRegion(Region.getRegion(Regions.US_WEST_2));

		DynamoDBMapper result = new DynamoDBMapper(db);
		
		if(! prefs.contains(FacebookActivity.PREFERENCE_ID)) {
			Request.newMeRequest(session, this).executeAndWait();
		
			Person me = new Person();
			me.setName(name);
			me.setId(id);
			
			result.save(me);
		}
		
		return result;
	}

	@Override
	protected void onPostExecute(DynamoDBMapper result) {
		callback.onLoginCompleted(result);
	}
	
	public interface LoginTaskCallback {
		void onLoginCompleted(DynamoDBMapper mapper);
	}

	@Override
	public void onCompleted(GraphUser user, Response response) {
		name = user.getName();
		id = user.getId();
		
		Editor editor = prefs.edit();
		editor.putString(FacebookActivity.PREFERENCE_NAME, name);
		editor.putString(FacebookActivity.PREFERENCE_ID, id);
		editor.commit();
	}
}
