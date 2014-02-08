package com.boilermake.studycentral;

import android.support.v4.app.Fragment;


public class FragmentFactory {
	private Class<? extends Fragment> clazz;
	private String title;
	
	public FragmentFactory(Class<? extends Fragment> clazz, String title) {
		this.clazz = clazz;
		this.title = title;
	}
	
	public Fragment createInstance() {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	} 
	
	public String getTitle() {
		return title;
	}
}
