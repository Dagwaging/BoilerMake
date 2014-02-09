package com.boilermake.studycentral;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.boilermake.studycentral.data.Group;

public class GroupListFragment extends Fragment {
	private static final String LOG_TAG = "com.boilermake.studycentral.GroupListFragment";
	ListView listView;
	GroupListAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_group_list, container, false);
		
		adapter = new GroupListAdapter(getActivity());
		adapter.addItem(new Group());
		adapter.addItem(new Group());
		adapter.addItem(new Group());
		
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
			
			
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
