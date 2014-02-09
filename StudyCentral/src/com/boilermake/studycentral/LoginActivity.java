package com.boilermake.studycentral;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Util;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
		
		ImageView button;
		Facebook fb;
		SharedPreferences sp;
		TextView welcome;
		
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			
			String APP_ID = getString(R.string.fb_app_id);
		    fb = new Facebook(APP_ID);
		    
		    welcome = (TextView) findViewById(R.id.Welcome);
			
			sp = getPreferences(MODE_PRIVATE);
			String access_token = sp.getString("access_token",null);
			long access_expires = sp.getLong("access_expires",0);
			
			if(access_token != null)
			{
				fb.setAccessToken(access_token);
			}
			if(access_expires != 0)
			{
				fb.setAccessExpires(access_expires);
			}
			
			button = (ImageView)findViewById(R.id.login);
			button.setOnClickListener(this);
		}
		
		private void afterLogIn() throws org.json.JSONException
		{
			if(fb.isSessionValid())
			{
					JSONObject obj = null;
					JSONObject emailObj = null;
					URL img_url = null;
					try{
							String jsonUser = fb.request("me");
							obj = Util.parseJson(jsonUser);
							String id = obj.optString("id");
							String name = obj.optString("name");
							String userEmail = emailObj.optString("emailID");
							
							welcome.setText("Welcome, " + name + "!");
							
					} catch(FacebookError e){
						e.printStackTrace();
					} catch(MalformedURLException e){
						e.printStackTrace();
					} catch(IOException e) {
						e.printStackTrace();
					}	
			}
		}


		/* (non-Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View v) 
		{
				//log into facebook
				fb.authorize(LoginActivity.this, new String[] {"email"},new DialogListener(){
						@Override
						public void onFacebookError(FacebookError e)
						{
							Toast.makeText(LoginActivity.this, "Could not log in. Please try again later.", Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onComplete(Bundle values) {
								Editor editor = sp.edit();
								editor.putString("access_token", fb.getAccessToken());
								editor.putLong("access_expires",fb.getAccessExpires());
								editor.commit();
						}

						@Override
						public void onError(DialogError e) {
							Toast.makeText(LoginActivity.this, "Could not log in. Please try again later.", Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onCancel() {
							Toast.makeText(LoginActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
						}
				});
		}
		
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode,data);
			fb.authorizeCallback(requestCode,resultCode,data);
		}
}

