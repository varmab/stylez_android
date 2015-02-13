package com.stylez.activities1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stylez.thirdpartyTool.HorizontalListView;

public class MenActivity extends Activity implements OnClickListener
{
	String from="",userid="";
	ImageView imgviewback,imgviewright,imgviewleft,imgviewhomeoff,imgviewupdotaboff,imgviewmenoff;//,imgviewbuzz,imgviewshort,imgviewlong;
	HorizontalListView horizontalListview;
	TextView txtviewcelebrities;
	ImageView imgviewhearttaboff;

	TextView txtviewchoose,txtviewa,txtviewstyle,txtviewfind,txtviewfinda,txtviewsalon,txtviewget,txtviewthe,txtviewlook;
	
	Typeface tf,tf1,tf2;
	
	List<String> Images= new ArrayList<String>();
	List<String> Names= new ArrayList<String>();
	List<String> ids= new ArrayList<String>();
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.men);
		
		 SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
	        userid=preferences.getString("userid", "");
	        
	        imgviewhearttaboff=(ImageView)findViewById(R.id.imgviewhearttaboff);
	        imgviewhearttaboff.setOnClickListener(this);
	        
//...............................
	        
	        
	        txtviewchoose=(TextView)findViewById(R.id.txtviewchoose);
	        txtviewa=(TextView)findViewById(R.id.txtviewa);
	        txtviewstyle=(TextView)findViewById(R.id.txtviewstyle);
	        txtviewfind=(TextView)findViewById(R.id.txtviewfind);
	        txtviewfinda=(TextView)findViewById(R.id.txtviewfinda);
	        txtviewsalon=(TextView)findViewById(R.id.txtviewsalon);
	        txtviewget=(TextView)findViewById(R.id.txtviewget);
	        txtviewthe=(TextView)findViewById(R.id.txtviewthe);
	        txtviewlook=(TextView)findViewById(R.id.txtviewlook);
	        
	        
	        imgviewback=(ImageView)findViewById(R.id.imgviewback);
	        imgviewback.setOnClickListener(this);
	        imgviewright=(ImageView)findViewById(R.id.imgviewright);
	        //imgviewleft=(ImageView)findViewById(R.id.imgviewleft);
	        imgviewhomeoff=(ImageView)findViewById(R.id.imgviewhomeoff);
	        imgviewupdotaboff=(ImageView)findViewById(R.id.imgviewupdotaboff);
	        
//	        imgviewbuzz=(ImageView)findViewById(R.id.imgviewbuzz);
//	        imgviewlong=(ImageView)findViewById(R.id.imgviewlong);
	        imgviewmenoff=(ImageView)findViewById(R.id.imgviewmenoff);
	        imgviewmenoff.setOnClickListener(this);
	       
	        tf1 = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeueLTStd-MdCn.otf");
	        tf2 = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeue-ThinCond.otf");
	        
//	        menImages.add("a");
	        horizontalListview=(HorizontalListView)findViewById(R.id.horizontalListview);
	        txtviewchoose.setTypeface(tf1);
	        txtviewa.setTypeface(tf2);
	        txtviewstyle.setTypeface(tf2);
	        txtviewfind.setTypeface(tf1);
	        txtviewfinda.setTypeface(tf2);
	        txtviewsalon.setTypeface(tf2);
	        txtviewget.setTypeface(tf1);
	        txtviewthe.setTypeface(tf2);
	        txtviewlook.setTypeface(tf2);
	        
//	        imgviewbuzz.setOnClickListener(this);
	        
	        imgviewupdotaboff.setOnClickListener(this);
	        imgviewhomeoff.setOnClickListener(this);
	        imgviewright.setOnClickListener(this);
	        //imgviewleft.setOnClickListener(this);
	        
	        txtviewcelebrities=(TextView)findViewById(R.id.txtviewcelebrities);
	        tf = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeue-ThinCond.otf");
	        txtviewcelebrities.setTypeface(tf);
	        
