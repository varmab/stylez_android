package com.stylez.activities1;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.TimeoutException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Session;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedVignetteBitmapDisplayer;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.stylez.helpers.DataUrls;
import com.stylez.helpers.MyProgressDialog;
import com.stylez.helpers.RoundedTransformationBuilder;
import com.stylez.helpers.UrltoValue;
import com.stylez.twitter.Twitt;
import com.stylez.twitter.TwitterApp;

public class ProfileActivity extends Activity implements OnClickListener
{
	ImageView imgviewback,imgvieweditprofile,imgviewprofilepic,imgviewlocation,imgviewbgprofilepic,imgviewhomeoff,imgviewhearttaboff,imgviewupdotaboff;
	TextView txtviewname,txtviewaddress,txtviewemail,txtviewlogout,txtviewmobile;
	SharedPreferences preferences;
	MyProgressDialog dialog;
	String jsonresponse="",userid="",strprofilename="",straddress="",stremail="",strphone="",strprofilepic="",strlogintype="",strprimaryemail="";
	
	DisplayImageOptions optionsprofileimge,optionsprofileimge1 ;
	
	Typeface tf;
	Transformation transformation;
	
	
	//ImageLoader1 loader;
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		imgviewback=(ImageView)findViewById(R.id.imgviewback);
        imgvieweditprofile=(ImageView)findViewById(R.id.imgvieweditprofile);
        imgviewprofilepic=(ImageView)findViewById(R.id.imgviewprofilepic);
        imgviewlocation=(ImageView)findViewById(R.id.imgviewlocation);
        imgviewbgprofilepic=(ImageView)findViewById(R.id.imgviewbgprofilepic);
        txtviewaddress=(TextView)findViewById(R.id.txtviewaddress);
        txtviewemail=(TextView)findViewById(R.id.txtviewemail);
        txtviewmobile=(TextView)findViewById(R.id.txtviewphone);
        txtviewname=(TextView)findViewById(R.id.txtviewname);
        txtviewlogout=(TextView)findViewById(R.id.txtviewlogout);
        preferences=PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
        userid=preferences.getString("userid", "");
        
          SharedPreferences.Editor editor = preferences.edit();
		  editor.remove("profilename");
		  editor.remove("profilepic");
		  editor.remove("email");
		  editor.remove("address");
		  editor.remove("phone");
		  editor.remove("primaryEmail");
		  editor.commit();
		  
        new Profile().execute();
        txtviewlogout.setOnClickListener(this);
        imgviewback.setOnClickListener(this);
        imgvieweditprofile.setOnClickListener(this);
        
    	tf = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeue-ThinCond.otf");
    	txtviewname.setTypeface(tf);
    	txtviewaddress.setTypeface(tf);
    	txtviewemail.setTypeface(tf);
    	txtviewmobile.setTypeface(tf);
        
        
        imgviewhearttaboff=(ImageView)findViewById(R.id.imgviewhearttaboff);
        imgviewhearttaboff.setOnClickListener(this);
	
        
        
        imgviewupdotaboff=(ImageView)findViewById(R.id.imgviewupdotaboff);
        imgviewupdotaboff.setOnClickListener(this);

        
        
        imgviewhomeoff=(ImageView)findViewById(R.id.imgviewhomeoff);
        imgviewhomeoff.setOnClickListener(this);
        txtviewmobile.setOnClickListener(this);
        
