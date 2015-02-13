package com.stylez.activities1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.stylez.adapters.ViewPagerAdapter;
import com.viewpagerindicator.PageIndicator;

public class PageIndicatorActivity extends Activity 
{
	private int imageArra[] = { R.drawable.screen1, R.drawable.screen2,
			R.drawable.screen3, R.drawable.screen4,
			 };
	 ImageView imgviewbtn1,imgviewbtn2,imgviewbtn3,imgviewbtn4;
	 PageIndicator mIndicator;
		ViewPager myPager;
		View view;
		LinearLayout ll_button;
		String checkbox="false";
		SharedPreferences preferences;
		
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imgviewbtn1=(ImageView)findViewById(R.id.imgviewbtn1);
		imgviewbtn2=(ImageView)findViewById(R.id.imgviewbtn2);
		imgviewbtn3=(ImageView)findViewById(R.id.imgviewbtn3);
		imgviewbtn4=(ImageView)findViewById(R.id.imgviewbtn4);
		ll_button=(LinearLayout)findViewById(R.id.ll_button);
		
		myPager = (ViewPager)findViewById(R.id.myfivepanelpager);
		ViewPagerAdapter adapter = new ViewPagerAdapter(this, imageArra);
		myPager.setAdapter(adapter);
		myPager.setCurrentItem(0);
		
		preferences=PreferenceManager.getDefaultSharedPreferences(this);
		checkbox=preferences.getString("checkbox", "false");
		
		imgviewbtn1.setBackgroundResource(R.drawable.dot_on);
		imgviewbtn2.setBackgroundResource(R.drawable.dot_off);
		imgviewbtn3.setBackgroundResource(R.drawable.dot_off);
		imgviewbtn4.setBackgroundResource(R.drawable.dot_off);
		
		ll_button.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Log.e("checkbox value is","<><>"+checkbox);
				Log.e("itg is in","pageindicatoractivity");
				if(checkbox.equals("false"))
				{
					Intent main=new Intent(PageIndicatorActivity.this,LoginScreenActivity.class);
	    			startActivity(main);
	       			finish();
				}
				else
				{
					Intent main=new Intent(PageIndicatorActivity.this,StyleZTab.class);
	    			startActivity(main);
	       			finish();
				}
			}
		});
		
		myPager.setOnPageChangeListener(new OnPageChangeListener() 
		{
			@Override
			public void onPageSelected(int arg0) 
			{
				// TODO Auto-generated method stub
				if(arg0==0)
				{
					imgviewbtn1.setBackgroundResource(R.drawable.dot_on);
					imgviewbtn2.setBackgroundResource(R.drawable.dot_off);
					imgviewbtn3.setBackgroundResource(R.drawable.dot_off);
					imgviewbtn4.setBackgroundResource(R.drawable.dot_off);
					ll_button.setVisibility(View.GONE);
				}
				else if(arg0==1)
				{
					imgviewbtn1.setBackgroundResource(R.drawable.dot_off);
					imgviewbtn2.setBackgroundResource(R.drawable.dot_on);
					imgviewbtn3.setBackgroundResource(R.drawable.dot_off);
					imgviewbtn4.setBackgroundResource(R.drawable.dot_off);
					ll_button.setVisibility(View.GONE);
				}
				else if(arg0==2)
				{
					imgviewbtn1.setBackgroundResource(R.drawable.dot_off);
					imgviewbtn2.setBackgroundResource(R.drawable.dot_off);
					imgviewbtn3.setBackgroundResource(R.drawable.dot_on);
					imgviewbtn4.setBackgroundResource(R.drawable.dot_off);
					ll_button.setVisibility(View.GONE);
				}
				else
				{
					imgviewbtn1.setBackgroundResource(R.drawable.dot_off);
					imgviewbtn2.setBackgroundResource(R.drawable.dot_off);
					imgviewbtn3.setBackgroundResource(R.drawable.dot_off);
					imgviewbtn4.setBackgroundResource(R.drawable.dot_on);
					ll_button.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
		});
	
	}
}
