package com.boilermake.studycentral;

import android.os.Bundle;

import com.boilermake.studycentral.facebook.FacebookActivity;

public class GroupActivity extends FacebookActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_group);
	}
	
}
