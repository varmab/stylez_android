package com.stylez.activities1;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class StyleZTab extends TabActivity 
{
	public static TabHost tabHost;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.styleztab);
		Log.e("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA","AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		{
		
		Display display = getWindowManager().getDefaultDisplay();
		Resources ressources = getResources(); 
		 tabHost = getTabHost(); 
		
		// Android tab
		Intent intent_HomeScreen = new Intent().setClass(getApplicationContext(), TabActivityActivity.class);
		TabSpec tab_HomeScreen = tabHost
			.newTabSpec("a")
			.setIndicator( prepareTabView(getApplicationContext(), "activity", ressources.getDrawable(R.drawable.home_off)))
			.setContent(intent_HomeScreen);

		// add all tabs 
		tabHost.addTab(tab_HomeScreen);
	
		//set Windows tab as default (zero based)
		
		tabHost.setCurrentTab(0);
		
		if(   getTabHost().getCurrentTab()==0)
		{
		}
		else 
		{
			tabHost.setVisibility(View.VISIBLE);
		}

		  tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener()
	       {
	        public void onTabChanged(String arg0)
	        {
	         Log.e("tab argument is", arg0);

			  int i = getTabHost().getCurrentTab();
			   Log.i("@@@@@@@@ ANN CLICK TAB NUMBER", "------" + i);
	        	
	        		tabHost.setCurrentTabByTag(arg0);
	         
	        } 
	       });
		  //.......................................
		  
		}
		  
	}

	@SuppressWarnings("deprecation")
	public static View prepareTabView(Context context, String text, Drawable background) 
	{
	    View view = LayoutInflater.from(context).inflate(R.layout.fake_native_tab, null);
	    LinearLayout image=(LinearLayout)view.findViewById(R.id.image);
	    image.setBackgroundDrawable(background);
	    return view;
	}

	/// asynch class.................................

}
