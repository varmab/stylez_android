package com.stylez.activities1;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.stylez.activities1.CustomMultiPartEntity.ProgressListener;
import com.stylez.helpers.DataUrls;
import com.stylez.helpers.MyProgressDialog;
import com.stylez.helpers.RoundedTransformationBuilder;
import com.stylez.helpers.UrltoValue;
import com.stylez.pojos.SaloonsItem;
import com.stylez.thirdpartyTool.HorizontalListView;

@SuppressLint("SimpleDateFormat")
public class ListviewItemActivity extends Activity implements OnClickListener 
{
	ImageView imgviewback,imgviewupdotaboff,imgviewback1,imgviewsaloonpic,imgviewsendon,imgviewhomeoff,imgviewhearttaboff,imgviewsendoff,imgviewcancelon,imgviewcanceloff,imgviewmenoff,imgviewcross;
	TextView txtviewsaloonname,txtviewaddress,txtviewcity,txtviewdistance,txtviewstylecut,txtviewaddressandcity,txtviewstylecut1,txtviewstylecut2;
	RelativeLayout rel_listviewitem,rel_appointment,rel_emailsaloon,rel_callandbook,rel_text,rel_saloonpictures;
	String imageurl="",saloonname="",address="",city="",distance="",stylename="",phonenumber="",mail="",phoneuser="",mailuser="",stremail,strphone,strprimaryemail,stremailforsend="",strphonenumberforsend="";
	//ImageLoader1 loader;
	Transformation transformation;
	LinearLayout ll_address;
	
	String jsonresponse="",url="";
	
	int year,month,day,dayofweek,monthofyear;
	
	String date="",saloonpics="",userid="",styleId,strdate="",strtime="",strstatus="",strmessage="",strmessagenew="";
	
	EditText edttextmail,edttextphonenumber,edttextdate,edttexttime,edtmessage;
	
	SharedPreferences preferences;
	HorizontalListView horizontalListview,horizontalListview1;
	
	SaloonsItem saloonsitem;
	ArrayList<String> listsaloons=new ArrayList<String>();
	
	MyProgressDialog dialog;
	
	static final int DATE_PICKER_ID = 2222;
		
	DisplayImageOptions optionsprofileimge ;
	 
	Typeface tf;
	
	int position;
	
	com.stylez.helpers.ImageLoader imageLoader;
	 
