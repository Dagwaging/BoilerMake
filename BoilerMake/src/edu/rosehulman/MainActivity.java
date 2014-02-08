package edu.rosehulman;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;


public class MainActivity extends Activity {
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerToggle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.drawer);
		
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_navigation_drawer, 0, 0);
		
	}
}
