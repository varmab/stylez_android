package com.stylez.activities1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import twitter4j.User;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionDefaultAudience;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.internal.SessionTracker;
import com.facebook.internal.Utility;
import com.facebook.model.GraphUser;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.squareup.picasso.Picasso;
import com.stylez.activities1.CustomMultiPartEntity.ProgressListener;
import com.stylez.helpers.DataUrls;
import com.stylez.helpers.FileCache;
import com.stylez.helpers.ImageLoader;
import com.stylez.helpers.MyProgressDialog;
import com.stylez.helpers.UrltoValue;
import com.stylez.helpers.Utils;
import com.stylez.pojos.StyleZItem;
import com.stylez.thirdpartyTool.HorizontalListView;
import com.stylez.twitter.Twitt;
import com.stylez.twitter.TwitterApp;
import com.stylez.twitter.TwitterApp.TwDialogListener;

@SuppressLint("NewApi")
public class GridviewItemActivity  extends Activity implements OnClickListener
{
	HashMap<String, String> shareTo=new HashMap<String, String>();
	TextView txtviewstylename,txtviewhairconstruction,txtviewsharewith;
	ImageView imgviewgetit,imgviewhearttaboff,imgviewshare,imgviewback,imgviewbgimg,imgviewback1,imgviewback2,imgviewfb,imgviewmenoff,imgviewtwitter,imgviewpin,imgviewinst,imgviewgmail,imgviewsharebutton,imgviewsharebtn;
	String strCurrentuser,strAccesstoken;
	ImageView imgviewheartoff,imgviewhearton,imgviewhomeoff,imgviewupdotaboff;
	WebView webview;
	Twitt twitt;
	public static String consumer_key="FTP0Zn6AkSvbcFcoNgUuuwqhq",secret_key="TTL75JQNc33zOU2sK879YazBYBC9jDI5tyC5Se1Bhw332cVe0K";
	RelativeLayout rel_share,rel_gridviewitem,rel_hairconstruction;
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions","publish_stream","user_photos");
	HorizontalListView horizontalListview;
	String imageUrl="",userid="",styleId="",url="",stylename="",enable="",tab="";
	ImageLoader imageLoader;
	List<String> listsubstyles= new ArrayList<String>();
	ArrayList<String> al_listsubstyles=new ArrayList<String>();
	StyleZItem styleZItem;
	SharedPreferences preferences;
	Typeface tf;
	Activity activity;
	 