        transformation = new RoundedTransformationBuilder()
        .borderColor(Color.parseColor("#1d1a3d"))
        .borderWidthDp(1)
        .cornerRadiusDp(60)
        .oval(false)
        .build();
		
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if(v.equals(imgviewback))
		{
			TabActivityActivity.history.clear();
			  Window wd=TabActivityActivity.group. getLocalActivityManager().startActivity("profile",new Intent(getParent(),HomeScreenActivity.class)
	          .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
	          View view = wd.getDecorView();
	          TabActivityActivity.group.replaceView(view);
		}
		
		else if(v.equals(txtviewmobile))
		{
			showDialogReport1();
		}
		
		else if(v.equals(imgvieweditprofile))
		{
			Log.e("click on the","editprofile");
			
			View vi =TabActivityActivity.group.getLocalActivityManager()  
			           .startActivity("Items", new Intent(getApplicationContext(),EditProfileActivity.class)
			           .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
			           .getDecorView();  
		           TabActivityActivity.group.replaceView(vi);
		
		}
		
		else if(v.equals(txtviewlogout))
		{
			if (Session.getActiveSession() != null) 
			{
				Session.getActiveSession().closeAndClearTokenInformation();
			}
			else
			{
				Session.setActiveSession(null);
			}
			
			String consumer_key="FTP0Zn6AkSvbcFcoNgUuuwqhq";
			
			String secret_key="TTL75JQNc33zOU2sK879YazBYBC9jDI5tyC5Se1Bhw332cVe0K";
			
			Twitt twitt = new Twitt(ProfileActivity.this, consumer_key, secret_key);
			
			TwitterApp twitterApp= new TwitterApp(ProfileActivity.this, consumer_key, secret_key); 
			
			twitterApp.resetAccessToken();
			twitt.logout();
			
			  SharedPreferences.Editor editor = preferences.edit();
			  editor.remove("checkbox");
			  editor.remove("userid");
			  editor.remove("access_token");
			  editor.remove("facebookid");
			  editor.remove("twitter_userid");
			  editor.remove("");
			  editor.commit();
			  
			  
			  Intent login=new Intent(getParent(),LoginScreenActivity.class);
			  startActivity(login);
			  finish();
			  
		}
		
		else if(v==imgviewhomeoff)
		{
			Log.e("imgviewhomeoff", "is clicked "+(TabActivityActivity.history.size()));
			
			 TabActivityActivity.history.clear();
			  Window wd=TabActivityActivity.group. getLocalActivityManager().startActivity("profile",new Intent(getParent(),HomeScreenActivity.class)
	          .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
	          View view = wd.getDecorView();
	          TabActivityActivity.group.replaceView(view);
		}
		
		else if(v==imgviewhearttaboff)
		{
			Log.e("clicked on","imgviewhearttaboff");
						View vi =TabActivityActivity.group.getLocalActivityManager()  
			           .startActivity("Items", new Intent(getApplicationContext(),GridviewActivity.class)
			           .putExtra("from", "1")
			           .putExtra("tab", "1")
			           .putExtra("url","http://app.hairconstruction.co/api/Account/Favorites/"+TabActivityActivity.userid+"?format=json")
			           .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
			           .getDecorView();  
		           TabActivityActivity.group.replaceView(vi);
		}
		
		else if(v==imgviewupdotaboff)
		{
			View vi =TabActivityActivity.group.getLocalActivityManager()  
			           .startActivity("Items", new Intent(getApplicationContext(),GetitActivity.class)
			           .putExtra("imageurl", "")
			           .putExtra("stylename", "")
			           .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
			           .getDecorView();  
		           TabActivityActivity.group.replaceView(vi);
		}
		
	}
	
	class Profile extends AsyncTask<URL, Integer, Long>
	{

		protected void onPreExecute() 
		  { 
			 dialog=MyProgressDialog.show(getParent(), null,null);
			 dialog.getWindow().setGravity(Gravity.CENTER);
		  }
		
		protected Long doInBackground(URL... arg0) 
		{
			try 
			{
				jsonresponse=UrltoValue.getValuefromUrl("http://app.hairconstruction.co/api/account/signup/"+userid+"?format=json");
				Log.e("url is",""+"http://app.hairconstruction.co/api/account/signup/"+userid+"?format=json");
			} 
			catch (TimeoutException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.e("json response is",jsonresponse);
		
          	  try
          	  {
          		 JSONObject jo=new JSONObject(jsonresponse);
          		 SharedPreferences.Editor editor = preferences.edit();
          		
          		 if(jo.has("userName"))
          		 {
          		 strprofilename=jo.getString("userName");
          		 Log.e("str profile name is",strprofilename);
          		  editor.putString("profilename",strprofilename);
          		 }
          		
         		if(jo.has("email"))
         		{
          		 stremail=jo.getString("email");
          		 Log.e("email is",stremail);
          		 editor.putString("email", stremail);
         		}
         		
         		if(jo.has("phoneNumber"))
          		{
          		 strphone=jo.getString("phoneNumber");
          		 Log.e("phone number is",strphone);
          		editor.putString("phone", strphone);
          		}
         		
          		if(jo.has("address1"))
          		{
          		
         		 straddress=jo.getString("address1");
         		 Log.e("sddress is",straddress);
         		editor.putString("address",straddress);
          		}
          		
          		 if(jo.has("profilepic"))
          		 {
          		 strprofilepic=jo.getString("profilepic");
         		 Log.e("profile oic is",strprofilepic);
         		 editor.putString("profilepic",strprofilepic);
          		 }
          		
          		 if(jo.has("loginType"))
          		 {
          			strlogintype=jo.getString("loginType");
            		 Log.e("login type  is",strlogintype);
            		editor.putString("LoginType",strlogintype);
          		 }
          		 
          		 if(jo.has("primaryEmail"))
          		 {
          			 strprimaryemail=jo.getString("primaryEmail");
          			 Log.e("primary email is",strprimaryemail);
          			 editor.putString("primaryemail", strprimaryemail);
          		 }
     			  editor.commit();
     			
          	  }
          	  catch(JSONException e)
          	  {
          		  e.printStackTrace();
          	  }
            return null;
		}
		
		protected void onPostExecute(Long result) 
		 {	
			  
			  if(strprofilename.equals(""))
			  {
				  Log.e("profile name is","<><>"+strprofilename);
			  }
			  
			  else if(straddress.equals("") && strprofilepic.equals(""))
			  {
				  
				  Log.e("address is","empty");
				  Log.e("profile pic", "is not updated");
			  }
			  
			  else if(!straddress.equals("") && strprofilepic.equals(""))
			  {
				  imgviewlocation.setVisibility(View.VISIBLE);
				  Log.e("profile pic", "is not updated");
			  }
			  
			  else if(straddress.equals("") && !strprofilepic.equals(""))
			  {
				  Log.e("address is","empty");
				  
				  Picasso.with(getParent())
			        .load(strprofilepic.replace(" ", "%20"))
			        .fit()
			        .transform(transformation)
			        
			        .into(imgviewprofilepic);
				  
				  Picasso.with(getParent()).load(strprofilepic.replace(" ", "%20")).into(imgviewbgprofilepic);
				  
			  }
			  
			  else if(!straddress.equals("") && !strprofilepic.equals(""))
			  {
				  imgviewlocation.setVisibility(View.VISIBLE);
				  
				  Picasso.with(getParent())
			        .load(strprofilepic.replace(" ", "%20"))
			        .fit()
			        .transform(transformation)
			        
			        .into(imgviewprofilepic);
				  
				  Picasso.with(getParent()).load(strprofilepic.replace(" ", "%20")).into(imgviewbgprofilepic);
				  
			  }
			  
			  else
			  {
				  	Log.e("strprofile name is","empty");
			  }
			  
			  txtviewmobile.setText(strphone);
			  txtviewname.setText(strprofilename);
			  txtviewaddress.setText(straddress);
			  txtviewemail.setText(stremail);
			  txtviewemail.setText(strprimaryemail);
			  dialog.dismiss();
		}
	}
	
	

	 class Doback_process extends AsyncTask<URL, Integer, Long>
	 {
		MyProgressDialog dialog;
		Bitmap bitmap;

		protected void onPreExecute() {
			 dialog=MyProgressDialog.show(getParent(), null,null);
			 dialog.getWindow().setGravity(Gravity.CENTER);
			 Log.e("its on pree","jhjkjk");
		}
	 
		@Override
		protected Long doInBackground(URL... params) {
		
		try{
			Log.e("Its donesss","strprofilepic== "+strprofilepic);
			Bitmap b = BitmapFactory.decodeStream((InputStream)new URL(strprofilepic.replaceAll(" ","%20")).getContent());
			if(b!=null)
			{
				Log.e("Its donesss"," while loading bitmap..."+b.getHeight()+"<>"+b.getWidth());
				
				 bitmap= Bitmap.createScaledBitmap(b, 250, 250, true);
				Log.e("Its donesss","<>"+bitmap.getHeight()+"<>"+bitmap.getWidth());
			}
			else{
				Log.e("Its failed"," while loading bitmap...");
			}
			
			
			}catch(Exception e){}
	        
		
		return null;
		}
	 
		
		 public  Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels,int width,int height) {

		        Bitmap output = Bitmap.createBitmap(width,height, Config.ARGB_8888);
		        Canvas canvas = new Canvas(output);

		        final int color = 0xff424242;
		        final Paint paint = new Paint();
		        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		        final RectF rectF = new RectF(rect);
		        final float roundPx = pixels;

		        paint.setAntiAlias(true);
		        canvas.drawARGB(0, 0, 0, 0);
		        paint.setColor(color);
		        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		        canvas.drawBitmap(bitmap, rect, rect, paint);

		        return output;
		    }
		protected void onProgressUpdate(Integer... progress) {}
		protected void onPostExecute(Long result1) {
			
			Log.e("Its donesss"," while strprofilepic bitmap...");
			//........................
			dialog.dismiss();
			bitmap=	getRoundedCornerBitmap(bitmap, 200, 250, 250);
			imgviewprofilepic.setImageBitmap(bitmap);
			//....................
		}
		 
		}
	 
	 @Override  
	  public void onBackPressed() 
	  { 
		TabActivityActivity.group.back();
	    return;  
	  }
	 
	 public void showDialogReport1()
 	{
 		 try
 	       {
 	              final Dialog dialog = new Dialog(getParent(),R.style.PauseDialog);
 	              dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
 	              dialog.setContentView(R.layout.calltosaloon);
 	              
 	              dialog.setCancelable(false);
 	              dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
 	             
 	              TextView    txtviewcancel=(TextView)dialog.findViewById(R.id.txtviewcancel);
 	              TextView    txtviewok=(TextView)dialog.findViewById(R.id.txtviewok);
 	              TextView    txtviewsaloonnumber=(TextView)dialog.findViewById(R.id.txtviewsaloonnumber);
 	              
 	              txtviewsaloonnumber.setText(strphone);
 	              
 	              Log.e("phonenumber is",strphone);
 	              
 	              txtviewcancel.setOnClickListener(new View.OnClickListener() 
 	             {
 	             public void onClick(View view) 
 	             {
 	            	  dialog.dismiss();
 	             }
 	             });
 	             
 	              txtviewok.setOnClickListener(new View.OnClickListener() 
 	             {
 	             public void onClick(View view) 
 	             {
 	           
 	            	 Intent i=new Intent(Intent.ACTION_DIAL);
 	            	 //Uri.parse("tel:" + bundle.getString("mobilePhone"))
 	            	 
 	            	 i.setData(Uri.parse("tel:" + strphone));
 	            	 startActivity(i);
 	            	  dialog.dismiss();
 	             }
 	             });
 	      		
 	              dialog.show();
 	             
 	         }
 	         catch(Exception e)
 	         {
 	       
 	         }
 	}
}
