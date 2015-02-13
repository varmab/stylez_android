package com.stylez.activities1;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.stylez.activities1.CustomMultiPartEntity.ProgressListener;
import com.stylez.helpers.CheckInternetConnection;
import com.stylez.helpers.DataUrls;
import com.stylez.helpers.MyProgressDialog;
import com.stylez.twitter.Twitt;
import com.stylez.twitter.TwitterApp;
import com.stylez.twitter.TwitterApp.TwDialogListener;

public class LoginScreenActivity extends Activity implements OnClickListener
{
	ImageView imgviewsignupoff,imgviewsignupon,imgviewloginoff,imgviewloginon,imgviewfacebook;//,imgviewtwitter
	EditText edttextemail,edttextpassword;
	TextView txtviewforgot,txtviewsignin,txtviewremember;
	String stremail,strpassword,url="",jsonresponse="",strstatus,strmessage,struserid,strchkbox,strphone,stremail1;
	MyProgressDialog dialog;
    SharedPreferences preferences;
	CheckBox chkboxremember;
	LinearLayout ll_fbimg;
	
    String strtwtname="",strtwtpic="",strtwtuserid="";
	
	//for facebook
	//public static String APP_ID ="614474328622406";
	public static String strFacebookId=null,socialid="",profilepicture="",firstname,lastname,fbid,strFacebookName,strFacebookUsername="",deviceId="",strFacebookEmail="",strFacebookProfilePic, access_token="",strfacebookphone,strfacebookaddress;
	public String response="",message="",regId="",userid="",strCurrentuser,strAccesstoken;
	
//	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions","publish_stream","user_photos","email");
	public static SharedPreferences mPrefs;
	private   List<String> PERMISSIONS = Arrays.asList("user_photos","email");
	 
	Typeface tf;
	 
	ProgressDialog ringProgressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginscreen);
		edttextemail=(EditText)findViewById(R.id.edttextemail);
		edttextpassword=(EditText)findViewById(R.id.edttextpwd);
		imgviewsignupoff=(ImageView)findViewById(R.id.imgviewsignupoff);
		imgviewsignupon=(ImageView)findViewById(R.id.imgviewsignupon);
		imgviewloginoff=(ImageView)findViewById(R.id.imgviewloginoff);
		imgviewloginon=(ImageView)findViewById(R.id.imgviewloginon);
		imgviewfacebook=(ImageView)findViewById(R.id.imgviewfacebook);
//		imgviewtwitter=(ImageView)findViewById(R.id.imgviewtwitter);
		txtviewforgot=(TextView)findViewById(R.id.txtviewforgot);
		txtviewsignin=(TextView)findViewById(R.id.txtviewsignin);
		txtviewremember=(TextView)findViewById(R.id.txtviewremember);
		chkboxremember=(CheckBox)findViewById(R.id.chkboxremember);
		ll_fbimg=(LinearLayout)findViewById(R.id.ll_fbimg);
		imgviewloginoff.setOnClickListener(this);
		txtviewforgot.setOnClickListener(this);
		imgviewsignupoff.setOnClickListener(this);
		imgviewfacebook.setOnClickListener(this);
