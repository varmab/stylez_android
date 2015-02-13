package com.stylez.activities1;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
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

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedVignetteBitmapDisplayer;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.stylez.activities1.CustomMultiPartEntity.ProgressListener;
import com.stylez.helpers.DataUrls;
import com.stylez.helpers.ImageHelper;
import com.stylez.helpers.ImageLoader1;
import com.stylez.helpers.MyProgressDialog;
import com.stylez.helpers.RoundedTransformationBuilder;
import com.stylez.helpers.Utils;

public class EditProfileActivity extends Activity implements OnClickListener
{
	ImageView imgviewback,imgviewdone,imgviewprofilepic,imgviewupdotaboff,imgviewmenoff,imgviewbgprofilepic;
	EditText edttextname,edttextaddress,edttextemail,edttextphone;
	SharedPreferences preferences;
	String strname,straddress,stremail,strphone,strprofilepic,url="",jsonresponse="",strprimaryemail="",stredtname="",stredtaddress="",strlogintype="",stredtemail="",stredtphone="",stredtprofilepic="";
	ImageLoader1 loader;
	
	DisplayImageOptions optionsprofileimge,optionsprofileimge1 ;
	
	Uri muri;
	Bitmap dispImage;
	String selectedImagePath="",userid="",strstatus="",strmessage="";
	static final int TAKE_PICTURE=1,SELECT_PICTURE=0; 
	private boolean lastLine=false;
	private String twoHyphens = "--";
	private String boundary = "*****";
	private String dQ="\"";
	private String lineEnd = "\r\n";
	private DataOutputStream outputStream = null;	
	Transformation transformation;
	Typeface tf;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editprofile);
		edttextname=(EditText)findViewById(R.id.edttextname);
        edttextaddress=(EditText)findViewById(R.id.edttextaddress);
        edttextemail=(EditText)findViewById(R.id.edttextemail);
        edttextphone=(EditText)findViewById(R.id.edttextphone);
        imgviewprofilepic=(ImageView)findViewById(R.id.imgviewprofilepic);
        imgviewbgprofilepic=(ImageView)findViewById(R.id.imgviewbgprofilepic);
        imgviewback=(ImageView)findViewById(R.id.imgviewback);
        
    	tf = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeue-ThinCond.otf");
    	edttextname.setTypeface(tf);
    	edttextaddress.setTypeface(tf);
    	edttextemail.setTypeface(tf);
    	edttextphone.setTypeface(tf);
        
        imgviewupdotaboff=(ImageView)findViewById(R.id.imgviewupdotaboff);
        imgviewupdotaboff.setOnClickListener(this);
        
        imgviewmenoff=(ImageView)findViewById(R.id.imgviewmenoff);
        imgviewmenoff.setOnClickListener(this);
        
        imgviewdone=(ImageView)findViewById(R.id.imgviewdone);
        imgviewback.setOnClickListener(this);
        imgviewdone.setOnClickListener(this);
        imgviewprofilepic.setOnClickListener(this);
        loader=new ImageLoader1(this);
        preferences=PreferenceManager.getDefaultSharedPreferences(EditProfileActivity.this);
        strname=preferences.getString("profilename", "");
        Log.e("str name is",strname);
        userid=preferences.getString("userid", "");
        straddress=preferences.getString("address", "");
        strphone=preferences.getString("phone", "");
        stremail=preferences.getString("email", "");
        strlogintype=preferences.getString("LoginType", "");
        strprofilepic=preferences.getString("profilepic", "");
        strprimaryemail=preferences.getString("primaryemail","");
        
        Log.e("logintype is",strlogintype);
        Log.e("primary email is","<><>"+strprimaryemail);
        Log.e("email is","<><>"+stremail);
        
        
        transformation = new RoundedTransformationBuilder()
        .borderColor(Color.parseColor("#1d1a3d"))
        .borderWidthDp(1)
        .cornerRadiusDp(60)
        .oval(false)
        .build();
        
       if(strlogintype.equals("General"))
       {
    	   Log.e("logintype  is","<><><>if"+"general");
    	   edttextname.setText(strname);
    	   edttextaddress.setText(straddress);
    	   edttextphone.setText(strphone);
    	   edttextemail.setText(stremail);
       }
       
       else if(strlogintype.equals("Twitter"))
       {
    	   Log.e("logintype is","<><><>twitter");
    	   edttextname.setText(strname);
    	   edttextname.setEnabled(false);
    	   edttextphone.setText(strphone);
    	   edttextemail.setText(strprimaryemail);
    	   edttextaddress.setText(straddress);
       }
       
       else if(strlogintype.equals("Facebook"))
       {
    	   Log.e("logintype is","<><><>facebook");
    	   edttextname.setText(strname);
    	   edttextname.setEnabled(false);
    	   edttextphone.setText(strphone);
    	   edttextemail.setText(stremail);
    	   edttextaddress.setText(straddress);
       }
       
       DataUrls.initImageLoader(getParent());
		
		optionsprofileimge = new DisplayImageOptions.Builder()
		  .showImageOnLoading(R.drawable.noimage)
		   .showImageForEmptyUri(R.drawable.noimage)
		   .showImageOnFail(R.drawable.noimage)
		    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
		   .cacheInMemory(true)
		   .cacheOnDisc(true)
		   .displayer(new RoundedBitmapDisplayer(0))
		   .build();
		
		optionsprofileimge1 = new DisplayImageOptions.Builder()
		    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
		   .cacheInMemory(true)
		   .cacheOnDisc(true)
		   .displayer(new RoundedVignetteBitmapDisplayer(250, 3))
		   .build();
       
		if(strprofilepic.equals(""))
       {
    	   Log.e("strprofilepic is","empty");
       }
       else if(!strprofilepic.equals(""))
       {
 	   
    	   Picasso.with(getParent())
	        .load(strprofilepic.replace(" ", "%20"))
	        .fit()
	        .transform(transformation)
	        
	        .into(imgviewprofilepic);
		  
		  Picasso.with(getParent()).load(strprofilepic.replace(" ", "%20")).into(imgviewbgprofilepic);
       }
       
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if(v.equals(imgviewback))
		{
			Log.e("back button ","is clicked");
			TabActivityActivity.group.back();
		}
		else if(v.equals(imgviewdone))
		{
			stredtname=edttextname.getText().toString();
			stredtemail=edttextemail.getText().toString();
			stredtphone=edttextphone.getText().toString();
			stredtaddress=edttextaddress.getText().toString();
			Log.e("mail is",""+stredtemail);
		    Log.e("path is",selectedImagePath);
			new EditProfile().execute();
		}
		
		else if(v.equals(imgviewprofilepic))
		{
			dialogForProfilePic();
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
	
	class EditProfile extends AsyncTask<URL, Integer, Long>
	{
		MyProgressDialog dialog;
		protected void onPreExecute() 
		  { 
			 dialog=MyProgressDialog.show(getParent(), null,null);
			 dialog.getWindow().setGravity(Gravity.CENTER);
		  }
		
		protected Long doInBackground(URL... arg0) 
		{
			url=DataUrls.editprofile;
			
			try 
			  {
				
				   HttpClient httpClient = new DefaultHttpClient();
			       HttpContext localContext = new BasicHttpContext();
			       HttpPut httpPost = new HttpPut(url);
			    
			      ProgressListener listener= new ProgressListener() {
			    
			    @Override
			    public void transferred(long num) {
			     // TODO Auto-generated method stub
			     
			    }
			   };
			   CustomMultiPartEntity multipartContent = new CustomMultiPartEntity(listener);
			   
			   if(selectedImagePath.equals("0") || selectedImagePath.equals(""))
	  			{
				   Log.e("strprofile pic is","<><>"+strprofilepic);
				   File f = getOutputMediaFile();
				   try
				   {
					   OutputStream os = new FileOutputStream(f);
					   Utils.CopyStream((InputStream)new URL(strprofilepic.replaceAll(" ", "%20")).getContent(), os);
					   Log.e("profile pi is","<>"+f.getAbsolutePath());
				   }
				 
				   catch(Exception e)
				   {}
				   multipartContent.addPart("Profilepic", new FileBody(new File(f.getAbsolutePath())));
				   
	  			}
	  			else
	  			{
	  				 multipartContent.addPart("Profilepic", new FileBody(new File(selectedImagePath)));
	  			}
			   
			     multipartContent.addPart("Email", new StringBody(stredtemail));
			     multipartContent.addPart("Id", new StringBody(userid));
			     multipartContent.addPart("UserName", new StringBody(stredtname));
			     multipartContent.addPart("Address1", new StringBody(stredtaddress));
			     multipartContent.addPart("PhoneNumber", new StringBody(stredtphone));
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
          	  }
          		 
          	 catch(Exception e)
          	  {
          		  e.printStackTrace();
          	  }
            }
			else
			{
          	  //strmessage="Registration Failed";
            }
			return null;
		}
		
		@SuppressWarnings("deprecation")
		protected void onPostExecute(Long result) 
		 {
			  dialog.dismiss();
			 alertDialog(strmessage);
		 }
	}

	class DownloadProfilepic extends AsyncTask<URL, Integer, Long>
	{
		MyProgressDialog dialog; File f ;
		protected void onPreExecute() 
		  {}
		
		protected Long doInBackground(URL... arg0) 
		{
			  f = getOutputMediaFile();
			   try
			   {
				   Log.e("str profile pic is","<><>"+strprofilepic);
				   OutputStream os = new FileOutputStream(f);
				   Utils.CopyStream((InputStream)new URL(strprofilepic.replaceAll(" ", "%20")).getContent(), os);
			   //inputStream=(InputStream)new URL(imageUrl).getContent();
			   }
			   
			   catch(Exception e)
			   {}
			return null;
		}
		
		@SuppressWarnings("deprecation")
		protected void onPostExecute(Long result) 
		 {
			
			Log.e("filename is","<><>"+f.getAbsolutePath());
		 }
	}

	
	@SuppressWarnings("deprecation")
	private void alertDialog(String msg) 
	{
		// TODO Auto-generated method stub
		AlertDialog alertDialog = new AlertDialog.Builder(getParent()).create();
        alertDialog.setTitle("StyleZ");
        alertDialog.setMessage(msg);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() 
        {
        public void onClick(DialogInterface dialog, int which) {
        	
        // Write your code here to execute after dialog closed
       dialog.dismiss();
       
       if(strstatus.equals("success"))
       {
    	   
    	   View vi =TabActivityActivity.group.getLocalActivityManager()  
		           .startActivity("Items", new Intent(getApplicationContext(),ProfileActivity.class)
		          
		           .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
		           .getDecorView();  
    	   
    	   TabActivityActivity.group.replaceView(vi);
       }
       else
       {
    	   
       }
        }
     });
     alertDialog.show();
	}

	public void dialogForProfilePic()
    {
        final Dialog dialog = new Dialog(getParent(),R.style.PauseDialog);
         dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
         dialog.setContentView(R.layout.selectpicturedialog);
         dialog.setTitle("Social Sharing");
         dialog.setCancelable(false);
         dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
         LinearLayout take= (LinearLayout)dialog.findViewById(R.id.take);
//         LinearLayout cancel= (LinearLayout)dialog.findViewById(R.id.cancel);
         Button cancel= (Button)dialog.findViewById(R.id.cancel);
         
         LinearLayout choose =(LinearLayout)dialog.findViewById(R.id.choose);


         
         choose.setOnClickListener(new View.OnClickListener() 
        {
         public void onClick(View view) 
         {
          Intent intent = new Intent();
          intent.setType("image/*");
          intent.setAction(Intent.ACTION_GET_CONTENT);
          getParent().startActivityForResult(Intent.createChooser(intent,"Select Picture"),0);
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
          getParent(). startActivityForResult(photoPickerIntent,1);
           dialog.dismiss();
           
          }
          else
          {
           Toast.makeText(getParent(), "You need to insert SD card", Toast.LENGTH_LONG).show();
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
		   
		   Log.e("onactivityresult","editprofilr");
		    switch(requestcode)
		    { 
		    case TAKE_PICTURE: 
		  	  if(resultcode==RESULT_OK)
		      {
		       
		  		  int index = selectedImagePath.lastIndexOf("/");
		            String filename = selectedImagePath.substring(index+1, selectedImagePath.length());
//		            imagename.setText(filename);
		            File myFile = null;
		              
		              if (selectedImagePath != null)
		                  myFile = new File(selectedImagePath);
		              if (myFile != null) {

		             // 	profilepic.setBackgroundDrawable(new BitmapDrawable(decodeFile(myFile,selectedImagePath)));
		            	 // decodeFile(myFile,selectedImagePath);
		            	  
		            		 Bitmap resized = Bitmap.createScaledBitmap( decodeFile(myFile,selectedImagePath), 250, 250, true);
		                 	
		                	 dispImage=ImageHelper.getRoundedCornerBitmap(resized, 200);
		                	
		                	 imgviewprofilepic.setImageBitmap(dispImage);
		              	
		             // 	il.DisplayImage(selectedImagePath, profilepic);
		              	
		              }
		            
		  	  break;
		      } 
		    case SELECT_PICTURE: if(resultcode==RESULT_OK)
		    {
		      Uri selectedImageUri = data.getData();
		            selectedImagePath = getPath(data);
		            Log.v("path", selectedImagePath);
		            if(selectedImagePath!="")
		            {
		            int index = selectedImagePath.lastIndexOf("/");
		            String filename = selectedImagePath.substring(index+1, selectedImagePath.length());
//		            imagename.setText(filename);
		    			File myFile = null;
		              
		              if (selectedImagePath != null)
		                  myFile = new File(selectedImagePath);
		              if (myFile != null) {
		            	  
		            		 Bitmap resized = Bitmap.createScaledBitmap( decodeFile(myFile,selectedImagePath),250, 250, true);
		                 	
		                	 dispImage=ImageHelper.getRoundedCornerBitmap(resized,200);
		                	
		                	 imgviewprofilepic.setImageBitmap(dispImage);
		            	  
		              }
		            }

		       break;
		    }
		   
		   }
		   }
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
	   Toast.makeText(getParent(), "please select image from gallery",Toast.LENGTH_LONG).show();
	   return "";
	  }else
	  {
	   return cursor.getString(column_index);
	  }
	  
	  }catch(Exception e)
	  {
	   Log.e("Crassssssssssssss","<><>><><><><><><>");
	   Toast.makeText(getParent(), "please select image from gallery",Toast.LENGTH_LONG).show();
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
			           
//			           if(m.preRotate(90)){
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

		   class Doback_process extends AsyncTask<URL, Integer, Long>
		    {
		    MyProgressDialog dialog;
		    Bitmap bitmap;
//		    File f = getOutputMediaFile();
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
		      
//		      Display(bitmap);
		      
		     }else{
		      Log.e("Its failed"," while loading bitmap...");
		     }
		     
//		     
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
		     bitmap= getRoundedCornerBitmap(bitmap, 200, 250, 250);
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
		//   if(type == MEDIA_TYPE_VIDEO) 
		//   {
//		             mediaFile = new File(mediaStorageDir.getPath() + File.separator +"Swy_"+ timeStamp + ".mp4");
//		         }else if(type == MEDIA_TYPE_PICTURE) {
		            mediaFile = new File(mediaStorageDir.getPath() + File.separator +"StyleZ_"+ timeStamp + ".png");
		            
		            Log.v("file path..","..."+mediaFile.getPath());
		             
//		        } else {
//		             return null;
//		         }
		  
		         return mediaFile;
		     }
		   
}
