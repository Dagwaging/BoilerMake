package com.boilermake.studycentral;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class GroupListFragment extends Fragment {
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
}
