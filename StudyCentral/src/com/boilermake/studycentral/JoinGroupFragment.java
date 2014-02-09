package com.boilermake.studycentral;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.boilermake.studycentral.facebook.FacebookActivity;
import com.boilermake.studycentral.facebook.LoginTask.LoginTaskCallback;

public class JoinGroupFragment extends Fragment implements LoginTaskCallback {
	private DynamoDBMapper mapper;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		((FacebookActivity) getActivity()).addListener(this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_join, container, false);
		
		//setHasOptionsMenu(true);
		
		return view;
	}

	@Override
	public void onLoginCompleted(DynamoDBMapper mapper) {
		this.mapper = mapper;
	}
}
