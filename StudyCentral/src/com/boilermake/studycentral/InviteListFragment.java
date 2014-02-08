package com.boilermake.studycentral;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class InviteListFragment extends Fragment {
	ListView listView;
	GroupListAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_invite_list, container, false);
		
		adapter = new GroupListAdapter(getActivity());
		adapter.addItem(new Group("6:00 PM Calculus", "Olin", "6 members"));
		adapter.addItem(new Group("4:00 PM CSSE 220", "Scharpe", "3 members"));
		
		listView = (ListView) view.findViewById(R.id.group_list);
		listView.setAdapter(adapter);
		
		return view;
	}
}
