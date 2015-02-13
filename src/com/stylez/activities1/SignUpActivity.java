package com.stylez.activities1;



import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stylez.activities1.CustomMultiPartEntity.ProgressListener;
import com.stylez.helpers.CheckInternetConnection;
import com.stylez.helpers.DataUrls;
import com.stylez.helpers.ImageHelper;
import com.stylez.helpers.MyProgressDialog;

public class SignUpActivity extends Activity implements OnClickListener
{
	ImageView imgviewback,imgviewsignupoff,imgviewsignupon,imgviewphoto;
	EditText edttextpassword,edttextname,edttextemail;
	MyProgressDialog dialog;
	String deviceId="";
	String url="",strpassword,stremail,strname,jsonresponse="",strstatus,strmessage,struserid;
	Uri muri;
	Bitmap dispImage;
	String selectedImagePath="";
	static final int TAKE_PICTURE=1,SELECT_PICTURE=0; 
	SharedPreferences preferences;
	private DataOutputStream outputStream = null;	
	private String dQ="\"";
	private String lineEnd = "\r\n";
	private boolean lastLine=false;
	private String twoHyphens = "--";
	private String boundary = "*****";
	InputStream inputStream;
	
	Typeface tf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		imgviewback=(ImageView)findViewById(R.id.imgviewback);
		imgviewsignupoff=(ImageView)findViewById(R.id.imgviewsignupoff);
		imgviewsignupon=(ImageView)findViewById(R.id.imgviewsignupon);
		imgviewphoto=(ImageView)findViewById(R.id.imgviewphoto);
		edttextemail=(EditText)findViewById(R.id.edttextemail);
		edttextpassword=(EditText)findViewById(R.id.edttextpassword);
		edttextname=(EditText)findViewById(R.id.edttextname);
		imgviewsignupoff.setOnClickListener(this);
		imgviewback.setOnClickListener(this);
		imgviewphoto.setOnClickListener(this);
		preferences=PreferenceManager.getDefaultSharedPreferences(this);
		