	public void onCreate(Bundle b)
	{
			super.onCreate(b);
			setContentView(R.layout.gridviewitem);
			url=getIntent().getExtras().getString("url");
			enable=getIntent().getExtras().getString("enable");
			tab=getIntent().getExtras().getString("tab");
			styleId=getIntent().getExtras().getString("styleId");
		
			txtviewstylename=(TextView)findViewById(R.id.txtviewstylename);
		 	txtviewsharewith=(TextView)findViewById(R.id.txtviewsharewith);
	        imgviewgetit=(ImageView)findViewById(R.id.imgviewgetit);
	        imgviewshare=(ImageView)findViewById(R.id.imgviewshare);
	        imgviewback=(ImageView)findViewById(R.id.imgviewback);
	        imgviewbgimg=(ImageView)findViewById(R.id.imgviewbgimg);
	        
	        //for sharing
	        rel_gridviewitem=(RelativeLayout)findViewById(R.id.rel_gridviewitem);
	        rel_share=(RelativeLayout)findViewById(R.id.rel_share);
	        imgviewback1=(ImageView)findViewById(R.id.imgviewback1);
	        imgviewback2=(ImageView)findViewById(R.id.imgviewback2);
	        imgviewsharebtn=(ImageView)findViewById(R.id.imgviewsharebtn);
	        
	    	tf = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeue-ThinCond.otf");
	    	txtviewstylename.setTypeface(tf);
	        txtviewsharewith.setTypeface(tf);

	        imgviewupdotaboff=(ImageView)findViewById(R.id.imgviewupdotaboff);
	        imgviewupdotaboff.setOnClickListener(this);
	        
	        imgviewhomeoff=(ImageView)findViewById(R.id.imgviewhomeoff);
	        imgviewback.setOnClickListener(this);
	        imgviewback1.setOnClickListener(this);
	        imgviewback2.setOnClickListener(this);
	        imgviewsharebtn.setOnClickListener(this);
	        imgviewgetit.setOnClickListener(this);
	        
	        imgviewfb = (ImageView)findViewById(R.id.imgviewfb);
	        imgviewfb.setOnClickListener(this);
	        
	        imgviewtwitter = (ImageView)findViewById(R.id.imgviewtwitter);
	        imgviewtwitter.setOnClickListener(this);
	        
	        imgviewpin = (ImageView)findViewById(R.id.imgviewpin);
	        imgviewpin.setOnClickListener(this);
	        
	        imgviewinst = (ImageView)findViewById(R.id.imgviewinst);
	        imgviewinst.setOnClickListener(this);
	        
	        imgviewgmail = (ImageView)findViewById(R.id.imgviewgmail);
	        imgviewgmail.setOnClickListener(this);
	        
	        imgviewshare = (ImageView)findViewById(R.id.imgviewshare);
	        imgviewshare.setOnClickListener(this);
			
	        imageUrl=	getIntent().getExtras().getString("imageUrl");
	        stylename=	getIntent().getExtras().getString("stylename");
	        
	        Log.e("imageURL",""+imageUrl);
	        
	        rel_share.setOnClickListener(this);
	        webview=(WebView)findViewById(R.id.webview);
	        txtviewhairconstruction=(TextView)findViewById(R.id.txtviewhairconstruction);
	        rel_hairconstruction=(RelativeLayout)findViewById(R.id.rel_hairconstruction);
	        txtviewhairconstruction.setTypeface(tf);
	        txtviewhairconstruction.setOnClickListener(this);
	        
	        imgviewmenoff=(ImageView)findViewById(R.id.imgviewmenoff);
	        imgviewmenoff.setOnClickListener(this);
	        
	        imgviewhearttaboff=(ImageView)findViewById(R.id.imgviewhearttaboff);
	        imgviewhearttaboff.setOnClickListener(this);

	        imgviewhomeoff.setOnClickListener(this);
	        
	        preferences = PreferenceManager.getDefaultSharedPreferences(this);
	       
	        userid=preferences.getString("userid", "");
		
	        imgviewheartoff=(ImageView)findViewById(R.id.imgviewheartoff);
	        imgviewhearton=(ImageView)findViewById(R.id.imgviewhearton);
	        
	        if(enable.equals("false"))
			{
				imgviewheartoff.setVisibility(View.VISIBLE);
				imgviewhearton.setVisibility(View.GONE);
			}
			else
			{
				imgviewheartoff.setVisibility(View.GONE);
				imgviewhearton.setVisibility(View.VISIBLE);
			}
	        
	        Log.e("TAb","<>"+tab);
	        
	        if(tab.equals("1"))
	        {
	        	imgviewhearttaboff.setVisibility(View.GONE);
	        }
	        else
	        {
	        	imgviewhearttaboff.setVisibility(View.VISIBLE);
	        }
	        
	        imgviewheartoff.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					imgviewhearton.setVisibility(View.VISIBLE);
					imgviewheartoff.setVisibility(View.GONE);
					new GridItem1(styleId).execute();
				}
			});
			  
			imgviewhearton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						imgviewheartoff.setVisibility(View.VISIBLE);
						imgviewhearton.setVisibility(View.GONE);
						new GridItem1(styleId).execute(); 
					}
			});
	        
	        horizontalListview=(HorizontalListView)findViewById(R.id.horizontalListview);
	        
	        HorizontalImageadapter imageadapter= new HorizontalImageadapter(GridviewItemActivity.this, 123,  listsubstyles);
	      
	        horizontalListview.setAdapter(imageadapter);
	        
	        horizontalListview.setOnItemClickListener(new OnItemClickListener() {
				@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					imageUrl=listsubstyles.get(arg2).replaceAll(" ", "%20");
					//imageLoader.DisplayImage(imageUrl, imgviewbgimg);
					Picasso.with(getParent()).load(imageUrl).into(imgviewbgimg);
					//horizontalListview.setSelection(arg2);
				}
			});
	        
	       
	        imageLoader= new ImageLoader(this);
	        new GridItem().execute();
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==imgviewback)
		{
			Log.e("back is ","imgviewback pressed");
			TabActivityActivity.group.back();
		}
		else if(v==imgviewshare)
		{
			rel_share.setVisibility(View.VISIBLE);
			Log.e("click","sharebtn");
		}
		else if(v==imgviewgetit)
		{
			Log.e("imageurl is",""+styleZItem.getImage());
			Log.e("style name is",""+styleZItem.getStyleName());
			Log.e("style id is",""+styleZItem.getStyleId());
			DataUrls.click=0;
			
			View vi =TabActivityActivity.group.getLocalActivityManager()  
			         .startActivity("Items", new Intent(getApplicationContext(),GetitActivity.class)
			         .putExtra("imageurl", ""+styleZItem.getImage())
			         .putExtra("stylename", ""+styleZItem.getStyleName())
			         .putExtra("styleId", styleZItem.getStyleId())
			         .putStringArrayListExtra("al_listsubstyles", al_listsubstyles)
			         .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
			         .getDecorView();  
		
		           TabActivityActivity.group.replaceView(vi);
		}
		
		else if(v.equals(imgviewback1))
		{
			Log.e("back is ","imgviewback1 pressed");
			rel_share.setVisibility(View.GONE);
//			resetShare();
		}
		else if(v.equals(imgviewback2))
		{
			Log.e("back is ","imgviewback2 pressed");
			rel_hairconstruction.setVisibility(View.GONE);
			rel_gridviewitem.setVisibility(View.VISIBLE);
		}
		else if(v.equals(txtviewhairconstruction))
		{
			rel_hairconstruction.setVisibility(View.VISIBLE);
			rel_gridviewitem.setVisibility(View.GONE);
			webview.loadUrl("http://hairconstruction.us/");
		}
		else if(v==imgviewfb)
		{
			Log.e("Share to ", "Facebook");
			if(shareTo.containsKey("1"))
			{
				imgviewfb.setBackgroundResource(R.drawable.facebook_off);
				shareTo.remove("1");
			}
			else
			{
				imgviewfb.setBackgroundResource(R.drawable.fb);
				shareTo.put("1", "1");
			}
		}
		else if(v==imgviewtwitter)
		{
			Log.e("Share to ", "Twitter");
			if(shareTo.containsKey("2"))
			{
				imgviewtwitter.setBackgroundResource(R.drawable.twitter_off);
				shareTo.remove("2");
			}
			else
			{
				imgviewtwitter.setBackgroundResource(R.drawable.twitter_share);
				shareTo.put("2", "2");
			}
		}
		else if(v==imgviewpin)
		{
			Log.e("Share to ", "Pintrest");
			if(shareTo.containsKey("3"))
			{
				imgviewpin.setBackgroundResource(R.drawable.pinterest_off);
				shareTo.remove("3");
			}else{
				imgviewpin.setBackgroundResource(R.drawable.p);
				shareTo.put("3", "3");
			}
		}
		else if(v==imgviewinst)
		{
			Log.e("Share to ", "Instagram");
			if(shareTo.containsKey("4"))
			{
				imgviewinst.setBackgroundResource(R.drawable.instagram_off);
				shareTo.remove("4");
			}else{
				imgviewinst.setBackgroundResource(R.drawable.instagram);
				shareTo.put("4", "4");
			}
		}
		else if(v==imgviewgmail)
		{
			Log.e("Share to ", "Email");
			if(shareTo.containsKey("5"))
			{
				imgviewgmail.setBackgroundResource(R.drawable.mail_off);
				shareTo.remove("5");
			}
			else
			{
				imgviewgmail.setBackgroundResource(R.drawable.mail_on);
				shareTo.put("5", "5");
			}
		}
		else if(v==imgviewsharebtn)
		{
			Log.e("Sharingggg",""+shareTo.toString());
			 if(shareTo.containsKey("2"))
			{
				Log.e("Posting to ","Twitter");
				imgviewtwitter.callOnClick();
				openTwitter();
			}
			else if(shareTo.containsKey("3"))
			{
				imgviewpin.callOnClick();
				WebView webView= new WebView(this);
				webView.loadUrl("http://www.pinterest.com/pin/create/button/?url="+imageUrl+"&media="+imageUrl+"&description=sample");
			}
			else if(shareTo.containsKey("4"))
			{
				imgviewinst.callOnClick();
				Doback_process doback_process= new Doback_process();
				doback_process.execute();
			}
			else if(shareTo.containsKey("5"))
			{
				imgviewgmail.callOnClick();
				showDialog_PopUp();
			}
			else if(shareTo.containsKey("1"))
			{
				openFacebookLogin();
				imgviewfb.callOnClick();
			}
				Log.e("<>","<>"+shareTo.toString());
		}
		else if(v==imgviewhomeoff)
		{
			Log.e("imgviewhomeoff", "is clicked "+(TabActivityActivity.history.size()-1));
			TabActivityActivity.history.clear();
			  Window wd=TabActivityActivity.group. getLocalActivityManager().startActivity("profile",new Intent(getParent(),HomeScreenActivity.class)
	          .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
	          View view = wd.getDecorView();
	          TabActivityActivity.group.   replaceView(view);
			
		}
		else if(v==rel_share)
		{
			Log.e("rel_share","rel_share");
		}
		else if(v==imgviewhearttaboff)
		{
			Log.e("clicked on","favourites");
				
			View vi =TabActivityActivity.group.getLocalActivityManager()  
			           .startActivity("Items", new Intent(getApplicationContext(),GridviewActivity.class)
			           .putExtra("from", "1")
			           .putExtra("tab", "1")
			           .putExtra("url","http://app.hairconstruction.co/api/Account/Favorites/"+userid+"?format=json")
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
		else if(v==imgviewmenoff)
		{
			Log.e("this is","profile activity");
			View vi =TabActivityActivity.group.getLocalActivityManager()  
			           .startActivity("Items", new Intent(getApplicationContext(),ProfileActivity.class)
			           .putExtra("from", "profile")
			           .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
			           .getDecorView();  
		
		           TabActivityActivity.group.replaceView(vi);
		}
		
	}

	class Doback_process extends AsyncTask<URL, Integer, Long>
	 {
		int start;
		MyProgressDialog dialog;
		File f = getOutputMediaFile();
		protected void onPreExecute() {
			 dialog=MyProgressDialog.show(getParent(), null,null);
			 dialog.getWindow().setGravity(Gravity.CENTER);
		}
	 
		@Override
		protected Long doInBackground(URL... params) {
		
		try{
	
			OutputStream os = new FileOutputStream(f);
	        Utils.CopyStream((InputStream)new URL(imageUrl).getContent(), os);
//			inputStream=(InputStream)new URL(imageUrl).getContent();
			}catch(Exception e){}
	        
		
		return null;
		}
	 
		protected void onProgressUpdate(Integer... progress) {}
		protected void onPostExecute(Long result1) {
			Log.e("====",""+f.getAbsolutePath());
			
			//........................
			dialog.dismiss();

	        Intent intent = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
	        if (intent != null)
	        {
	            Intent shareIntent = new Intent();
	            shareIntent.setAction(Intent.ACTION_SEND);
	            shareIntent.setPackage("com.instagram.android");
	            try 
	            {
//	            	private Drawable grabImageFromUrl(String url) throws Exception {
	            	     
//	            	}
	                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), ""+f.getAbsolutePath(), "I am Happy", "Share happy !")));
	            } catch (FileNotFoundException e) { 
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            shareIntent.setType("image/jpeg");

	            startActivity(shareIntent);
	        }
	        else
	        {
	            // bring user to the market to download the app.
	            // or let them choose an app?
	            intent = new Intent(Intent.ACTION_VIEW);
	            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            intent.setData(Uri.parse("market://details?id="+"com.instagram.android"));
	            startActivity(intent);
	            //Toast.makeText(getParent(), "Image posted successfully", Toast.LENGTH_LONG).show();
	        }
			
			//.........................
		}
		 
		}
	@Override
	public void onResume()
	{
		super.onResume();
		Log.e("On Resume","On Resume");
		imgviewsharebtn.callOnClick();
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		Log.e("On onPause","On onPause");
	}
	 
	 private static File getOutputMediaFile(){
        
		 
	        // Check that the SDCard is mounted
	        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	                  Environment.DIRECTORY_PICTURES), "StyleZ");
	         
	         
	        // Create the storage directory(MyCameraVideo) if it does not exist
	        if (! mediaStorageDir.exists()){
	             
	            if (! mediaStorageDir.mkdirs()){
	                Log.d("StyleZZ = ", "Failed to create directory MyCameraVideo.");
	                return null;
	            }
	        }
	        // Create a media file name
	         
	        // For unique file name appending current timeStamp with file name
	        java.util.Date date= new java.util.Date();
	        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date.getTime());
			File mediaFile;
