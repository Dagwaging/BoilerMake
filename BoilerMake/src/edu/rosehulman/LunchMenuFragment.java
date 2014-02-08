package edu.rosehulman;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class LunchMenuFragment extends Fragment {
	private LinearLayout breakfast;
	private TextView breakfast_text;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ScrollView view = (ScrollView) inflater.inflate(R.layout.lunchmenu, container);
		
		breakfast = (LinearLayout) view.findViewById(R.id.menu_breakfast);
		breakfast_text = (TextView) breakfast.findViewById(R.id.text_breakfast);
		
		return view;
	}
	
	@Override
	public void onStart() {
		MenuLoaderTask task = new MenuLoaderTask();
		task.execute();
	}

	private class MenuLoaderTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			breakfast_text.setText(result);
		}
		
	}
}
