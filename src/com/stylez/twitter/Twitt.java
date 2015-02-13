package com.stylez.twitter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.stylez.helpers.FileCache;
import com.stylez.helpers.ImageLoader;
import com.stylez.helpers.MyProgressDialog;
import com.stylez.twitter.TwitterApp.TwDialogListener;


public class Twitt{
	private TwitterApp twitterApp;
	

	private Activity activity;
	FileCache fileCache;
//	public static String username="",URL="";;
	ImageLoader imageloader;
	public static Boolean success;
	String message="";
	MyProgressDialog dialog;
//	String imageUrl;
	
	
	
	public TwitterApp getTwitterApp() {
		return twitterApp;
	}
	
	public Twitt(Activity activity, String consumer_key, String consumer_secret) {
		this.activity = activity;
		twitterApp = new TwitterApp(this.activity, consumer_key, consumer_secret);
		imageloader=new ImageLoader(activity);
		fileCache=new FileCache(activity);
	}
	
	

	public void logout()
	{
		twitterApp.resetAccessToken();
	}

	public void share(String imageurl)
	{
//		this.activity1= c;
		new PostTwittTask(imageurl).execute();
	}
	
	
//	public void shareToTwitter(String msg) {
//		this.twitt_msg = msg;
//		mTwitter.setListener(mTwLoginDialogListener);
//
//		if (mTwitter.hasAccessToken()) {
//			//showTwittDialog();
////            ImagePosting.tvName.setText(mTwitter.getUsername());
//			
//			Log.e("URL IS...........","........"+URL);
////			imageloader.DisplayImg(URL.replace(" ","%20"),ImagePosting.ivProfile);
//				username=mTwitter.getUsername();
//				
//				
//				
//				/*Intent i=	  new Intent(activity,ImagePosting.class);
//				
//				 i.putExtra("post_code",SocialActivity.social);
//			          
//				 i.putExtra("id",Twitt.URL);
//			     i .putExtra("name",Twitt.username);
//			     i.putExtra("image",SocialActivity.strLogo);
//			     activity. startActivity(i);*/
//				
//				
//				/* MessageShareActivity.imgTwitter.setBackgroundResource(R.drawable.twitter_red);
//				 
//				 MessageShareActivity.imgFav4me.setBackgroundResource(R.drawable.fav4);
//			     
//			     MessageShareActivity.logintype_twitter="3";
//			     
//			     MessageShareActivity.logintype_general="";
//			     
//			     MessageShareActivity.btnShare.setBackgroundResource(R.drawable.share_big);
//			     
//			     MessageShareActivity.strTwitter_logout="login";*/
//				
//				
//			       
//				
//		} else {
//			mTwitter.authorize();
//			
//		}
//	}

	private void showTwittDialog() {

		final Dialog dialog = new Dialog(activity);
//		dialog.setContentView(R.layout.twitt_dialog);
		dialog.setTitle("Twitter");

//		Button btnPost = (Button) dialog.findViewById(R.id.btnpost);
//		final EditText et = (EditText) dialog.findViewById(R.id.twittext);
//		et.setText(twitt_msg);
//		et.setSelection(et.getText().length());
/*		btnPost.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				twitt_msg = et.getText().toString().trim();

				if (twitt_msg.length() == 0) {
					showToast("Twitt is empty!!!");
					return;
				} else if (twitt_msg.length() > 140) {
					showToast("Twitt is more than 140 characters not allowed!!!");
					return;
				}
				dialog.dismiss();
				new PostTwittTask().execute(twitt_msg);

			}

		});*/

		dialog.show();

	}

	private TwDialogListener mTwLoginDialogListener = new TwDialogListener() {

		public void onError(String value) {
			showToast("Login Failed");
			twitterApp.resetAccessToken();
		}

		public void onComplete(String value) {
			
			Log.e("Got ittt","<><><><><><><><>>>>");
			
//			  share(imageUrl);
			  
			  Intent i=	  new Intent();
				
				 i.putExtra("A","A");
			          
//				 i.putExtra("id",Twitt.URL);
//			     i .putExtra("name",Twitt.username);
//			     i.putExtra("image",SocialActivity.strLogo);
			  activity.setResult(2,i);  
			     
//			     TwitterLogin twitterLogin= new TwitterLogin();
//			    twitterLogin .bacKToDuty();


	/*	     MessageShareActivity.imgTwitter.setBackgroundResource(R.drawable.twitter_red);
		     
		     MessageShareActivity.logintype_twitter="3";
		     
		     MessageShareActivity.btnShare.setBackgroundResource(R.drawable.share_big);
		     
		     MessageShareActivity.strTwitter_logout="login";*/


		}
	};

