package com.boilermake.studycentral;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

class GroupListAdapter extends BaseAdapter {
	ArrayList<Group> groups;
	Activity activity;
	
	public GroupListAdapter(Activity activity) {
		this.activity = activity;
		groups = new ArrayList<Group>();
	}
	
	public void addItem(Group item) {
		groups.add(item);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return groups.size();
	}

	@Override
	public Group getItem(int position) {
		return groups.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup container) {
		if(convertView == null)
			convertView = activity.getLayoutInflater().inflate(R.layout.group_list_item, container, false);
		
		Group item = getItem(position);
		
		TextView itemName = (TextView) convertView.findViewById(R.id.item_name);
		TextView itemLocation = (TextView) convertView.findViewById(R.id.item_location);
		TextView itemSize = (TextView) convertView.findViewById(R.id.item_size);
		
		itemName.setText(item.name);
		itemLocation.setText(item.location);
		itemSize.setText(item.size);
		
		return convertView;
	}
}