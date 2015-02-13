package com.stylez.activities1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeScreenActivity extends Activity implements OnClickListener
{
	String userid;
	ImageView imgviewmen,imgviewwomen,imgviewprofile,imgviewfavourites,imgviewcelebrities,imgviewupdos;
	TextView txtviewmen,txtviewwomen,txtviewprofile,txtviewfavorites,txtviewcelebrities,txtviewupdos;
	Typeface tf;
	SharedPreferences preferences;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen);

	    preferences = PreferenceManager.getDefaultSharedPreferences(this);
	    userid=preferences.getString("userid", "");
		imgviewmen=(ImageView)findViewById(R.id.imgviewmen);
		imgviewwomen=(ImageView)findViewById(R.id.imgviewwomen);
		imgviewprofile=(ImageView)findViewById(R.id.imgviewprofile);
		imgviewcelebrities=(ImageView)findViewById(R.id.imgviewcelebrities);
		imgviewupdos=(ImageView)findViewById(R.id.imgviewupdos);
		imgviewfavourites=(ImageView)findViewById(R.id.imgviewfavourites);
		imgviewmen.setOnClickListener(this);
		imgviewwomen.setOnClickListener(this);
		imgviewprofile.setOnClickListener(this);
		imgviewfavourites.setOnClickListener(this);
		imgviewupdos.setOnClickListener(this);
		imgviewcelebrities.setOnClickListener(this);
		
		txtviewmen=(TextView)findViewById(R.id.txtviewmen);
		txtviewwomen=(TextView)findViewById(R.id.txtviewwomen);
		txtviewprofile=(TextView)findViewById(R.id.txtviewprofile);
		txtviewfavorites=(TextView)findViewById(R.id.txtviewfavourites);
		txtviewcelebrities=(TextView)findViewById(R.id.txtviewcelebrities);
		txtviewupdos=(TextView)findViewById(R.id.txtviewupdos);
		
		tf = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeue-ThinCond.otf");
		txtviewmen.setTypeface(tf);
		txtviewwomen.setTypeface(tf);
		txtviewprofile.setTypeface(tf);
		txtviewfavorites.setTypeface(tf);
		txtviewcelebrities.setTypeface(tf);
		txtviewupdos.setTypeface(tf);
		
		SharedPreferences.Editor edit=preferences.edit();
		edit.putString("page", "1");
		edit.commit();
		
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if(v.equals(imgviewmen))
		{
			Log.e("this is","men activity");
			
			//here is the codr for intigrating tabs,..
			
			View vi =TabActivityActivity.group.getLocalActivityManager()  
			           .startActivity("Items", new Intent(getApplicationContext(),MenActivity.class)
			           .putExtra("from", "men")
			           .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
			           .getDecorView();  
		
		           TabActivityActivity.group.replaceView(vi);
			
		}
		if(v.equals(imgviewwomen))
		{
			Log.e("this is","woman activity");
			
			View vi =TabActivityActivity.group.getLocalActivityManager()  
			           .startActivity("Items", new Intent(getApplicationContext(),MenActivity.class)
			           .putExtra("from", "women")
			           .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
			           .getDecorView();  
		
		           TabActivityActivity.group.replaceView(vi);
		}
		if(v.equals(imgviewprofile))
		{
			Log.e("this is","profile activity");
 			View vi =TabActivityActivity.group.getLocalActivityManager()  
			           .startActivity("Items", new Intent(getApplicationContext(),ProfileActivity.class)
			           .putExtra("from", "profile")
			           .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
			           .getDecorView();  
		
		           TabActivityActivity.group.replaceView(vi);
		}
		if(v.equals(imgviewfavourites))
		{
			View vi =TabActivityActivity.group.getLocalActivityManager()  
			           .startActivity("Items", new Intent(getApplicationContext(),GridviewActivity.class)
			           .putExtra("tab", "1")
			            .putExtra("from", "1")
			           .putExtra("url","http://app.hairconstruction.co/api/Account/Favorites/"+userid+"?format=json")
			           .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
			           .getDecorView();  
		
		           TabActivityActivity.group.replaceView(vi);
		           
	}
		if(v.equals(imgviewupdos))
		{
			Log.e("clicked on","updos");
			
			View vi =TabActivityActivity.group.getLocalActivityManager()  
			           .startActivity("Items", new Intent(getApplicationContext(),GridviewActivity.class)
			           .putExtra("from", "from")
			           .putExtra("tab", "2")
			           .putExtra("url","http://app.hairconstruction.co/api/Styles/34/"+userid+"?format=json")
			           .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
			           .getDecorView();  
		
		           TabActivityActivity.group.replaceView(vi);
	}
		/*if(v.equals(imgviewcelebrities))
		{
			Log.e("clicked on","celebrities");
			
			View vi =TabActivityActivity.group.getLocalActivityManager()  
			           .startActivity("Items", new Intent(getApplicationContext(),GridviewActivity.class)
			           .putExtra("tab", "0")
			            .putExtra("from", "0")
			           .putExtra("url","http://app.hairconstruction.co/api/Celebrities/Female/"+userid+"?format=json")
			           .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
			           .getDecorView();  
		
		           TabActivityActivity.group.replaceView(vi);
		}*/
		
		if(v.equals(imgviewcelebrities))
		{
			Log.e("clicked on","salons");
			
			View vi =TabActivityActivity.group.getLocalActivityManager()  
			           .startActivity("Items", new Intent(getApplicationContext(),GetitActivity.class)
			           .putExtra("imageurl", "")
			           .putExtra("stylename", "")
			           .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
			           .getDecorView();  
		
		           TabActivityActivity.group.replaceView(vi);
		}
	}
	
	@Override  
    public void onBackPressed() 
	{ 
    TabActivityActivity.group.back();

    return;  
   }
}
