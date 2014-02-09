package com.boilermake.studycentral;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.amazonaws.auth.WebIdentityFederationSessionCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;

public class GroupListFragment extends Fragment {
	private static final int LOGIN_REQUEST_CODE = 0;
	private static final String LOG_TAG = "com.boilermake.studycentral.GroupListFragment";
	ListView listView;
	GroupListAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_group_list, container, false);
		
		adapter = new GroupListAdapter(getActivity());
		adapter.addItem(new Group("7:00 PM Calculus", "Library", "4 members"));
		adapter.addItem(new Group("5:00 PM English", "Mees 212", "5 members"));
		adapter.addItem(new Group("3:00 PM Physics", "BSB 218", "3 members"));
		
		listView = (ListView) view.findViewById(R.id.group_list);
		listView.setAdapter(adapter);
		
		setHasOptionsMenu(true);
		
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.group_list, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.join:
			login();
			
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	private void login() {
		Intent intent = new Intent(getActivity(), FacebookLogin.class);
		
		startActivityForResult(intent, LOGIN_REQUEST_CODE);
	}
	
	private void login(String token) {
		WebIdentityFederationSessionCredentialsProvider wif = new WebIdentityFederationSessionCredentialsProvider(token, getResources().getString(R.string.provider), getResources().getString(R.string.fb_role_arn));

		LoginTask loginTask = new LoginTask();
		loginTask.execute(wif);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_OK) {
			switch(requestCode) {
			case LOGIN_REQUEST_CODE:
				login(data.getStringExtra(FacebookLogin.TOKEN_KEY));
				
				break;
			}
		}
	}
	
	private class LoginTask extends AsyncTask<WebIdentityFederationSessionCredentialsProvider, Void, AmazonDynamoDBAsyncClient> {

		@Override
		protected AmazonDynamoDBAsyncClient doInBackground(WebIdentityFederationSessionCredentialsProvider... params) {
			params[0].refresh();
			
			AmazonDynamoDBAsyncClient db = new AmazonDynamoDBAsyncClient(params[0]);
			db.setRegion(Region.getRegion(Regions.US_WEST_2));
			
			
			
			return db;
		}
		
	}
}