	HorizontalImageadapter imageadapter;
	HorizontalImageadapter1 imageadapter1;
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.listviewitem);
		imgviewback=(ImageView)findViewById(R.id.imgviewback);
		imgviewback1=(ImageView)findViewById(R.id.imgviewback1);
		imgviewsaloonpic=(ImageView)findViewById(R.id.imgviewsaloonpic);
		txtviewsaloonname=(TextView)findViewById(R.id.txtviewsalooname);
		
		txtviewdistance=(TextView)findViewById(R.id.txtviewdistance);
		txtviewstylecut=(TextView)findViewById(R.id.txtviewstylecut);
		txtviewstylecut1=(TextView)findViewById(R.id.txtviewstylecut1);
		txtviewstylecut2=(TextView)findViewById(R.id.txtviewstylecut2);
		txtviewaddressandcity=(TextView)findViewById(R.id.txtviewaddressandcity);
					
		imgviewcanceloff=(ImageView)findViewById(R.id.imgviewcanceloff);
		imgviewcancelon=(ImageView)findViewById(R.id.imgviewcancelon);
		imgviewsendon=(ImageView)findViewById(R.id.imgviewsendon);
		imgviewsendoff=(ImageView)findViewById(R.id.imgviewsendoff);
		imgviewcross=(ImageView)findViewById(R.id.imgviewcross);
		
		edttextmail=(EditText)findViewById(R.id.edttextmail);
		edttextphonenumber=(EditText)findViewById(R.id.edttextphonenumber);
		edttextdate=(EditText)findViewById(R.id.edttextdate);
		edttexttime=(EditText)findViewById(R.id.edttexttime);
		edtmessage=(EditText)findViewById(R.id.edtmessage);
		
		rel_listviewitem=(RelativeLayout)findViewById(R.id.rel_listviewitem);
		rel_appointment=(RelativeLayout)findViewById(R.id.rel_appointment);
		rel_callandbook=(RelativeLayout)findViewById(R.id.rel_callandbook);
		rel_emailsaloon=(RelativeLayout)findViewById(R.id.rel_emailsaloon);
		rel_text=(RelativeLayout)findViewById(R.id.rel_text);
		rel_saloonpictures=(RelativeLayout)findViewById(R.id.rel_saloonpictures);
		
		preferences=PreferenceManager.getDefaultSharedPreferences(this);
		userid=preferences.getString("userid", "");
		phoneuser=preferences.getString("phone", "");
		Log.e("user id is","<><>"+userid);
		Log.e("phone user is","<><>"+phoneuser);
		stylename=getIntent().getExtras().getString("stylename");
		styleId=getIntent().getExtras().getString("styleId");
		if(styleId==null)
		{
			styleId="204";
		}
		imageurl=getIntent().getExtras().getString("imageurl");
		saloonname=getIntent().getExtras().getString("saloonname");
		address=getIntent().getExtras().getString("address");
		city=getIntent().getExtras().getString("city");
		distance=getIntent().getExtras().getString("distance");
		phonenumber=getIntent().getExtras().getString("phonenumber");
		mail=getIntent().getExtras().getString("mail");
		Log.e("mail in listview is","<><>"+mail);
		saloonpics=getIntent().getExtras().getString("listofsaloonpics");
		
		Log.e("styleID is",""+styleId);
		Log.e("saloonpics are",saloonpics);
		Log.e("mailuser is","<><><"+mailuser);
		
		transformation = new RoundedTransformationBuilder()
        .borderColor(Color.parseColor("#1d1a3d"))
        .borderWidthDp(1)
        .cornerRadiusDp(60)
        .oval(false)
        .build();
		
		try
		{
			
			JSONArray jobj=new JSONArray(saloonpics);
			Log.e("array length is","<><>"+jobj.length());
			for(int j=0;j<jobj.length();j++)
			{
				JSONObject object2=jobj.getJSONObject(j);
				listsaloons.add(""+object2.getString("saloonPics"));
			}
		}
		catch(Exception e)
		{
			
		}
		if(listsaloons.size()==0)
		{
			rel_text.setVisibility(View.GONE);
		}
		//for horizontal listview
		ll_address=(LinearLayout)findViewById(R.id.ll_address);
		ll_address.setOnClickListener(this);
		
		tf = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeue-ThinCond.otf");
		txtviewaddressandcity.setTypeface(tf);
		txtviewstylecut2.setTypeface(tf);
		txtviewstylecut1.setTypeface(tf);
		//txtviewdistance.setTypeface(tf);
		edttextmail.setTypeface(tf);
		edttextphonenumber.setTypeface(tf);
		edttextdate.setTypeface(tf);
		edttexttime.setTypeface(tf);
		edtmessage.setTypeface(tf);
		
		imgviewhomeoff=(ImageView)findViewById(R.id.imgviewhomeoff);
		imgviewhomeoff.setOnClickListener(this);
		  
		imgviewhearttaboff=(ImageView)findViewById(R.id.imgviewhearttaboff);
		imgviewhearttaboff.setOnClickListener(this);

		imgviewmenoff=(ImageView)findViewById(R.id.imgviewmenoff);
		imgviewmenoff.setOnClickListener(this);
		
		imgviewupdotaboff=(ImageView)findViewById(R.id.imgviewupdotaboff);
		imgviewupdotaboff.setOnClickListener(this);
		
		horizontalListview=(HorizontalListView)findViewById(R.id.horizontalListview);
		horizontalListview1=(HorizontalListView)findViewById(R.id.horizontalListview1);
        
	     imageadapter= new HorizontalImageadapter(getParent(), 123, listsaloons);
	  
	    horizontalListview.setAdapter(imageadapter);
	    
	    imageadapter1= new HorizontalImageadapter1(getParent(), 123, listsaloons);
		horizontalListview1.setAdapter(imageadapter1);
	   
	    horizontalListview.setOnItemClickListener(new OnItemClickListener() 
	    {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) 
			{
				// TODO Auto-generated method stub
				imageadapter1.notifyDataSetChanged();
				rel_saloonpictures.setVisibility(View.VISIBLE);
				position=arg2;
				imgviewback.setVisibility(View.GONE);
				Log.e("position is","<><>"+position);
				horizontalListview1.setSelection(3);
				
			}
		});
	      
		txtviewsaloonname.setText(saloonname);
		txtviewaddressandcity.setText(address+", "+city);
		
		txtviewdistance.setText(""+distance+" mi");
		
		txtviewstylecut.setText(stylename);
		
		edttextdate.setInputType(0);
		edttexttime.setInputType(0);
		edttextdate.setOnClickListener(this);
		edttexttime.setOnClickListener(this);
		
		rel_appointment.setOnClickListener(this);
		rel_emailsaloon.setOnClickListener(this);
		rel_callandbook.setOnClickListener(this);
		rel_listviewitem.setOnClickListener(this);
		rel_saloonpictures.setOnClickListener(this);
		
		imgviewback.setOnClickListener(this);
		imgviewback1.setOnClickListener(this);
		imgviewsendoff.setOnClickListener(this);
		imgviewcanceloff.setOnClickListener(this);
		imgviewcross.setOnClickListener(this);
		
		//for getting current date
		
		Calendar cal=Calendar.getInstance();
		year=cal.get(Calendar.YEAR);
		month=cal.get(Calendar.MONTH)+1;
		day=cal.get(Calendar.DATE);
		dayofweek=cal.get(Calendar.MONDAY);
		
		if(imageurl.equals(""))
		{
			Log.e("image url is","empty");
		}
		else
		{
			Picasso.with(getParent())
	        .load(imageurl.replace(" ", "%20"))
	        .fit()
	        .transform(transformation)
	        .into(imgviewsaloonpic);
		}
		
		horizontalListview1.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) 
			{
				// TODO Auto-generated method stub
				
			}
		});
		  
		imageLoader=new com.stylez.helpers.ImageLoader(getParent());
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if(v.equals(imgviewback))
		{
			TabActivityActivity.group.back();
		}
		else if(v.equals(rel_callandbook))
		{
			showDialogReport1();
		}
		else if(v.equals(rel_emailsaloon))
		{
			edttextmail.setText(mail);
			edttextphonenumber.setText(phonenumber);
			edtmessage.setText("");
			rel_listviewitem.setVisibility(View.GONE);
			rel_appointment.setVisibility(View.VISIBLE);
			//new Profile().execute();
			
		}
		else if(v.equals(imgviewback1))
		{
			edttextmail.setText("");
			edttextphonenumber.setText("");
			edttextdate.setText("");
			edttexttime.setText("");
			edtmessage.setText("");
			imgviewsendoff.setVisibility(View.VISIBLE);
			imgviewsendon.setVisibility(View.GONE);
			rel_appointment.setVisibility(View.GONE);
			rel_listviewitem.setVisibility(View.VISIBLE);
		}
		else if(v.equals(edttextdate))
		{
			showDialog(DATE_PICKER_ID);
		}
		else if(v.equals(edttexttime))
		{
			showDialogReport();
		}
		else if(v.equals(imgviewcanceloff))
		{
			edttextmail.setText("");
			edttextphonenumber.setText("");
			edttextdate.setText("");
			edttexttime.setText("");
			edtmessage.setText("");
			rel_appointment.setVisibility(View.GONE);
			rel_listviewitem.setVisibility(View.VISIBLE);
		}
		else if(v.equals(imgviewcancelon))
		{
			Log.e("clicking ","cancel on");
		}
		else if(v.equals(imgviewsendoff))
		{
			strdate=edttextdate.getText().toString();
			strtime=edttexttime.getText().toString();
			strmessagenew=edtmessage.getText().toString();
			stremailforsend=edttextmail.getText().toString();
			strphonenumberforsend=edttextphonenumber.getText().toString();
			validation();
		}
		else if(v.equals(imgviewsendon))
		{
			Log.e("clicking ","send on");
		}
		else if(v.equals(imgviewcross))
		{
			imgviewback.setVisibility(View.VISIBLE);
			rel_saloonpictures.setVisibility(View.GONE);
		}
		else if(v.equals(rel_saloonpictures))
		{
			Log.e("this is","<><>"+rel_saloonpictures);
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
		
		else if(v==imgviewhearttaboff)
		{
		   Log.e("clicked on","favourites");
		   View vi =TabActivityActivity.group.getLocalActivityManager()  
		              .startActivity("Items", new Intent(getApplicationContext(),GridviewActivity.class)
		              .putExtra("from", "1")
		              .putExtra("tab", "1")
		              .putExtra("url","http://app.hairconstruction.co/api/Account/Favorites/"+TabActivityActivity.userid+"?format=json")
		              .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
		              .getDecorView();  
		             TabActivityActivity.group.replaceView(vi);
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
	}
	
	public void validation()
	{
		if(edttextdate.getText().toString().trim().length()==0)
		{
			toast("Please Enter date");
		}
		else if(edttexttime.getText().toString().trim().length()==0)
		{
			toast("Please enter time");
		}
		else
		{
			imgviewsendoff.setVisibility(View.GONE);
			imgviewsendon.setVisibility(View.VISIBLE);
			new Appointment().execute();
		}
	}
	
	//For Toast Mesage
	public void toast(String msg)
	{
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
	
	protected Dialog onCreateDialog(int id) 
	{
		Log.e("in onclick","onClick");
		switch (id) 
		{
		  case DATE_PICKER_ID:
			// set date picker as current date
			  Log.e("date picker","is selected");
			  DatePickerDialog d= new DatePickerDialog(getParent(), datePickerListener, year, month,day);
			   d.setCancelable(false);
			   return d;
		}
		return null;
	}
	
	 private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() 
	 {
	        @Override
	        public void onDateSet(DatePicker view, int selectedYear,
	                int selectedMonth, int selectedDay) 
	        {
	        	int selectedmonth1=selectedMonth+1;
	        	String strdate=selectedDay+","+selectedmonth1+","+selectedYear;
	        	Log.e("strdate is",strdate);
	        	convertingToWebFormat(strdate);
	        	
	        	//for getting space after comma
	        	
	        	String[] date1=date.split(",");
	        	String day1 = null,month1 = null,year1 = null;
	        	for(int i=0;i<date1.length;i++)
	        	{
	        		day1=date1[0];
	        		month1=date1[1];
	        		year1=date1[2];
	        	}
	        	edttextdate.setText(day1+", "+month1+", "+year1);
	        	//edttextdate.setText(date);
	            
	           }
	        };
	        
	        public void showDialogReport()
	    	{
	    		 try
	    	       {
	    	              final Dialog dialog = new Dialog(getParent(),R.style.PauseDialog);
	    	              dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
	    	              dialog.setContentView(R.layout.callandbook);
	    	              
	    	              dialog.setCancelable(false);
	    	              dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	    	             
	    	              TextView    txtviewmorning=(TextView)dialog.findViewById(R.id.txtviewmorning);
	    	              TextView    txtviewafternoon=(TextView)dialog.findViewById(R.id.txtviewafternoon);
	    	              TextView    txtviewevening=(TextView)dialog.findViewById(R.id.txtviewevening);
	    	              TextView    txtviewcancel=(TextView)dialog.findViewById(R.id.txtviewcancel);
	    	              
	    	             txtviewmorning.setOnClickListener(new View.OnClickListener() 
	    	             {
	    	             public void onClick(View view) 
	    	             {
	    	            	 
	    	            	  edttexttime.setText("Morning");
	    	            	  dialog.dismiss();
	    	             }
	    	             });
	    	             
	    	             txtviewafternoon.setOnClickListener(new View.OnClickListener() 
	    	             {
	    	             public void onClick(View view) 
	    	             {
	    	            	 
	    	            	  edttexttime.setText("Afternoon");
	    	            	  dialog.dismiss();
	    	             }
	    	             });
	    	             
	    	             txtviewevening.setOnClickListener(new View.OnClickListener() 
	    	             {
	    	             public void onClick(View view) 
	    	             {
	    	            	 
	    	            	  edttexttime.setText("Evening");
	    	            	  dialog.dismiss();
	    	             }
	    	             });
	    	             
	    	             txtviewcancel.setOnClickListener(new View.OnClickListener() 
	    	             {
							@Override
							public void onClick(View v) 
							{
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
	    	             
	    	      		
	    	              dialog.show();
	    	             
	    	         }
	    	         catch(Exception e)
	    	         {
	    	       
	    	         }
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
	    	             
	    	              TextView   txtviewcancel=(TextView)dialog.findViewById(R.id.txtviewcancel);
	    	              TextView   txtviewok=(TextView)dialog.findViewById(R.id.txtviewok);
	    	              TextView   txtviewsaloonnumber=(TextView)dialog.findViewById(R.id.txtviewsaloonnumber);
	    	              
	    	              txtviewsaloonnumber.setText(phonenumber);
	    	              
	    	              Log.e("phonenumber is",phonenumber);
	    	              
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
	    	            	 i.setData(Uri.parse("tel:" + phonenumber));
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
	      
	    
			private void convertingToWebFormat(String start) 
			{
	        	   // TODO Auto-generated method stub
	        	   SimpleDateFormat curFormater = new SimpleDateFormat("dd,MM,yyyy"); 
	        	   Date dateObj1 = null;
	        	   	try 
	        	   	{
							dateObj1 = curFormater.parse(start.trim());
					} 
	        	   	catch (java.text.ParseException e) 
	        	   	{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        	    
	        	    SimpleDateFormat postFormater = new SimpleDateFormat("EEEE,MMM dd,yyyy");
	        	    date=postFormater.format(dateObj1);
	        	  }
	
	 class Doback_process extends AsyncTask<URL, Integer, Long>
	    {
	    MyProgressDialog dialog;
	    Bitmap bitmap;
//	    File f = getOutputMediaFile();
	    protected void onPreExecute() {
	      dialog=MyProgressDialog.show(getParent(), null,null);
	      dialog.getWindow().setGravity(Gravity.CENTER);
	      Log.e("its on pree","jhjkjk");
	    }
	    
	    @Override
	    protected Long doInBackground(URL... params) {
	    
	    try{
	     Log.e("Its donesss","strprofilepic== "+imageurl);
	     Bitmap b = BitmapFactory.decodeStream((InputStream)new URL(imageurl.replaceAll(" ","%20")).getContent());
	     if(b!=null)
	     {
	      Log.e("Its donesss"," while loading bitmap..."+b.getHeight()+"<>"+b.getWidth());
	      
	       bitmap= Bitmap.createScaledBitmap(b, 130, 130, true);
	      Log.e("Its donesss","<>"+bitmap.getHeight()+"<>"+bitmap.getWidth());
	      
//	      Display(bitmap);
	      
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
	     bitmap= getRoundedCornerBitmap(bitmap, 90, 130, 130);
	     imgviewsaloonpic.setImageBitmap(bitmap);
	     //....................
	    }
	     
	    }
	   
	 public class HorizontalImageadapter extends ArrayAdapter<String>
		{

			//List<String> listsubstyles = new ArrayList<String>();
			Context context;
			DisplayImageOptions optionsprofileimge1;
			private ArrayList<String> listsaloons=new ArrayList<String>();
			
			public HorizontalImageadapter(Context context, int textViewResourceId,ArrayList<String> listsaloons) 
			{
				super(context,listsaloons.size(),listsaloons);
				// TODO Auto-generated constructor stub
				this.listsaloons=listsaloons;
				this.context=context;
				DataUrls.initImageLoader(context);
				
				 optionsprofileimge1 = new DisplayImageOptions.Builder()
				   .showImageOnLoading(R.drawable.noimageofsaloonpic)
				   .showImageForEmptyUri(R.drawable.noimageofsaloonpic)
				   .showImageOnFail(R.drawable.noimageofsaloonpic)
				   .cacheInMemory(true)
				   .cacheOnDisc(true)
				   .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				   .displayer(new RoundedBitmapDisplayer(0))
				   .build();
			}
			
			public View getView(int position, View convertView, ViewGroup parent) 
			{
				String imageUrl=listsaloons.get(position);
				LayoutInflater inflater=ListviewItemActivity.this.getLayoutInflater();	
				
				View  gridView = inflater.inflate(R.layout.horizontal_saloonpics, null);
		         ImageView iv = (ImageView)gridView.findViewById(R.id.imgviewbuzz) ;
//		        int i= Integer.parseInt(listsubstyles.get(position));
		        imageLoader.DisplayImage(imageUrl.replaceAll(" ", "%20"), iv);
		         
		         //ImageLoader.getInstance().displayImage(imageUrl.replaceAll(" ", "%20"),iv,optionsprofileimge1,null);
				return gridView;
			}
		}
	 
	 public class HorizontalImageadapter1 extends ArrayAdapter<String>
		{
			Context context;
			DisplayImageOptions optionsprofileimge1;
			private ArrayList<String> listsaloons=new ArrayList<String>();
			
			public HorizontalImageadapter1(Context context, int textViewResourceId,ArrayList<String> listsaloons) 
			{
				super(context,listsaloons.size(),listsaloons);
				// TODO Auto-generated constructor stub
				this.listsaloons=listsaloons;
				this.context=context;
			}
			
			public View getView(int position, View convertView, ViewGroup parent) 
			{
				String imageUrl=listsaloons.get(position);
				LayoutInflater inflater=ListviewItemActivity.this.getLayoutInflater();	
				
				View  gridView = inflater.inflate(R.layout.horizontal_sloonpics1, null);
		         ImageView iv = (ImageView)gridView.findViewById(R.id.imgviewbuzz) ;       
		         
		         Picasso.with(context).load(imageUrl.replaceAll(" ", "%20")).into(iv);
		         
				return gridView;
			}
		}
	 
	 

	 class Appointment extends AsyncTask<URL, Integer, Long>
		{

			protected void onPreExecute() 
			  { 
				 dialog = MyProgressDialog.show(getParent(), null,null);
				 dialog.getWindow().setGravity(Gravity.CENTER);
			  }
			
			protected Long doInBackground(URL... arg0) 
			{	
				
//				url="http://app.hairconstruction.co/api/Appointments?format=json";
			String	urlmessage="http://app.hairconstruction.co/api/MakeAppointments?format=json";
				
				try 
				  {
					
					HttpClient httpClient = new DefaultHttpClient();
				    HttpContext localContext = new BasicHttpContext();
				    HttpPost httpPost = new HttpPost(urlmessage);
					
				    ProgressListener listener= new ProgressListener() {
					
					@Override
					public void transferred(long num) 
					{
						// TODO Auto-generated method stub
						
					}
				    };
					
					CustomMultiPartEntity multipartContent = new CustomMultiPartEntity(listener);
					Log.e("styleid id","<><>"+styleId);
					multipartContent.addPart("StyleId", new StringBody(styleId));
					multipartContent.addPart("UserId", new StringBody(userid));
					Log.e("user id id","<><>"+userid);
					multipartContent.addPart("day", new StringBody(strtime+" of "+strdate));
					Log.e("day is","<><>"+strtime+" of "+strdate);
					multipartContent.addPart("Email", new StringBody(stremailforsend));
					Log.e("email is","<><>"+stremailforsend);
					multipartContent.addPart("PhoneNumber", new StringBody(phoneuser));
					Log.e("phone n umber is","<><>"+strphonenumberforsend);
					multipartContent.addPart("Message",new StringBody(strmessagenew));
					Log.e("message is","<><>"+strmessagenew);
					
					Log.e("messsage is","meaassage is"+strmessagenew+"mail is "+edttextmail.getText().toString());
					
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
				
				return null;
			}
			
			protected void onPostExecute(Long result) 
			 {
				dialog.dismiss();
				//alertDialog(strmessage);
				if(strstatus.equals("success"))
				{
					showDialog1("",ListviewItemActivity.this);
				}
				else
				{
					showDialog2(strmessage,ListviewItemActivity.this);
				}
			 }
		}
	 
	 @SuppressWarnings("deprecation")
	private void alertDialog(String msg) 
		{
			// TODO Auto-generated method stub
		 AlertDialog alertDialog = new AlertDialog.Builder(getParent()).create();
	     alertDialog.setTitle("Stylez");
	     alertDialog.setMessage("Your stylist will contact you to confirm."+"\n"+"They have the recipe so you'll get the look you want!");
	     alertDialog.setButton("OK", new DialogInterface.OnClickListener() 
	     {
	       public void onClick(DialogInterface dialog, int which) 
	       {
	        // Write your code here to execute after dialog closed
	       dialog.dismiss();
	       
	       if(strstatus.equals("success"))
	       {
	    	   edttextmail.setText("");
				edttextphonenumber.setText("");
				edttextdate.setText("");
				edttexttime.setText("");
				imgviewsendoff.setVisibility(View.VISIBLE);
				//imgviewcanceloff.setVisibility(View.VISIBLE);
				//imgviewcancelon.setVisibility(View.GONE);
				imgviewsendon.setVisibility(View.GONE);
				rel_appointment.setVisibility(View.GONE);
				rel_listviewitem.setVisibility(View.VISIBLE);
	       }
	       else
	       {
	    	   imgviewsendon.setVisibility(View.GONE);
	    	   imgviewsendoff.setVisibility(View.VISIBLE);
	       }
	       }
	     });
	     alertDialog.show();
		}
	 
	 @Override  
	    public void onBackPressed() { 
//	    super.onBackPressed();
	  //  Log.e("Back pressed at","TabActivityActivity"+history.size());
	    //back();
	    TabActivityActivity.group.back();
	  //  Log.e("size","<>"+history.size());

	    return;  
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
	          		
	         		if(jo.has("email"))
	         		{
	          		 stremail=jo.getString("email");
	          		 Log.e("email is",stremail);
	         		}
	         		
	         		if(jo.has("phoneNumber"))
	          		{
	          		 strphone=jo.getString("phoneNumber");
	          		 Log.e("phone number is",strphone);
	          		
	          		}
	         		
	         		if(jo.has("primaryEmail"))
	          		 {
	          			 strprimaryemail=jo.getString("primaryEmail");
	          			 Log.e("primary email is",strprimaryemail);
	          		 }
	     			
	          	  }
	          	  catch(JSONException e)
	          	  {
	          		  e.printStackTrace();
	          	  }
	            return null;
			}
			
			protected void onPostExecute(Long result) 
			 {	
				  edttextphonenumber.setText(strphone);
				  edttextmail.setText(stremail);
				  edttextmail.setText(strprimaryemail);
				  dialog.dismiss();
			}
		}
	 
	 
	 public void showDialog1(String msg,Context ctx)
		{
		 Log.e("it is in","showDiaolg()");
			 try
		       {
				 Log.e("before show","dialog");
		           final Dialog dialog = new Dialog(getParent(),R.style.PauseDialog);
		           dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		           dialog.setContentView(R.layout.dialog1);
		           dialog.setCancelable(false);
		           dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		           Log.e("after show","dialog");
		           //TextView txtviewmessage=(TextView)dialog.findViewById(R.id.txtviewmessage);
		           //txtviewmessage.setText(msg);
		           TextView  txtviewok=(TextView)dialog.findViewById(R.id.txtviewok);
		           txtviewok.setOnClickListener(new View.OnClickListener() 
		           {
		              public void onClick(View view) 
		              {
		            	edttextmail.setText("");
		  				edttextphonenumber.setText("");
		  				edttextdate.setText("");
		  				edttexttime.setText("");
		  				imgviewsendoff.setVisibility(View.VISIBLE);
		  				//imgviewcanceloff.setVisibility(View.VISIBLE);
		  				//imgviewcancelon.setVisibility(View.GONE);
		  				imgviewsendon.setVisibility(View.GONE);
		  				rel_appointment.setVisibility(View.GONE);
		  				rel_listviewitem.setVisibility(View.VISIBLE);
		            	  
		            	dialog.dismiss();
		            	
		              }
		           });
		           dialog.show();
		        }
		        catch(Exception e)
		        {
		        	e.printStackTrace();
		        	
		        	Log.e("it is in","catch block");
		        }
		}
	 
	 public void showDialog2(String msg,Context ctx)
		{
		 Log.e("it is in","showDiaolg()");
			 try
		       {
				 Log.e("before show","dialog");
		           final Dialog dialog = new Dialog(getParent(),R.style.PauseDialog);
		           dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		           dialog.setContentView(R.layout.dialog2);
		           dialog.setCancelable(false);
		           dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		           Log.e("after show","dialog");
		           TextView txtviewmessage=(TextView)dialog.findViewById(R.id.txtviewmessage);
		           txtviewmessage.setText(msg);
		           TextView  txtviewok=(TextView)dialog.findViewById(R.id.txtviewok);
		           txtviewok.setOnClickListener(new View.OnClickListener() 
		           {
		              public void onClick(View view) 
		              {
		            	 imgviewsendon.setVisibility(View.GONE);
		   	    	   	 imgviewsendoff.setVisibility(View.VISIBLE);
		   	    	   	 dialog.dismiss();
		              }
		           });
		           dialog.show();
		        }
		        catch(Exception e)
		        {
		        	e.printStackTrace();
		        	
		        	Log.e("it is in","catch block");
		        }
		}
	 
}
