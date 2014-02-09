package com.boilermake.studycentral;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.boilermake.studycentral.data.Group;
import com.boilermake.studycentral.data.Person;
import com.boilermake.studycentral.facebook.FacebookActivity;
import com.boilermake.studycentral.facebook.LoginTask.LoginTaskCallback;

public class GroupFragment extends Fragment implements LoginTaskCallback {
	public static final String KEY_ID = "id";
	private Group group;
	private String id;
	private DynamoDBMapper mapper;
	
	private TextView subject;
	private TextView location;
	private TextView time;
	private TextView days;
	private TextView size;
	private ListView members;
	
	private ArrayAdapter<String> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		id = getActivity().getIntent().getStringExtra(KEY_ID);
		
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
	}
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		((FacebookActivity) getActivity()).addListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_group, container, false);
		
		subject = (TextView) view.findViewById(R.id.subject);
		location = (TextView) view.findViewById(R.id.location);
		time = (TextView) view.findViewById(R.id.time);
		days = (TextView) view.findViewById(R.id.days);
		size = (TextView) view.findViewById(R.id.size);
		members = (ListView) view.findViewById(R.id.members);
		
		setHasOptionsMenu(true);
		
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.group, menu);
	}
	
	private class LoadGroupTask extends AsyncTask<Void, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Void... params) {
			group = mapper.load(Group.class, id);
			
			Set<String> members = group.getMembers();
			
			List<String> names = new ArrayList<String>();
			
			for(String member : members)
				names.add(mapper.load(Person.class, member).getName());
			
			return names;
		}

		@Override
		protected void onPostExecute(List<String> result) {
			subject.setText(group.getName());
			location.setText(group.getLocation());
			size.setText(Integer.toString(group.getMembers().size()));
			
			adapter.clear();
			
			adapter.addAll(result);
			
			members.setAdapter(adapter);
		}
	}

	@Override
	public void onLoginCompleted(DynamoDBMapper mapper) {
		this.mapper = mapper;
		
		LoadGroupTask loadGroupTask = new LoadGroupTask();
		loadGroupTask.execute();
	}
}
