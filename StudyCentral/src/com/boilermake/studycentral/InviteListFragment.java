package com.boilermake.studycentral;

import com.boilermake.studycentral.data.Group;

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
		adapter.addItem(new Group());
		adapter.addItem(new Group());
		
		listView = (ListView) view.findViewById(R.id.group_list);
		listView.setAdapter(adapter);
		
		return view;
	}
}