//		imgviewtwitter.setOnClickListener(this);
		preferences=PreferenceManager.getDefaultSharedPreferences(this);
		
		SharedPreferences.Editor edit=preferences.edit();
		edit.putString("page", "1");
		edit.commit();
		
		tf = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeue-ThinCond.otf");
		edttextemail.setTypeface(tf);
		edttextpassword.setTypeface(tf);
		txtviewforgot.setTypeface(tf);
		txtviewsignin.setTypeface(tf);
		txtviewremember.setTypeface(tf);
		
		
		 try {
		      PackageInfo info = getPackageManager().getPackageInfo(
		      "com.stylez.activities", 
		      PackageManager.GET_SIGNATURES);
		        for (Signature signature : info.signatures) {
		         MessageDigest md = MessageDigest.getInstance("SHA");
		         md.update(signature.toByteArray());
		         System.out.println("KeyHash : "+ Base64.encodeToString(md.digest(), Base64.DEFAULT));
		          }
		     } catch (NameNotFoundException e) {
		   } catch (NoSuchAlgorithmException e) {
		  }
		
		
	}
	
	
	
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if(v==txtviewforgot)
		{
			Intent forgot=new Intent(LoginScreenActivity.this,ForgotPasswordActivity.class);
       	 	startActivity(forgot);
       	 	finish();
		}
		if(v==imgviewsignupoff)
		{
			imgviewsignupoff.setVisibility(View.GONE);
			imgviewsignupon.setVisibility(View.VISIBLE);
			Intent signup=new Intent(LoginScreenActivity.this,SignUpActivity.class);
       	 	startActivity(signup);
       	 	finish();
		}
		if(v==imgviewloginoff)
		{
			Log.e("login button is","clicked");
			validation();
			stremail=edttextemail.getText().toString();
			strpassword=edttextpassword.getText().toString();
			strchkbox= String.valueOf(chkboxremember.isChecked());
      		Log.e("chkbox value is",strchkbox);
		}
		if(v==imgviewfacebook)
		{	
			Log.e("facebook button","is clicked");
			loginToFacebook();
		}
		/*if(v==imgviewtwitter)
		{
			Log.e("twitter button ","is clicked");
			loginToTwitter();
		}*/
	}
	
	
	public void validation()
	{
		
		if(edttextemail.getText().toString().trim().length()==0)
		{
			toast("Please Enter Email");
		}
		else if(edttextpassword.getText().toString().trim().length()==0)
		{
			toast("Please Enter Password");
		}
		else
		{
			imgviewloginoff.setVisibility(View.GONE);
			imgviewloginon.setVisibility(View.VISIBLE);
			if(checkEmailCorrect(edttextemail.getText().toString().trim()))
			{
				 if(CheckInternetConnection.isOnline(this))
				 {
					
					 new Login().execute();
					 
				 }
				 else
				 {
					 toast("No Internet Connection");
				 }				
			}
			else
			{
				toast("Please Enter Valid Email");
		    }
		}
		
	}
	
	
	//for email validation
		boolean checkEmailCorrect(String email)
	     {
	      String pttn = "^\\D.+@.+\\.[a-z]+";
	      Pattern p = Pattern.compile(pttn);
	      Matcher m = p.matcher(email);
	      if(m.matches()){  return true;}
	      else{return false;}
	     }
		
		//For Toast Mesage
		public void toast(String msg)
		{
			Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
		}
		
		class Login extends AsyncTask<URL, Integer, Long>
		{
			long startTime = System.currentTimeMillis();
			protected void onPreExecute() 
			  { 
				 dialog=MyProgressDialog.show(LoginScreenActivity.this, null,null);
				 dialog.getWindow().setGravity(Gravity.CENTER);
			  }
			
			protected Long doInBackground(URL... arg0) 
			{
				url=DataUrls.login;
				
				try 
				  {
					
					HttpClient httpClient = new DefaultHttpClient();
				    HttpContext localContext = new BasicHttpContext();
				    HttpPost httpPost = new HttpPost(url);
					
				   ProgressListener listener= new ProgressListener() {
					
					@Override
					public void transferred(long num) 
					{
						// TODO Auto-generated method stub
						
					}
				};
					
					CustomMultiPartEntity multipartContent = new CustomMultiPartEntity(listener);
					 	multipartContent.addPart("Email", new StringBody(stremail));
						multipartContent.addPart("Password", new StringBody(strpassword));
					
						  httpPost.setEntity(multipartContent);
						    HttpResponse response = httpClient.execute(httpPost, localContext);
							String serverResponse = EntityUtils.toString(response.getEntity());
							Log.e("serverResponse== <><><","<> "+serverResponse);
							
							jsonresponse=serverResponse;
							
					Log.e("json response is",jsonresponse);
					}
				catch (UnsupportedEncodingException e) 
				{ 
					e.printStackTrace(); 
				} 
				catch (ClientProtocolException e) 
				{ 
					e.printStackTrace(); 
				} 
				catch (IOException e) 
				{ 
					e.printStackTrace(); 
				}
				
				if(!jsonresponse.equals("") && !jsonresponse.equals("null"))
	            {
	          	  try
	          	  {
	          		  JSONObject jo=new JSONObject(jsonresponse);
	          		  strstatus=jo.getString("status");
	          		 strmessage=jo.getString("message");
	          		 struserid=jo.getString("id");
	          		
	          	  }
	          	  catch(JSONException e)
	          	  {
	          		  e.printStackTrace();
	          	  }
	            }
				
				
				
				return null;
			}
			
			@SuppressWarnings("deprecation")
			protected void onPostExecute(Long result) 
			 {
				  dialog.dismiss();
				  if(strstatus.equals("success"))
			       {
					  SharedPreferences.Editor editor = preferences.edit();
	     			  editor.putString("userid",struserid);
	     			  Log.e("chkbo login",strchkbox);
	     			  editor.putString("checkbox", strchkbox);
	     			  editor.commit();
			       Intent i=new Intent(LoginScreenActivity.this,StyleZTab.class);
			       startActivity(i);
			       long executionTime = System.currentTimeMillis() - startTime;
					  System.out.println("execution time was ~" + executionTime + " ms");
					  Log.e("execution time was","<><>"+executionTime+"ms");

			       finish();
			       }
				  
				  else
					{
		          	  strmessage="Login Failed";
		          	  alertDialog(strmessage);
		          	 
		            }
				  //alertDialog(strmessage);
			
			 }
		}
		
		@SuppressWarnings("deprecation")
		private void alertDialog(String msg) 
		{
			// TODO Auto-generated method stub
			AlertDialog alertDialog = new AlertDialog.Builder(LoginScreenActivity.this).create();
	        alertDialog.setTitle("StyleZ");
	        alertDialog.setMessage(msg);
	        alertDialog.setButton("OK", new DialogInterface.OnClickListener() 
	        {
	        public void onClick(DialogInterface dialog, int which) {
	        	
	        // Write your code here to execute after dialog closed
	       dialog.dismiss();
	       
	       if(strstatus.equals("success"))
	       {
	       Intent i=new Intent(LoginScreenActivity.this,StyleZTab.class);
	       startActivity(i);
	       finish();
	       }
	       else
	       {
	    	   imgviewloginon.setVisibility(View.GONE);
	          imgviewloginoff.setVisibility(View.VISIBLE);
	       }
	        }
	     });
	     alertDialog.show();
		}
		
		
		
		 public void loginToFacebook()
		 {
			    Session.openActiveSession(LoginScreenActivity.this, true, new Session.StatusCallback() 
			    {

			    @SuppressWarnings("deprecation")
			    @Override
			         public void call(Session session, SessionState state, Exception exception) 
			    {
			     
			     Log.e("session",""+session.isOpened());
			     
			           if (session.isOpened()) 
			           {
			        	   
			        	   ringProgressDialog = ProgressDialog.show(LoginScreenActivity.this, "","Fetching data", true);
			               ringProgressDialog.setCancelable(true);
			        	   
			            Log.e("session","if open session");
			            strAccesstoken=session.getAccessToken();
			            List<String> permissions = session.getPermissions();
			             
			             Log.e("current permissions",""+permissions);
			             if (!isSubsetOf(PERMISSIONS, permissions)) 
			             {
			                 Session.NewPermissionsRequest newPermissionsRequest = new Session
			                         .NewPermissionsRequest(LoginScreenActivity.this, PERMISSIONS);
			                 session.requestNewReadPermissions(newPermissionsRequest);
			                 return;
			             }
			             // make request to the /me API
			             Request.executeMeRequestAsync(session, new Request.GraphUserCallback() 
			             {
			            	
			               // callback after Graph API response with user object
			            

			      @Override
			      public void onCompleted(GraphUser user, Response response) 
			      {
			       // TODO Auto-generated method stub
			       if (user != null) 
			       {
			        Log.e("session","if open user");
			        try
			        {
			        	strFacebookEmail=user.asMap().get("email").toString();
					Log.e("email","hi"+strFacebookEmail);
					strFacebookName=user.getName();
					Log.e("name",strFacebookName);
					strFacebookId=user.getId();
					Log.e("fbid",strFacebookId);
				
					  	strFacebookProfilePic= "http://graph.facebook.com/"+strFacebookId+"/picture?type=large";
						profilepicture=strFacebookProfilePic.replaceAll(" ","%20").trim();
					
						Log.e("profilepic",strFacebookProfilePic);
						
						firstname=user.getFirstName();
						lastname=user.getLastName();
						
						Log.e("firstname",firstname);
						Log.e("lastname",lastname);
						
						 mPrefs = PreferenceManager.getDefaultSharedPreferences(LoginScreenActivity.this);
					     SharedPreferences.Editor editor = mPrefs.edit();
					     editor.putString("email",strFacebookEmail);
					 	 editor.putString("access_token",strAccesstoken);
					     editor.putString("facebookid",strFacebookId);
					     editor.putString("profilepic",strFacebookProfilePic);
					     editor.putString("username",strFacebookName);
					     editor.putString("firstname",firstname);
					     editor.putString("lastname",lastname);
					     editor.putString("checkvalue","2");
					     editor.commit();
	                
					     new LoginFacebook().execute();
					 }
			        catch(Exception e)
			        {
			        	e.printStackTrace();
			        }
			                    
			                    
			      }
			       
			        // Check for publish permissions    
			            
			       
			      }
			             });
			           }
			         }
			       });
		 }
		
		 @Override
		   public void onActivityResult(int requestCode, int resultCode, Intent data) {
		       super.onActivityResult(requestCode, resultCode, data);
		       Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
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

	
		class LoginFacebook extends AsyncTask<URL, Integer, Long>
		{
			
			protected void onPreExecute() 
			  { 
			  }
			
			protected Long doInBackground(URL... arg0) 
			{
				url=DataUrls.signup;
				
				try 
				  {
					final int TIMEOUT_MILLISEC = 60000;
		        	HttpParams httpParams = new BasicHttpParams();
		        	HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
		        	HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
		        	HttpConnectionParams.setTcpNoDelay(httpParams, true);
					DefaultHttpClient httpClient = new DefaultHttpClient(); 
					HttpPost httpPost = new HttpPost(url);
					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
				    
				    nameValuePair.add(new BasicNameValuePair("Email",strFacebookEmail));
				    nameValuePair.add(new BasicNameValuePair("UserName",strFacebookName));
				    nameValuePair.add(new BasicNameValuePair("Profilepic",strFacebookProfilePic));
				    nameValuePair.add(new BasicNameValuePair("LoginType","Facebook"));
				    nameValuePair.add(new BasicNameValuePair("Address1",""));
				    nameValuePair.add(new BasicNameValuePair("PhoneNumber", ""));
				    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			
					HttpResponse httpResponse = httpClient.execute(httpPost); 
					HttpEntity httpEntity = httpResponse.getEntity(); 
					jsonresponse = EntityUtils.toString(httpEntity);

					Log.e("json response is",jsonresponse);
					}
				
				catch (UnsupportedEncodingException e) 
				{ 
					e.printStackTrace(); 
				} 
				catch (ClientProtocolException e) 
				{ 
					e.printStackTrace(); 
				} 
				catch (IOException e) 
				{ 
					e.printStackTrace(); 
				}
				
				if(!jsonresponse.equals("") && !jsonresponse.equals("null"))
	            {
	          	  try
	          	  {
	          		  JSONObject jo=new JSONObject(jsonresponse);
	          		  strstatus=jo.getString("status");
	          		  Log.e("status is",strstatus);
	          		 strmessage=jo.getString("message");
	          		 Log.e("message is",strmessage);
	          		 struserid=jo.getString("id");
	          		
	          		 //inserting userid into shared preferences
	          		 if(!struserid.equals("") && !struserid.equals("null"))
	          		 {
	          		  SharedPreferences.Editor editor = preferences.edit();
	     			  editor.putString("userid",struserid);
	     			  editor.commit();
	     			  }
	     			
	          	  }catch(JSONException e)
	          	  {
	          		  e.printStackTrace();
	          	  }
	            }
				else
				{
	          	  //strmessage="Login Failed";
	          	  alertDialog(strmessage);
	          	 
	            }
				return null;
			}
			
			@SuppressWarnings("deprecation")
			protected void onPostExecute(Long result) 
			 {
				 // dialog.dismiss();
				
				  if(strmessage.equals("User is already exists") || strmessage.equals("User created successfully"))
			       {
			       Intent i=new Intent(LoginScreenActivity.this,StyleZTab.class);
			       startActivity(i);
			       ringProgressDialog.dismiss();
			       finish();
			       }
			 }
		}
		
		//for login twitter
		
		public void loginToTwitter()
		{
			   twitt = new Twitt(LoginScreenActivity.this, consumer_key, secret_key);
//			   twitt.shareToTwitter("http://samir-mangroliya.blogspot.in/");
			   twitterApp = twitt.getTwitterApp();
//				mTwitter.setListener(mTwLoginDialogListener);
			//..........................
			   ringProgressDialog = ProgressDialog.show(LoginScreenActivity.this, "","Fetching data", true);
               ringProgressDialog.setCancelable(true);
//			   twitt.logout();
				twitterApp.setListener(mTwLoginDialogListener);

				if (twitterApp.hasAccessToken()) {
					
	     		strtwtuserid=preferences.getString("twitter_userid","0");
	     		strtwtpic=preferences.getString("twitter_profilepic","0");
	     		strtwtname=preferences.getString("twitter_name","0");
	     		//d=preferences.getString("type","0");

	     		Log.e("Abcd==="," "+strtwtuserid+" "+strtwtuserid+" "+strtwtpic);  
	     			
	     		new LoginTwitter().execute();
	     			
			}
			else 
			{
				twitterApp.authorize();
			}
			//............................
		}

		Twitt twitt;
		TwitterApp twitterApp;
		//public static String consumer_key="14hweCh90xHPGegVvSmkw",secret_key="YGihOxFRhYWZhFai7ycz9KW70F2iKAKB9KZT0dCXaA";
		
		
		public static String consumer_key="FTP0Zn6AkSvbcFcoNgUuuwqhq",secret_key="TTL75JQNc33zOU2sK879YazBYBC9jDI5tyC5Se1Bhw332cVe0K";
		
		private TwDialogListener mTwLoginDialogListener = new TwDialogListener() {

			public void onError(String value) {
//				showToast("Login Failed");
				twitterApp.resetAccessToken();
			}

			public void onComplete(String value) 
			{
				
				        Log.e("before","Now in Login..");

				        User user=	twitterApp.getUser();
				
//					    User user = mTwitter.verifyCredentials();
					    Log.e("profile image.........",".........."+user.getProfileImageURL());
		                String userid=String.valueOf(user.getId());
		                Log.e("uuuuuuuserid",userid);
		                Log.e("username s",""+user.getName());
		                Log.e("user.getId()========>",""+user.getId());
		                SharedPreferences.Editor editor = preferences.edit();
		                
		                strtwtname=user.getName();
		                strtwtpic=user.getProfileImageURL();
		                
		                
		     			editor.putString("twitter_userid",""+user.getId());
		     			editor.putString("twitter_name",""+user.getName());
		     			editor.putString("twitter_profilepic",""+user.getProfileImageURL());
		     			editor.putString("twitter_name", ""+user.getName());
//		     			editor.putString("twitter_email",""+user.getId());
//		     			editor.putString("twitter_phone",""+user.getId());
		     			
		     			editor.putString("type", "twitter");
		     			editor.commit();

		     			 new LoginTwitter().execute();
			}
		};

		void showToast(final String msg) {
			this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(LoginScreenActivity.this, msg, Toast.LENGTH_SHORT).show();

				}
			});

		}
		
		class LoginTwitter extends AsyncTask<URL, Integer, Long>
		{
			protected void onPreExecute() 
			  { 
			  }
			protected Long doInBackground(URL... arg0) 
			{
				url=DataUrls.signup;
				
				Log.e("hello this is","mohanraj");
				Log.e("username is","<><><>"+strtwtname);
				Log.e("pic is","<><>"+strtwtpic);
				try 
				  {
					final int TIMEOUT_MILLISEC = 90000;
		        	HttpParams httpParams = new BasicHttpParams();
		        	HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
		        	HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
		        	HttpConnectionParams.setTcpNoDelay(httpParams, true);
					DefaultHttpClient httpClient = new DefaultHttpClient(); 
					HttpPost httpPost = new HttpPost(url);
					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
				   
					 nameValuePair.add(new BasicNameValuePair("Email",""));
				    nameValuePair.add(new BasicNameValuePair("UserName",strtwtname));
				    nameValuePair.add(new BasicNameValuePair("Profilepic",strtwtpic));
				    nameValuePair.add(new BasicNameValuePair("LoginType","Twitter"));
				    nameValuePair.add(new BasicNameValuePair("Address1",""));
				    nameValuePair.add(new BasicNameValuePair("PhoneNumber", ""));
				   
				 
				    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
				    
					HttpResponse httpResponse = httpClient.execute(httpPost); 
					HttpEntity httpEntity = httpResponse.getEntity(); 
					jsonresponse = EntityUtils.toString(httpEntity);

					Log.e("json response is",jsonresponse);
					}
				
				catch (UnsupportedEncodingException e) 
				{ 
					e.printStackTrace(); 
				} 
				catch (ClientProtocolException e) 
				{ 
					e.printStackTrace(); 
				} 
				catch (IOException e) 
				{ 
					e.printStackTrace(); 
				}
				
				if(!jsonresponse.equals("") && !jsonresponse.equals("null"))
	            {
	          	  try
	          	  {
	          		 JSONObject jo=new JSONObject(jsonresponse);
	          		 strstatus=jo.getString("status");
	          		 Log.e("status is",strstatus);
	          		 strmessage=jo.getString("message");
	          		 Log.e("message is",strmessage);
	          		 struserid=jo.getString("id");
	          		
	          		 //inserting userid into shared preferences
	          		 if(!struserid.equals("") && !struserid.equals("null"))
	          		 {
	          		  SharedPreferences.Editor editor = preferences.edit();
	     			  editor.putString("userid",struserid);
	     			  
//	     			  Log.e("chkbo login",strchkbox);
//	     			  editor.putString("checkbox", strchkbox);
	     			  editor.commit();
	     			  }
	     			
	          	  }catch(JSONException e)
	          	  {
	          		  e.printStackTrace();
	          	  }
	            }
				else
				{
	          	  //strmessage="Login Failed";
	          	  alertDialog(strmessage);
	          	 
	            }
				
				
				return null;
			}
			
			@SuppressWarnings("deprecation")
			protected void onPostExecute(Long result) 
			 {
				  //dialog.dismiss();
				  if(strmessage.equals("User created successfully")||strmessage.equals("User already exists"))
			       {
					  //Intent i=new Intent(LoginScreenActivity.this,HomeScreenActivity.class);
					  Intent i=new Intent(LoginScreenActivity.this,StyleZTab.class);
					  startActivity(i);
					  ringProgressDialog.dismiss();
					  finish();
			       }
			 }
		}
}
