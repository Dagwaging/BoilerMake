package com.boilermake.studycentral;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.boilermake.studycentral.data.Group;
import com.boilermake.studycentral.data.Person;
import com.boilermake.studycentral.facebook.FacebookActivity;
import com.boilermake.studycentral.facebook.LoginTask.LoginTaskCallback;

public class GroupListFragment extends Fragment implements LoginTaskCallback {
	private static final String LOG_TAG = "com.boilermake.studycentral.GroupListFragment";
	ListView listView;
	FacebookActivity activity;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		this.activity = (FacebookActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_group_list, container, false);
		
		listView = (ListView) view.findViewById(R.id.group_list);
		
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
			
			
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	private class LoadGroupsTask extends AsyncTask<Void, Void, List<Group>> {

		@Override
		protected List<Group> doInBackground(Void... params) {
			Person self = Person.getSelf(getActivity());
			
			List<Group> result = self.getGroups(activity.getMapper());
			
			return result;
		}

		@Override
		protected void onPostExecute(List<Group> result) {
			GroupListAdapter adapter = new GroupListAdapter(getActivity());
			
			adapter.setData(result);
			
			listView.setAdapter(adapter);
		}
	}

	@Override
	public void onLoginCompleted(DynamoDBMapper mapper) {
		LoadGroupsTask loadGroupsTask = new LoadGroupsTask();
		loadGroupsTask.execute();
	}
}