	void showToast(final String msg) {
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

			}
		});

	}

	class PostTwittTask extends AsyncTask<String, Void, String> {
		ProgressDialog pDialog;
		String imageURL;

		public PostTwittTask(String imageURL) {
			super();
			this.imageURL = imageURL;
		}

		@Override
		protected void onPreExecute() {

			dialog=MyProgressDialog.show(activity, null,null);
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... twitt) {
			try {

				Log.e("post image","<>"+imageURL);

				
		 File file=fileCache.getFile(imageURL);
		 Log.e("file catheee has==","<>"+file.exists());
		 
//		 Log.e("fileeee:",""+MessageDetailsActivity.file);
				
		 twitterApp.uploadPic(file, "StyleZ");
				return "success";

			} catch (Exception e) {
				if (e.getMessage().toString().contains("duplicate")) {
					return "Posting Failed because of Duplicate message...";
				}
				e.printStackTrace();
				return "Posting Failed to twitter!!!";
			}

		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();

			if (null != result && result.equals("success")) {
				
				Toast.makeText(activity, "Image posted to twitter successfully", Toast.LENGTH_SHORT).show();

//				alertMessage();
				
//				showDialog_Confirm("Image posted to twitter successfully");
				
/*				Toast.makeText(activity, "Image posted to twitter successfully", Toast.LENGTH_SHORT).show();
				if(!MessageShareActivity.tumbler_login && !MessageShareActivity.facebook_login)
				{
//					Intent i=new Intent(activity,SlideAnimationThenCallLayout.class);
//					activity.startActivity(i);
//					activity.finish();
//					
//					MessageShareActivity.logintype_facebook="";
//					MessageShareActivity.logintype_twitter="";
//					
//					DataUrls.strCaption="";
//					DataUrls.conversation_to="Friend";
					
					
					doInBack_general dg=new doInBack_general();
					dg.execute();
					
					
				}

			} else {
				showToast(result);
			}*/
				
			} else {
				showToast(result);
			}

			super.onPostExecute(result);
		}
	}
	
/*	public static Bitmap getBitmapFromURL(String src) {
	    try {
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}*/
	
	void alertMessage()
    {
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
//    TextView title = new TextView(activity);
    alertDialog.setTitle("Success");
    
    alertDialog.setMessage("Image posted to twitter successfully");
    // You Can Customise your Title here 

      alertDialog.setCancelable(false);

       alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog,int which) {
        	   
        	   
        	   Log.e("code val","in staff");

        	   dialog.cancel();
           }
       });

       alertDialog.show();
    } 
	
/*	
	 public void showDialog_Confirm(String msg)
	    {
	     try
	     {
	            final Dialog dialog = new Dialog(activity,R.style.PauseDialog);
	            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
	            dialog.setContentView(R.layout.logincheck_dialog);
	            dialog.setTitle("Aack Aack");
	            dialog.setCancelable(false);
	            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	            
	            final RelativeLayout ok= (RelativeLayout)dialog.findViewById(R.id.ok);

	            TextView text= (TextView)dialog.findViewById(R.id.text);
	            text.setText(msg);

	            
	            ok.setOnClickListener(new View.OnClickListener() 
	            {
	            public void onClick(View view) 
	            {
	              ok.setBackgroundColor(Color.RED);
	              dialog.dismiss();
//	              MessageShareActivity.this.finish();
	            }
	            });
	            
	            
	            dialog.show();
	       }
	       catch(Exception e)
	       {
	     
	       }
	   
	    }
	 */
	 
/*	 class doInBack_general extends AsyncTask<URL, Integer, Long>
		{
			
			 String res;
			protected void onPreExecute() 
			{ 
				dialog_general=MyProgressDialog.show(activity, null,null);	
				
			}
			@Override
			protected Long doInBackground(URL... arg0) {
				
				
				 try {
						
					 Log.e("str caption ... is..","...... "+DataUrls.strCaption);
					 int c=0;
					 if(DataUrls.strCaption.contains("\n"))
					 {
						 c++;
						
					 }
					String paramString = URLEncoder.encode(DataUrls.strCaption, "utf-8");
					
					if(DataUrls.conversation_to.trim().length()==0)
					{
						DataUrls.conversation_to="Friend";
					}
					String param_conversation = URLEncoder.encode(DataUrls.conversation_to, "utf-8");
					 Log.e("count value is..",""+c);
					 
					
					//String caption=strCaption.replace(" ", "%20");
				     
				 String respone_db=UrltoValue.getValuefromUrl(DataUrls.share_aack+"userid="+DisplayWebview.userid+"&aackid="+DisplayWebview.aack_id+"&caption="+paramString+"&conversation="+param_conversation+"&screentype="+MessageShareActivity.strScreenlook+"&sharedto="+"1"+"&devicedatetime="+getDateTime());
				// Log.e("response_update", DataUrls.share_aack+"userid="+userid+"&aackid="+aackid+"&caption="+paramString+"&conversation="+conversation_to.replaceAll(" ", "%20")+"&screentype="+strScreenlook+"&sharedto="+"0"+"&devicedatetime="+getDateTime());
				    
				 if(!respone_db.equals(" ") && !respone_db.equals("zero"))
				 {
					 JSONObject jo=new JSONObject(respone_db);
				 message=jo.getString("message");
					 
				 }
				 
				 } catch(Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					 
	             return null;
			}
			
			@SuppressLint("NewApi")
			protected void onPostExecute(Long result) { 
				
				if(message.equals("update success")){
					String msg="Aack shared successfully.";
					Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
//					showDialog_Confirm(msg);
					
					MessageShareActivity.logintype_facebook="";
					MessageShareActivity.logintype_twitter="";
					DataUrls.strCaption="";
					DataUrls.conversation_to="Friend";
					
					
					
					Intent i=new Intent(activity,SlideAnimationThenCallLayout.class);
					activity.startActivity(i);
					activity.finish();
				 }else{
					String msg="Aack sharing failed.";
	                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
				}
	             dialog_general.dismiss();
			  }
			}*/
		
	 private String getDateTime() { 
		   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   Date date = new Date(); 
		   return dateFormat.format(date).trim().replace(" ", "%20");
		  
		   }
	}
	