//			if(type == MEDIA_TYPE_VIDEO) 
//			{
//	            mediaFile = new File(mediaStorageDir.getPath() + File.separator +"Swy_"+ timeStamp + ".mp4");
//	        }else if(type == MEDIA_TYPE_PICTURE) {
	           mediaFile = new File(mediaStorageDir.getPath() + File.separator +"StyleZ_"+ timeStamp + ".png");
	           
	           Log.v("file path..","..."+mediaFile.getPath());
	            
//	       } else {
//	            return null;
//	        }
	 
	        return mediaFile;
	    }
	   
	   private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) 
	   {
	         for (String string : subset) 
	         {
	             if (!superset.contains(string)) 
	             {
	                 return false;
	             }
	         }
	         return true;
	     }
	   
	   @Override
	   public void onActivityResult(int requestCode, int resultCode, Intent data) {
	       super.onActivityResult(requestCode, resultCode, data);
	       Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	       Log.e("Its in ","On activity for Result");
	   }
	   
	   //for facebook sharing
	   
	   private void openFacebookLogin()
	   {

	        Session.openActiveSession(getParent(), true, new Session.StatusCallback() {

	            // callback when session changes state
	       @SuppressWarnings("deprecation")
		@Override
	            public void call(Session session, SessionState state, Exception exception) {
	        
	        Log.e("session","open session");
	        
	              if (session.isOpened()) {
	               
	               strAccesstoken=session.getAccessToken();
	                List<String> permissions = session.getPermissions();
	                
	                Log.e("current permissions",""+permissions);
	                if (!isSubsetOf(PERMISSIONS, permissions)) {
	                  
	                    Session.NewPermissionsRequest newPermissionsRequest = new Session
	                            .NewPermissionsRequest(getParent(), PERMISSIONS);
	                session.requestNewPublishPermissions(newPermissionsRequest);
	                    return;
	                }

	                // make request to the /me API
	                Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
	                 
	                  // callback after Graph API response with user object
			@Override
			public void onCompleted(GraphUser user, Response response) {
				// TODO Auto-generated method stub

		          if (user != null) {
//		                       welcome = (TextView) findViewById(R.id.welcome);
//		                       welcome.setText("Hello " + user.getName() + "!");
		                       strCurrentuser=user.getId();
		                       
		                       Log.e("user Id",""+user.getId());
		                       publishFeedDialog(strCurrentuser);
		                       
		                       
		                     }
			}
	                });
	              }
	            }
	          });
			
	   }
	   private void publishFeedDialog(String friend_uid) {
		     
//		     openFacebookLogin();
		     
		     try{
		             Session mCurrentSession = Session.getActiveSession();
		             
		             Log.e("permissions",""+mCurrentSession);
		             Log.e("permissions",""+mCurrentSession);

		             SessionTracker mSessionTracker = new SessionTracker(
		            		 
		                     getParent(), new StatusCallback() {
		                         public void call(Session session, SessionState state,
		                                 Exception exception) {
		                         }
		                     }, null, false);
		             
		             String applicationId = Utility
		             .getMetadataApplicationId(getBaseContext());
		             mCurrentSession = mSessionTracker.getSession();

		             if (mCurrentSession == null
		                     || mCurrentSession.getState().isClosed()) {
		                 mSessionTracker.setSession(null);
		                 Session session = new Session.Builder(getBaseContext())
		                 .setApplicationId(applicationId).build();
		                 Session.setActiveSession(session);
		                 mCurrentSession = session;
		             }

		             if (!mCurrentSession.isOpened()) {
		                 Session.OpenRequest openRequest = null;
		                 openRequest = new Session.OpenRequest(
		                        GridviewItemActivity.this);

		                 if (openRequest != null) {
		                     openRequest
		                     .setDefaultAudience(SessionDefaultAudience.FRIENDS);
		                     openRequest.setPermissions(Arrays.asList("email", "publish_actions","publish_stream"));
		                     openRequest
		                     .setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);

		                     mCurrentSession.openForPublish(openRequest);
		                 }
		             }
		      
		             if (strCurrentuser != null && friend_uid != null ) {

		                 final Activity activity = this;
		                 Bundle params = new Bundle();
		                 //This is what you need to post to a friend's wall
		                
		                 //up to this
//		                 params.putString("access_token",strAccesstoken);
		                 params.putString("name", ""+styleZItem.getStyleName());
		                 params.putString("caption", "");
		                 params.putString("description", " ");
		                 params.putString("link", ""+imageUrl);
//		                 params.putString("picture", "http://3.bp.blogspot.com/-kA94wIY9W-M/UTufOFtxvCI/AAAAAAAABs4/157ZXDvKWF8/s1600/rose+color+green.jpg");
		                 params.putString("picture",imageUrl);
//		                 params.putString("to","100004387144287");
		               
		                 WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(getParent(), Session.getActiveSession(), params))
		                         .setOnCompleteListener(new OnCompleteListener() {

		                         @Override
		                         public void onComplete(Bundle values, FacebookException error) {
		                             if (error == null) {
		                                 // When the story is posted, echo the success
		                                 // and the post Id.
		                                 final String postId = values.getString("post_id");
		                                 if (postId != null) {
		                                     Toast.makeText(activity, "Image posted Successfully",Toast.LENGTH_SHORT).show();
		                                    
		                                 } else {
		                                     // User clicked the Cancel button
		                                     Toast.makeText(activity,  "Publish cancelled",Toast.LENGTH_SHORT).show();
		                                 }
		                             } else if (error instanceof FacebookOperationCanceledException) {
		                                 // User clicked the "x" button
		                                 Toast.makeText(activity,  "Publish cancelled",Toast.LENGTH_SHORT).show();
		                             } else {
		                                 // Generic, ex: network error
		                                 Toast.makeText(activity,  "Error posting story", Toast.LENGTH_SHORT).show();
		                             }
		                             
		                             imgviewsharebtn.callOnClick();
		                         }

		                     }).build();
		                 feedDialog.show();
		             }
		     }catch(Exception e)
		     {
		         Log.d("Error", ""+e.toString());
		     }
		   }
	   
	   //for twitter sharing
	   public void openTwitter()
	   {
		  {
		   twitt = new Twitt(getParent(), consumer_key, secret_key);
		   twitterApp = twitt.getTwitterApp();
			  
			twitterApp.setListener(mTwLoginDialogListener);

			if (twitterApp.hasAccessToken()) {
				
				User user=	twitterApp.getUser();
				
				 twitt.share(imageUrl);
				 imgviewsharebtn.callOnClick();
				
			} else {
				twitterApp.authorize();
				
			}
		   }
		          
	   }
	   TwitterApp twitterApp;
	   
	   private TwDialogListener mTwLoginDialogListener = new TwDialogListener() {

			public void onError(String value) {
//				showToast("Login Failed");
				twitterApp.resetAccessToken();
			}

			public void onComplete(String value) {
				
				Log.e("Its in Login","Now in Login..");

					User user=	twitterApp.getUser();
				
					  Log.e("profile image.........",".........."+user.getProfileImageURL());
		                String userid=String.valueOf(user.getId());
		                Log.e("uuuuuuuserid",userid);
		               
		                Log.e("user.getId()========>",""+user.getId());
		                
             twitt.share(imageUrl);
             imgviewsharebtn.callOnClick();
               
			}
		};

		void showToast(final String msg) {
			this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(getParent(), msg, Toast.LENGTH_SHORT).show();

				}
			});
		}
	   
	   //for post mail
	   public void postEmail(int type)
		{
			try
			{
			Intent email = new Intent(Intent.ACTION_SEND);
			email.putExtra(Intent.EXTRA_EMAIL, new String[]{" "});
			if(type==1)
			{			
				email.putExtra(Intent.EXTRA_SUBJECT, "Appointment request...");
				String html = "<!DOCTYPE html><html><body> Get access to the hairstyle recipe to give me this look! <br/> <a href=\"http://hairconstruction.us\" target=\"_blank\">HairConstruction.us</a><br/>" + "Style: "+styleZItem.getStyleName()+"</body></html>";
				email.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(html));
//				email.putExtra(Intent.EXTRA_TEXT, "Get access to the hairstyle recipe to give me this look!"+"http://hairconstruction.us/"+"Style: "+stylename);
			}else{
				email.putExtra(Intent.EXTRA_SUBJECT, "I found a hairstyle for you!");
				String html = "<!DOCTYPE html><html><body> Pick a style on the <a href=\"https://play.google.com/store/apps/details?id=com.stylez.activities\" target=\"_blank\">StyleZ app</a> and request an appointment with me! <br/> <a href=\"http://stylez.hairconstruction.co\" target=\"_blank\">stylez.hairconstruction.co</a><br/> " + "Style: "+styleZItem.getStyleName()+"</body></html>";
				email.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(html));
			}
			
			FileCache fileCache = new FileCache(getParent());
			File file=fileCache.getFile(imageUrl);
			 
			File f = getOutputMediaFile();
			OutputStream os = new FileOutputStream(f);
			InputStream inputStream= new FileInputStream(file);
		    Utils.CopyStream(inputStream, os);
			
			Uri uri = Uri.fromFile(f);
			email.putExtra(Intent.EXTRA_STREAM, uri);
			email.setType("message/rfc822");
			
			final PackageManager pm = getPackageManager();
		    final List<ResolveInfo> matches = pm.queryIntentActivities(email, 0);
		    
		    ResolveInfo best = null;
		    for(final ResolveInfo info : matches)
		        if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
		            best = info;
		    if (best != null)
		        email.setClassName(best.activityInfo.packageName, best.activityInfo.name);
		    startActivity(email);
		    
		    InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
			 imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		    
			}
			catch(Exception e){}
		}
	
	class GridItem extends AsyncTask<URL, Integer, Long>
	{
		MyProgressDialog dialog;
		String jsonresponse;
		protected void onPreExecute() 
		  { 
			  dialog = MyProgressDialog.show(getParent(), null,null);
			 dialog.getWindow().setGravity(Gravity.CENTER);
		  }
		
		protected Long doInBackground(URL... arg0) 
		{
//			String url = "http://stylez.testshell.net/api/Styles/Getstyle/"+styleId+"/"+userid+"?format=json";
			String url="http://app.hairconstruction.co/api/Styles/Getstyle/"+styleId+"/"+userid+"?format=json";
			Log.e("url is",url);
			
			try
			{
				jsonresponse=UrltoValue.getValuefromUrl(url);
				Log.e("json response is",jsonresponse);
			}
			catch(TimeoutException e){}
			
			return null;	
		}
		
		protected void onPostExecute(Long result) 
		 {
			dialog.dismiss();
			
			
			Log.e("jsonresponse",""+ jsonresponse);
			try{
			JSONObject jsonObject = new JSONObject(jsonresponse);
			if(jsonObject.getString("status").toLowerCase().equals("success"))
			{
				Log.e("111","111");
				JSONObject object= jsonObject.getJSONObject("style");
				Log.e("2","2");
			    styleZItem= new StyleZItem(
			    		  							object.getString("subCatId"), 
			    		  							"NOGENDER", 
			    		  							object.getString("stylePic"), 
			    		  							object.getString("styleId"),
			    		  							object.getString("userId"),
			    		  							object.getString("styleName"), 
			    		  							object.getString("enable")
			    		  							
			    		  						);
//			    listsubstyles.add(styleZItem.getImage());
				JSONArray array= object.getJSONArray("listsubstyles");
				for(int i=0;i<array.length();i++)
				{
					JSONObject object2= array.getJSONObject(i);
					object2.getString("image");
					listsubstyles.add(object2.getString("image"));
					al_listsubstyles.add(object2.getString("image"));
//					Log.e("3","3");
				}
				Log.e("4","4");
				Log.e("imgviewbgimg","<>"+styleZItem.getImage().replaceAll(" ", "%20"));
				Log.e("5","5");
				imageUrl=styleZItem.getImage().replaceAll(" ", "%20");
				imageLoader.DisplayImage(imageUrl, imgviewbgimg);
				txtviewstylename.setText(styleZItem.getStyleName());
				Log.e("6","6");
				horizontalListview.setSelection(0);
				 
		        if(styleZItem.getEnable().equals("false"))
				{
					imgviewheartoff.setVisibility(View.VISIBLE);
					imgviewhearton.setVisibility(View.GONE);
				}
				else
				{
					imgviewheartoff.setVisibility(View.GONE);
					imgviewhearton.setVisibility(View.VISIBLE);
				}
				
			}
		
			}catch(Exception e){
				e.printStackTrace();
			}
	 }
	}
	
	public class HorizontalImageadapter extends ArrayAdapter<String>
	{

		List<String> listsubstyles = new ArrayList<String>();
		Context context;
		
		public HorizontalImageadapter(Context context, int textViewResourceId,List<String> listsubstyles) {
			super(context, listsubstyles.size(),listsubstyles);
			// TODO Auto-generated constructor stub
			this.listsubstyles=listsubstyles;
			this.context=context;
		}

		public View getView(int position, View convertView, ViewGroup parent) 
		{
			
			LayoutInflater inflater=GridviewItemActivity.this.getLayoutInflater();	
			
			View  gridView = inflater.inflate(R.layout.horizontal_item_styles, null);
	         ImageView iv = (ImageView)gridView.findViewById(R.id.imgviewbuzz) ;
//	        int i= Integer.parseInt(listsubstyles.get(position));
	         //imageLoader.DisplayImage(listsubstyles.get(position).replaceAll(" ", "%20"), iv);
	         
	         Picasso.with(context).load(listsubstyles.get(position).replaceAll(" ", "%20")).into(iv);
	        
	        RelativeLayout rel_text= (RelativeLayout)gridView.findViewById(R.id.rel_text);
	        rel_text.setVisibility(View.GONE);
			return gridView;
		}
	}
	
	public void resetShare()
	{
		  	imgviewfb .setBackgroundResource(R.drawable.facebook_off);
	        
	        imgviewtwitter.setBackgroundResource(R.drawable.twitter_off);
	        
	        imgviewpin.setBackgroundResource(R.drawable.pinterest_off);
	        
	        imgviewinst.setBackgroundResource(R.drawable.instagram_off);
	        
	        imgviewgmail.setBackgroundResource(R.drawable.mail_off);
	        
	}

  	public void showDialog_PopUp()
     {
  	    final Dialog dialog = new Dialog(getParent(),R.style.PauseDialog);
  	    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
  	    dialog.setContentView(R.layout.social_share);
  	          dialog.setTitle("Social Sharing");
  	          dialog.setCancelable(false);
  	          dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
  	          LinearLayout mailToStylist= (LinearLayout)dialog.findViewById(R.id.mailToStylist);
  	          LinearLayout mailToFriends= (LinearLayout)dialog.findViewById(R.id.mailToFriends);
  	          Button cancel= (Button)dialog.findViewById(R.id.cancel);
  	          
  	        mailToStylist.setOnClickListener(new View.OnClickListener() 
  		        {
  		        	public void onClick(View view) 
  		        	{
  		        		Log.e("Chosed ","mailToStylist");
  		        		dialog.dismiss();
  		        		postEmail(1);
  		        	}
  		        });
  	          cancel.setOnClickListener(new View.OnClickListener() 
  		        {
  		        	public void onClick(View view) 
  		        	{
  		        		Log.e("Cancel","cancellll");
  		        		dialog.dismiss();
  		        	}
  		        });
  	          
  	        mailToFriends .setOnClickListener(new View.OnClickListener() 
		        {
		        	public void onClick(View view) 
		        	{
		        		Log.e("Chosed ","mailToFriends");
		        		dialog.dismiss();
		        		postEmail(2);
		        	}
		        });
  	          
  	          dialog.show();
  		     
	  	     }
  	
  	class GridItem1 extends AsyncTask<URL, Integer, Long>
	{
		MyProgressDialog dialog;
		String jsonresponse;
		int nt;
		public String styleId;
		public  GridItem1(String styleId) 
		{
			super();
			this.styleId= styleId;
		}

		protected void onPreExecute() 
		  { 
			  dialog = MyProgressDialog.show(getParent(), null,null);
			 dialog.getWindow().setGravity(Gravity.CENTER);
		  }
		
		protected Long doInBackground(URL... arg0) 
		{
			
			 SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(GridviewItemActivity.this);
		        String userid = preferences.getString("userid", "");

		       HttpClient httpClient = new DefaultHttpClient();
		       HttpContext localContext = new BasicHttpContext();
		       HttpPost httpPost = new HttpPost("http://app.hairconstruction.co/api/Account/Favorites/?format=json");
		    
		      ProgressListener listener= new ProgressListener() {
		    
		    @Override
		    public void transferred(long num) {}
		   };
		   			
			try
			{
				CustomMultiPartEntity multipartContent = new CustomMultiPartEntity(listener);
				multipartContent.addPart("userid", new StringBody(userid));
				multipartContent.addPart("styleid", new StringBody(styleId));
				httpPost.setEntity(multipartContent);
		        HttpResponse response = httpClient.execute(httpPost, localContext);
		        String serverResponse = EntityUtils.toString(response.getEntity());
		        jsonresponse=serverResponse;
			}
			catch(Exception e){}
			return null;	
		}
		protected void onPostExecute(Long result) 
		 {
			dialog.dismiss();
			Log.e("jsonresponse",""+ jsonresponse);
		 }
		}
  	@Override  
    public void onBackPressed() 
  	{ 
    TabActivityActivity.group.back();
    return;  
   }
}