		tf = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeue-ThinCond.otf");
		edttextname.setTypeface(tf);
		edttextemail.setTypeface(tf);
		edttextpassword.setTypeface(tf);
	}

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if(v.equals(imgviewback))
		{
			Intent login=new Intent(SignUpActivity.this,LoginScreenActivity.class);
       	 	startActivity(login);
       	 	finish();
		}
		if(v.equals(imgviewsignupoff))
		{
			Log.e("file path is",selectedImagePath);
			validation();
			
			stremail=edttextemail.getText().toString();
			strpassword=edttextpassword.getText().toString();
			strname=edttextname.getText().toString();
		}
		if(v.equals(imgviewphoto))
		{
			dialogForProfilePic();
		}
	}
	
	class SignUp extends AsyncTask<URL, Integer, Long>
	{
		long startTime = System.currentTimeMillis();
		protected void onPreExecute() 
		  { 
			 dialog=MyProgressDialog.show(SignUpActivity.this, null,null);
			 dialog.getWindow().setGravity(Gravity.CENTER);
		  }
		
		protected Long doInBackground(URL... arg0) 
		{
			url=DataUrls.signup;
			
			try 
			  {
				
				
				  HttpClient httpClient = new DefaultHttpClient();
			       HttpContext localContext = new BasicHttpContext();
			       HttpPost httpPost = new HttpPost(url);
			    
			      ProgressListener listener= new ProgressListener() {
			    
			    @Override
			    public void transferred(long num) {
			     // TODO Auto-generated method stub
			     
			    }
			   };
			   CustomMultiPartEntity multipartContent = new CustomMultiPartEntity(listener);
			 
			     multipartContent.addPart("Profilepic", new FileBody(new File(selectedImagePath)));
			     multipartContent.addPart("Email", new StringBody(stremail));
			     multipartContent.addPart("Password", new StringBody(strpassword));
			     multipartContent.addPart("UserName", new StringBody(strname));
			     multipartContent.addPart("DeviceIdentifier", new StringBody(getDeviceId()));
			     multipartContent.addPart("LoginType", new StringBody("General"));
			     multipartContent.addPart("Address1", new StringBody(""));
			     
			     multipartContent.addPart("PhoneNumber", new StringBody(""));
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
     
          		 //inserting userid into shared preferences
          		 if(!struserid.equals("") && !struserid.equals("null"))
          		 {
          		  SharedPreferences.Editor editor = preferences.edit();
     			  editor.putString("userid",struserid);
     			  editor.commit();
     			  
     			
          		 }
          		 
          		 
          	  }catch(Exception e)
          	  {
          		  e.printStackTrace();
          	  }
            }
			else
			{
          	  strmessage="Registration Failed"; 
            }
			
			return null;
		}
		
		@SuppressWarnings("deprecation")
		protected void onPostExecute(Long result) 
		 {
			  dialog.dismiss();
			  alertDialog(strmessage);
			  long executionTime = System.currentTimeMillis() - startTime;
			  System.out.println("execution time was ~" + executionTime + " ms");
		 }
	}
	
	public String convertResponseToString(HttpResponse response) throws IllegalStateException, IOException{
   	 
        String res = "";
        StringBuffer buffer = new StringBuffer();
        inputStream = response.getEntity().getContent();
        int contentLength = (int) response.getEntity().getContentLength(); //getting content length…..
        if (contentLength < 0){
        }
        else{
               byte[] data = new byte[512];
               int len = 0;
               try
               {
                   while (-1 != (len = inputStream.read(data)) )
                   {
                       buffer.append(new String(data, 0, len)); //<span id="IL_AD11" class="IL_AD">converting to</span> string and appending  to stringbuffer…..
                   }
               }
               catch (IOException e)
               {
                   e.printStackTrace();
               }
               try
               {
                   inputStream.close(); // closing the stream…..
               }
               catch (IOException e)
               {
                   e.printStackTrace();
               }
               res = buffer.toString();     // converting stringbuffer to string…..

//               Toast.makeText(SignupActivity.this, "Result : " + res, Toast.LENGTH_LONG).show();
        }
        return res;
   }
	
	@SuppressWarnings("deprecation")
	private void alertDialog(String msg) 
	{
		// TODO Auto-generated method stub
		AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this).create();
        alertDialog.setTitle("StyleZ");
        alertDialog.setMessage(msg);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() 
        {
        public void onClick(DialogInterface dialog, int which) {
        	
        // Write your code here to execute after dialog closed
       dialog.dismiss();
       
       if(strstatus.equals("success"))
       {
    	   Intent i=new Intent(SignUpActivity.this,StyleZTab.class);
    	   startActivity(i);
    	   finish();
       }
       else
       {
    	   imgviewsignupoff.setVisibility(View.VISIBLE);
    	   imgviewsignupon.setVisibility(View.GONE);
       }
        }
     });
     alertDialog.show();
	}
	
	public String getDeviceId()
	   {
	    TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
	        if (Integer.parseInt(Build.VERSION.SDK) >= 10 )
	        {
	              deviceId=Secure.getString(getContentResolver(),Secure.ANDROID_ID); 
	          }
	          else
	          {
	              deviceId= telephonyManager.getDeviceId();
	          }
	        Log.w("Device id",deviceId);
	        return deviceId;
	   }
	
	
	public void validation()
	{
		if(edttextname.getText().toString().trim().length()==0)
		{
			toast("Please Enter Name");
		}
		else if(edttextemail.getText().toString().trim().length()==0)
		{
			toast("Please Enter Email");
		}
		else if(edttextpassword.getText().toString().trim().length()==0)
		{
			toast("Please Enter Password");
		}
		else if(selectedImagePath.trim().length()==0)
		{
			toast("Please Choose Profile Picture");
		}
		else if(!edttextname.getText().toString().trim().matches("[a-zA-Z0-9 ]*"))
		{
			toast("Special Characters are not allowed");
		}
		else
		{
			if(checkEmailCorrect(edttextemail.getText().toString().trim()))
			{
				 if(CheckInternetConnection.isOnline(this))
				 {
					 imgviewsignupoff.setVisibility(View.GONE);
						imgviewsignupon.setVisibility(View.VISIBLE);
						
					 new SignUp().execute();
					 
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
		
		
		 public void dialogForProfilePic()
	     {
	         final Dialog dialog = new Dialog(SignUpActivity.this,R.style.PauseDialog);
	          dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
	          dialog.setContentView(R.layout.selectpicturedialog);
	          dialog.setTitle("Social Sharing");
	          dialog.setCancelable(false);
	          dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	          LinearLayout take= (LinearLayout)dialog.findViewById(R.id.take);
//	          LinearLayout cancel= (LinearLayout)dialog.findViewById(R.id.cancel);
	          Button cancel= (Button)dialog.findViewById(R.id.cancel);
	          
	          LinearLayout choose =(LinearLayout)dialog.findViewById(R.id.choose);


	          
	          choose.setOnClickListener(new View.OnClickListener() 
	         {
	          public void onClick(View view) 
	          {
	           Intent intent = new Intent();
	           intent.setType("image/*");
	           intent.setAction(Intent.ACTION_GET_CONTENT);
	           startActivityForResult(Intent.createChooser(intent,"Select Picture"),0);
	           dialog.dismiss();
	           
	          }
	         });
	      // Called when take button is clicked.   
	          
	          
	          cancel.setOnClickListener(new View.OnClickListener() 
	         {
	          public void onClick(View view) 
	          {
	           
	           Log.e("Cancel","cancellll");
	           dialog.dismiss();
	           
	          }
	         });
	          
	         take.setOnClickListener(new View.OnClickListener() 
	         {
	          public void onClick(View view) 
	          {
	           if(isSDCARDMounted())
	           {
	            Intent photoPickerIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	            photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempFile());
	            photoPickerIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
	            photoPickerIntent.putExtra("return-data", true);
	            startActivityForResult(photoPickerIntent,1);
	            dialog.dismiss();
	            
	           }
	           else
	           {
	            Toast.makeText(SignUpActivity.this, "You need to insert SD card", Toast.LENGTH_LONG).show();
	            dialog.dismiss();
	            
	           }
	          }
	         });
	          
	          
	          dialog.show();
	     }
		 
		 
		 private boolean isSDCARDMounted()
	     {
	       String status = Environment.getExternalStorageState();
	       if (status.equals(Environment.MEDIA_MOUNTED))
	       return true;
	       else
	       return false;
	     
	       }
		 
		 
		 private Uri getTempFile() 
		   {
		        File root = new File(Environment.getExternalStorageDirectory(), "StyleZ");
		        if (!root.exists()) {
		            root.mkdirs();
		        }
		       final Calendar c = Calendar.getInstance();
		        int y = c.get(Calendar.YEAR);
		        int m = c.get(Calendar.MONTH);
		        int d = c.get(Calendar.DAY_OF_MONTH);
		      
		        
		        int h = c.get(Calendar.HOUR_OF_DAY);
		        int mi = c.get(Calendar.MINUTE);
		        
		        //String filename=""+y+"-"+"-"+(m+1)+"-"+d+" "+h+":"+mi;
		        String filename=""+System.currentTimeMillis();
		        File file = new File(root,filename+".jpeg" );
		        muri = Uri.fromFile(file);
		        selectedImagePath=muri.getPath();
		     Log.v("take picture path",selectedImagePath);
		          return muri;
		     }
		 
		 
		   @SuppressWarnings("deprecation")
			public void onActivityResult(int requestcode,int resultcode ,Intent data)
			   {
			    switch(requestcode)
			    { 
			    case TAKE_PICTURE: 
			  	  if(resultcode==RESULT_OK)
			      {
			       
			  		  int index = selectedImagePath.lastIndexOf("/");
			            String filename = selectedImagePath.substring(index+1, selectedImagePath.length());
//			            imagename.setText(filename);
			            File myFile = null;
			              
			              if (selectedImagePath != null)
			                  myFile = new File(selectedImagePath);
			              if (myFile != null) 
			              {

			             // 	profilepic.setBackgroundDrawable(new BitmapDrawable(decodeFile(myFile,selectedImagePath)));
			            	 // decodeFile(myFile,selectedImagePath);
			            	  
			            		 Bitmap resized = Bitmap.createScaledBitmap( decodeFile(myFile,selectedImagePath), 400, 400, true);
			                 	
			                	 dispImage=ImageHelper.getRoundedCornerBitmap(resized, 400);
			                	
			                	 imgviewphoto.setBackgroundDrawable(new BitmapDrawable(dispImage));
			              	
			             // 	il.DisplayImage(selectedImagePath, profilepic);
			              	
			              }
			            
			  	  break;
			      } 
			    case SELECT_PICTURE: if(resultcode==RESULT_OK)
			    {
			      Uri selectedImageUri = data.getData();
			            selectedImagePath = getPath(data);
			           
			            if(selectedImagePath!="")
			            {
			            	 Log.v("path","<><>"+ selectedImagePath);
			            int index = selectedImagePath.lastIndexOf("/");
			            String filename = selectedImagePath.substring(index+1, selectedImagePath.length());
//			            imagename.setText(filename);
			    			File myFile = null;
			              
			              if (selectedImagePath != null)
			                  myFile = new File(selectedImagePath);
			              if (myFile != null) {

			              //	profilepic.setBackgroundDrawable(new BitmapDrawable(decodeFile(myFile,selectedImagePath)));
			            	  
			            	//  decodeFile(myFile,selectedImagePath);
			            	  
			            		 Bitmap resized = Bitmap.createScaledBitmap( decodeFile(myFile,selectedImagePath),400, 400, true);
			                 	
			                	 dispImage=ImageHelper.getRoundedCornerBitmap(resized,400);
			                	
			                	 imgviewphoto.setBackgroundDrawable(new BitmapDrawable(dispImage));
			            	  
			              }
			              
			            }

			       break;
			    }
			   }
			   }
			  
		   
		   @SuppressWarnings("deprecation")
		public String getPath(Intent data) 
		   {
		  try{
		  String[] projection = { MediaStore.Images.Media.DATA,MediaStore.Images.ImageColumns.ORIENTATION };
		  Cursor cursor = managedQuery(data.getData(), projection, null, null, null);
		  int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		  int orientation_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);
		  cursor.moveToFirst();
		  String orientation =  cursor.getString(orientation_ColumnIndex);
		//  Log.e("Crassssssssssssss","<><>><><><123><><><>"+data.getData().getPath());
		  if(cursor.getString(column_index)==null)
		  {
		   Toast.makeText(SignUpActivity.this, "please select image from gallery",Toast.LENGTH_LONG).show();
		   return "";
		  }else
		  {
		   return cursor.getString(column_index);
		  }
		  
		  
		  }catch(Exception e)
		  {
		   Log.e("Crassssssssssssss","<><>><><><><><><>");
		   Toast.makeText(SignUpActivity.this, "please select image from gallery",Toast.LENGTH_LONG).show();
		   return "";
		  }
		   }
		   
			   private Bitmap decodeFile(File f,String path) {
				   	
				   	int orientation;
				   	
				       try {
				           // decode image size
				           BitmapFactory.Options o = new BitmapFactory.Options();
				           o.inJustDecodeBounds = true;
				           BitmapFactory.decodeStream(new FileInputStream(f), null, o);

				           // Find the correct scale value. It should be the power of 2.
				           final int REQUIRED_SIZE = 70;
				           int width_tmp = o.outWidth, height_tmp = o.outHeight;
				           int scale = 1;
				           while (true) {
				               if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
				                   break;
				               width_tmp /= 2;
				               height_tmp /= 2;
				               scale++;
				           }

				           // decode with inSampleSize
				           BitmapFactory.Options o2 = new BitmapFactory.Options();
				           o2.inSampleSize = scale;
				           Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
				           
				           Bitmap bitmap = bm;
				           
				           ExifInterface exif = new ExifInterface(path);
				           
				           orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
				           
				           Log.e("orientation",""+orientation);
				           
				           Matrix m=new Matrix();
				           
				           if((orientation==3)){

				           m.postRotate(180);
				           m.postScale((float)bm.getWidth(), (float)bm.getHeight());
				           
//				           if(m.preRotate(90)){
				           Log.e("in orientation",""+orientation);
				           
				           bitmap = Bitmap.createBitmap(bm, 0, 0,bm.getWidth(),bm.getHeight(), m, true);
				          // dispImage=ImageHelper.getRoundedCornerBitmap(bitmap, 150);
				           return  bitmap;
				           }
				           else if(orientation==6){
				           
				           	m.postRotate(90);
				           	
				           	Log.e("in orientation",""+orientation);
				           	
				           	bitmap = Bitmap.createBitmap(bm, 0, 0,bm.getWidth(),bm.getHeight(), m, true);
				          //  dispImage=ImageHelper.getRoundedCornerBitmap(bitmap, 100);
				            return  bitmap;
				           }
				           
				           else if(orientation==8){
				               
				           	m.postRotate(270);
				           	
				           	Log.e("in orientation",""+orientation);
				           	
				           	bitmap = Bitmap.createBitmap(bm, 0, 0,bm.getWidth(),bm.getHeight(), m, true);
				           	
				           // dispImage=ImageHelper.getRoundedCornerBitmap(bitmap, 100);
				           	
				           //	Bitmap resized = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
				               return  bitmap;
				           }
				           return bitmap;
				       }
				       catch (Exception e) {
				    	   
				       }
				       return null;
				   }
			   
	//to set the image paramater
	
			   public void setImageParameter1(String name,String path,boolean compress){
					
					
					Log.e("entered","entered");
					
					String req = "Content-Disposition: form-data;name="+dQ+name+dQ+";filename="+dQ
						+ path + dQ + lineEnd;
					
					try {
					outputStream.writeBytes(req);
					outputStream.writeBytes(lineEnd);
					if(compress){
						File myFile = null;
			          
			          if (path != null)
			              myFile = new File(path);
			          if (myFile != null) {

			        	  Uri uri = Uri.fromFile(myFile);
			        	  
			                	   decodeFile(myFile,path).compress(CompressFormat.JPEG,90, outputStream);
			                	   Log.e("this is..","jpen");

			              //SET BITMAP TO IMAGEVIEW
			          }
					}else{
						int bytesRead, bytesAvailable, bufferSize;
						byte[] buffer;
						int maxBufferSize = 1 * 1024 * 1024;
						FileInputStream fileInputStream = new FileInputStream(new File(
								path));
						bytesAvailable = fileInputStream.available();
						bufferSize = Math.min(bytesAvailable, maxBufferSize);
						buffer = new byte[bufferSize];			
						bytesRead = fileInputStream.read(buffer, 0, bufferSize);

						while (bytesRead > 0) {
							outputStream.write(buffer, 0, bufferSize);
							bytesAvailable = fileInputStream.available();
							bufferSize = Math.min(bytesAvailable, maxBufferSize);
							bytesRead = fileInputStream.read(buffer, 0, bufferSize);
						}
						
						fileInputStream.close();
						
					}
					setLastLine();

				} catch (Exception e) {
					
					Log.e(name+" Parameter failed",e.toString());
				}

				}
			   
			   public void setLastLine(boolean lastLine)
			   {
					this.lastLine=lastLine;
				}
				private void setLastLine(){
				
						try {
							outputStream.writeBytes(lineEnd);
							if(lastLine){
							outputStream.writeBytes(twoHyphens + boundary + twoHyphens
									+ lineEnd);
						}else{
							outputStream.writeBytes(twoHyphens + boundary + lineEnd);
						}
						} catch (IOException e) {
							
							Log.e("Webservice failed to write lastline",e.toString());
						}
				
				}
	
}
