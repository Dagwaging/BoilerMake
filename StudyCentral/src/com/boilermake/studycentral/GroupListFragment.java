package com.boilermake.studycentral;

import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.boilermake.studycentral.data.Group;
import com.boilermake.studycentral.data.Person;
import com.boilermake.studycentral.facebook.FacebookActivity;
import com.boilermake.studycentral.facebook.LoginTask.LoginTaskCallback;

public class GroupListFragment extends Fragment implements LoginTaskCallback, OnItemClickListener {
	private static final String LOG_TAG = "com.boilermake.studycentral.GroupListFragment";
	ListView listView;
	GroupListAdapter adapter;
	private DynamoDBMapper mapper;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		((FacebookActivity) getActivity()).addListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_group_list, container, false);
		
		listView = (ListView) view.findViewById(R.id.group_list);
		
		ProgressBar progress = new ProgressBar(getActivity());
		
		getActivity().addContentView(progress, new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER));
		
		listView.setEmptyView(progress);
		
		listView.setOnItemClickListener(this);
		
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
			Intent intent = new Intent(getActivity(), JoinGroupActivity.class);
			
			startActivity(intent);
			
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	private class LoadGroupsTask extends AsyncTask<Void, Void, List<Group>> {

		@Override
		protected List<Group> doInBackground(Void... params) {
			Person self = Person.getSelf(getActivity());
			
			List<Group> result = self.getGroups(mapper);
			
			return result;
		}

		@Override
		protected void onPostExecute(List<Group> result) {
			adapter = new GroupListAdapter(getActivity());
			
			adapter.setData(result);
			
			listView.setAdapter(adapter);
		}
	}

	@Override
	public void onLoginCompleted(DynamoDBMapper mapper) {
		this.mapper = mapper;
		
		LoadGroupsTask loadGroupsTask = new LoadGroupsTask();
		loadGroupsTask.execute();
	}

	@Override
	public void onItemClick(AdapterView<?> root, View view, int position, long itemId) {
		String id = adapter.getItem(position).getId();
		
		Intent intent = new Intent(getActivity(), GroupActivity.class);
		intent.putExtra(GroupFragment.KEY_ID, id);
		startActivity(intent);
	}
}
