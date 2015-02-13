package com.stylez.activities1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class SplashScreenActivity extends Activity 
{
	Thread splashThread;
	String userid="",checkbox="false",pagination="";
	SharedPreferences  prefs;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		prefs = PreferenceManager.getDefaultSharedPreferences(SplashScreenActivity.this);
		userid=prefs.getString("userid", "");
		Log.e("userid is","<><><><>"+userid);
		checkbox=prefs.getString("checkbox", "false");
		Log.e("ckkbox value is","<><><><><>"+checkbox);
		pagination=prefs.getString("page", "0");
		Log.e("pagination value is","<><>"+pagination);
		
	    splashThread= new Thread() 
        {
            public void run() 
            {
               try 
               {
                  int waited = 0;
                  while (waited < 2000) 
                  {
                     sleep(100);
                     waited += 100;                                          
                  }
               } 
               catch (InterruptedException e) 
               {
                  // do nothing
               }
               finally 
               {  
            	Log.e("ckeck box value is",checkbox);

            	if(pagination.equals("0"))
            	{
            		Log.e("it ids in","pagination if block");
            		Intent pagination=new Intent(SplashScreenActivity.this,PageIndicatorActivity.class);
               		startActivity(pagination);
               		finish();
            	}
            	else
            	{
            		Log.e("it ids in","pagination else block");
            		if(checkbox.equals("false"))
            		{
               		     Intent login=new Intent(SplashScreenActivity.this,LoginScreenActivity.class);
                      	 startActivity(login);
                      	 finish();
            		}
            		else
            		{
            			Intent main=new Intent(SplashScreenActivity.this,StyleZTab.class);
            			startActivity(main);
               			finish();
            		}
            	}
               }
            }
        };
        splashThread.start(); 
	}

	
}
