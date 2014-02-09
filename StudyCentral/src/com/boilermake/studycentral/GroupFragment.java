package com.boilermake.studycentral;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GroupFragment extends Fragment {
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_group, container, false);
		
		TextView subject = (TextView) view.findViewById(R.id.subject);
		TextView location = (TextView) view.findViewById(R.id.location);
		TextView time = (TextView) view.findViewById(R.id.time);
		TextView days = (TextView) view.findViewById(R.id.days);
		TextView size = (TextView) view.findViewById(R.id.size);
		
		setHasOptionsMenu(true);
		
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.group, menu);
	}
}
