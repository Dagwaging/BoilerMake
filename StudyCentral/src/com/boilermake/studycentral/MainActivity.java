package com.boilermake.studycentral;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.boilermake.studycentral.data.Group;
import com.boilermake.studycentral.data.Person;
import com.boilermake.studycentral.facebook.FacebookActivity;
import com.boilermake.studycentral.facebook.LoginTask.LoginTaskCallback;

public class MainActivity extends FacebookActivity implements
		ActionBar.TabListener {

	private static final String TAG = "com.boilermake.studycentral.MainActivity";

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	private List<LoginTaskCallback> listeners;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		listeners = new ArrayList<LoginTaskCallback>();
		
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		private final FragmentFactory[] fragments = {
				new FragmentFactory(GroupListFragment.class, "GROUPS"),
				new FragmentFactory(InviteListFragment.class, "INVITES"),
				new FragmentFactory(SuggestionListFragment.class, "SUGGESTIONS") };

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = fragments[position].createInstance();
			
			if(position == 0)
				listeners.add((GroupListFragment) fragment);
			
			return fragment;
		}

		@Override
		public int getCount() {
			return fragments.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return fragments[position].getTitle();
		}
	}

	@Override
	public void onLoginCompleted(DynamoDBMapper mapper) {
		super.onLoginCompleted(mapper);
		
		for(LoginTaskCallback listener : listeners)
			listener.onLoginCompleted(mapper);
	}
}