//	        horizontalListview.setoni
	        
	        
	        from=getIntent().getExtras().getString("from");
	        Log.e("from value",">"+from);
	        
	        //...................
	        if(from.equals("men"))
	        {
	        
		        Images.add(""+R.drawable.buzz); Names.add("BUZZ"); ids.add("35");
		        Images.add(""+R.drawable.short1); Names.add("SHORT"); ids.add("39");
		        Images.add(""+R.drawable.long1); Names.add("LONG"); ids.add("37");
		        
		        
		        imgviewright.setBackgroundResource(R.drawable.man_right);
		        //imgviewleft.setBackgroundResource(R.drawable.man_left);
	        }
	        else
	        {
	        
		        Images.add(""+R.drawable.short_women); Names.add("short"); ids.add("32");
		        Images.add(""+R.drawable.medium); Names.add("medium");ids.add("31");
		        Images.add(""+R.drawable.long_women); Names.add("long");ids.add("30");

		        
		        Images.add(""+R.drawable.bobcut); Names.add("bobcut");ids.add("26");
		        Images.add(""+R.drawable.curly); Names.add("curly");ids.add("28");
		        Images.add(""+R.drawable.updos1); Names.add("updos"); ids.add("34");

		        
		        Images.add(""+R.drawable.extensions); Names.add("extensions"); ids.add("29");
		        Images.add(""+R.drawable.color); Names.add("color");ids.add("27");
		        Images.add(""+R.drawable.undercut); Names.add("undercut"); ids.add("33");
		        

		        imgviewright.setBackgroundResource(R.drawable.left_img);
		        //imgviewleft.setBackgroundResource(R.drawable.right_img);
	        
	        }
	        
	        HorizontalImageadapter imageadapter= new HorizontalImageadapter(MenActivity.this, 123, Names, Images);
	        
	        horizontalListview.setAdapter(imageadapter);
	        
	        horizontalListview.setOnItemClickListener(new OnItemClickListener() {

				@SuppressWarnings("deprecation")
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					
					
					Log.e("Postion clicked ","="+arg2);
					String tempId="";
					tempId=ids.get(arg2);
					
					View vi =TabActivityActivity.group.getLocalActivityManager()  
					           .startActivity("Items", new Intent(getApplicationContext(),GridviewActivity.class)
					           .putExtra("from", "from")
					           .putExtra("tab", "0")
					           .putExtra("url", "http://app.hairconstruction.co/api/Styles/"+tempId+"/"+userid+"?format=json")
					           .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
					           .getDecorView();  
				
				           TabActivityActivity.group.replaceView(vi);
					
					
				}
			});
	        
	        //....................
	        
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if(v==imgviewback)
		{
			Log.e("back button ", "is clicked");
			TabActivityActivity.group.back();
		}
		
		else if(v==imgviewright)
		{
			Log.e("men right", "is clicked");
			String url="";
			
			if(from.equals("men"))
			{
				
				url= "http://app.hairconstruction.co/api/Celebrities/Male/"+userid+"?format=json";
			}
			else
			{
				url= "http://app.hairconstruction.co/api/Celebrities/Female/"+userid+"?format=json";
			}
			
			
			View vi =TabActivityActivity.group.getLocalActivityManager()  
			           .startActivity("Items", new Intent(getApplicationContext(),GridviewActivity.class)
			           .putExtra("tab", "0")
			           .putExtra("url", url)
			           .putExtra("from", from)
			           .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
			           .getDecorView();  
		
		           TabActivityActivity.group.replaceView(vi);
		}
		else if(v==imgviewleft)
		{
			Log.e("men left ", "is clicked");
			
			// here is the codr for intigrating tabs,..
						String url="";
						if(from.equals("men"))
						{
							url= "http://app.hairconstruction.co/api/Celebrities/Male/"+userid+"?format=json";
						}
						else
						{
							url="http://app.hairconstruction.co/api/Celebrities/Female/"+userid+"?format=json";
						}
						View vi =TabActivityActivity.group.getLocalActivityManager()  
						           .startActivity("Items", new Intent(getApplicationContext(),GridviewActivity.class)
						           .putExtra("tab", "0") 
						           .putExtra("url", url)
						           .putExtra("from", from)
						           .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
						           .getDecorView();  
					
					           TabActivityActivity.group.replaceView(vi);
			
		}
		else if(v==imgviewhomeoff)
		{
			Log.e("imgviewhomeoff", "is clicked "+(TabActivityActivity.history.size()));
			
			 TabActivityActivity.history.clear();
			  Window wd=TabActivityActivity.group. getLocalActivityManager().startActivity("profile",new Intent(getParent(),HomeScreenActivity.class)
	          .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
	          View view = wd.getDecorView();
	          TabActivityActivity.group.   replaceView(view);
	          
		}
		else if(v==imgviewhearttaboff)
		{
//			DataUrls.from=1;
			Log.e("clicked on","imgviewhearttaboff");
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
	
	public class HorizontalImageadapter extends ArrayAdapter<String>
	{

		List<String> namesList = new ArrayList<String>();
		List<String> imagesList = new ArrayList<String>();
		Context context;
		//Typeface tf;
		public HorizontalImageadapter(Context context, int textViewResourceId,List<String> namesList,List<String> imagesList) {
			super(context, imagesList.size(),imagesList);
			// TODO Auto-generated constructor stub
			this.namesList=namesList;
			this.imagesList=imagesList;
			this.context=context;
		}

		public View getView(int position, View convertView, ViewGroup parent) 
		{
			
			LayoutInflater inflater=MenActivity.this.getLayoutInflater();	
			
			View  gridView = inflater.inflate(R.layout.horizontal_item, null);
			
	         ImageView iv = (ImageView)gridView.findViewById(R.id.imgviewbuzz) ;
	        int i= Integer.parseInt(imagesList.get(position));
	        iv.setImageResource(i);
	        //tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeue-ThinCond.otf");
	        TextView textView= (TextView)gridView.findViewById(R.id.txtviewbuzz);
	        textView.setTypeface(tf);
	        textView.setText(namesList.get(position).toUpperCase());
	        
			return gridView;
		}
	}
	
	@Override  
    public void onBackPressed() 
	{ 
       TabActivityActivity.group.back();
       return;  
   }

}
