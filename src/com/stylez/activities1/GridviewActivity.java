package com.stylez.activities1;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.stylez.adapters.CustomStylesAdapter;
import com.stylez.helpers.MyProgressDialog;
import com.stylez.helpers.UrltoValue;
import com.stylez.pojos.StyleZItem;

public class GridviewActivity extends Activity implements OnClickListener 
{
	ImageView imgviewback,imgviewstylelogo,	imgviewhearttaboff,imgviewhearttabon,imgviewhomeoff,imgviewupdotaboff,imgviewupdotabon,imgviewmenoff;
	GridView gridview;
	MyProgressDialog dialog;
	String url="",jsonresponse="",userid="";
	SharedPreferences preferences;
	String strstatus="",strmessage="";
	List<StyleZItem> styleZItems= new ArrayList<StyleZItem>();
	CustomStylesAdapter stylesAdapter;
	public static TextView txtviewnoitems;
	String from="",tab="";
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview);
		
		url = getIntent().getExtras().getString("url");
		from= getIntent().getExtras().getString("from");
		tab= getIntent().getExtras().getString("tab");
		 
		 preferences=PreferenceManager.getDefaultSharedPreferences(this);
	     userid=preferences.getString("userid", "");
	    
	     txtviewnoitems=(TextView)findViewById(R.id.txtviewnoitems);
	        
	     	stylesAdapter= new CustomStylesAdapter(getParent(), styleZItems,from);
	        gridview= (GridView)findViewById(R.id.gridView);
	        imgviewhearttaboff=(ImageView)findViewById(R.id.imgviewhearttaboff);
	        imgviewhearttabon=(ImageView)findViewById(R.id.imgviewhearttabon);
	        gridview.setAdapter(stylesAdapter);
	        
	        imgviewmenoff=(ImageView)findViewById(R.id.imgviewmenoff);
	        imgviewmenoff.setOnClickListener(this);
	        	        
	        imgviewhomeoff=(ImageView)findViewById(R.id.imgviewhomeoff);
	        imgviewhomeoff.setOnClickListener(this);

	        Log.e("Tab is","<>"+tab);
	        
	        int tab1=Integer.valueOf(tab);
	        
	        new DataAsynch().execute();
		
	        imgviewback=(ImageView)findViewById(R.id.imgviewback);
	        imgviewback.setOnClickListener(this);
	        
	        gridview.setOnItemClickListener(new OnItemClickListener() {

				@SuppressWarnings({ "deprecation", "deprecation", "deprecation" })
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					
					StyleZItem item= styleZItems.get(arg2);
					 Log.e("Grid item","<><><>"+arg2);					 
					
					View vi =TabActivityActivity.group.getLocalActivityManager()  
					           .startActivity("Items", new Intent(getApplicationContext(),GridviewItemActivity.class)
					           .putExtra("styleId", ""+item.getStyleId())
					           .putExtra("tab", "0")
					           .putExtra("url", url)
					           .putExtra("enable", item.getEnable())
					           .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
					           .getDecorView();  
					
				        TabActivityActivity.group.replaceView(vi);
					
				}
			});
	        
	        
	        imgviewhearttaboff.setOnClickListener(this);
	        imgviewhearttabon.setOnClickListener(this);
	        
	        imgviewupdotaboff=(ImageView)findViewById(R.id.imgviewupdotaboff);
	        imgviewupdotabon=(ImageView)findViewById(R.id.imgviewupdotabon);
	        imgviewupdotaboff.setOnClickListener(this);
	        
	        Log.e("tab1 is","<><>"+tab1);
	        
	        switch (tab1) 
			{
	        
			  case 1:
				  		imgviewhearttaboff.setVisibility(View.GONE);
				  		imgviewhearttabon.setVisibility(View.VISIBLE);
				  		imgviewupdotaboff.setVisibility(View.VISIBLE);
				  		imgviewupdotabon.setVisibility(View.GONE);
				  		break;
			  case 2:
			  		
				  	imgviewhearttaboff.setVisibility(View.VISIBLE);
		  			imgviewhearttabon.setVisibility(View.GONE);
		  			imgviewupdotaboff.setVisibility(View.GONE);
			  		imgviewupdotabon.setVisibility(View.VISIBLE);
			  		break;
			  	  
			}
		
	}
	
	
	class DataAsynch extends AsyncTask<URL, Integer, Long>
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
				Log.e("Url iss==","<>"+url);
				jsonresponse=UrltoValue.getValuefromUrl(url);
			} 
			catch (Exception e) 
			{}
			
			
			
			return null;
		}
		
		protected void onPostExecute(Long result) 
		 {
			dialog.dismiss();
			
			
			Log.e("jsonresponse=> ","<>"+jsonresponse);
			int t=0;
			if(url.contains("http://app.hairconstruction.co/api/Account/Favorites/"))
			{
				Log.e("uhytg9iuyghoiuhgouygouyhoi","jhn");
				t=1;
			}
			
			try {
				JSONObject jsonObject= new JSONObject(jsonresponse);
				
				Log.e("josn object is","<><>"+jsonObject.toString());
				
				if(jsonObject.getString("status").toLowerCase().equals("success"))
				{
					Log.e("it is in","sucees");
					if(jsonObject.has("listCelebrities"))
					{
						Log.e("123","123");
						JSONArray array=jsonObject.getJSONArray("listCelebrities");
						for(int i=0;i<array.length();i++)
						{
							JSONObject object= array.getJSONObject(i);
							
							StyleZItem item= new StyleZItem(object.getString("id"), object.getString("gender"), object.getString("image"), object.getString("styleId"), object.getString("userId"), object.getString("styleName"),(t==1?("true"): object.getString("enable")));
							styleZItems.add(item);
						}
							
						
					}else if(jsonObject.has("liststyles")) 
					{
						Log.e("123","321");

						JSONArray array=jsonObject.getJSONArray("liststyles");
						for(int i=0;i<array.length();i++)
						{
							JSONObject object= array.getJSONObject(i);
							
							StyleZItem item= new StyleZItem(object.getString("subCatId"), "NOGENDER", object.getString("stylePic"), object.getString("styleId"), object.getString("userId"), object.getString("styleName"), (t==1?("true"): object.getString("enable")));
							styleZItems.add(item);
						}
						}
					
					Log.e("styels Sizes==","<>"+styleZItems.size());
				}
				
				
				} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stylesAdapter.notifyDataSetChanged();
		 }
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==imgviewback)
		{
			imgviewhearttaboff.setVisibility(View.VISIBLE);
			imgviewhearttabon.setVisibility(View.GONE);
//			finish();
			
			TabActivityActivity.group.back();
			
		} 
		else if(v==imgviewhearttaboff)
		{
//			DataUrls.from=1;
			Log.e("clicked on","favourites");
				
			View vi =TabActivityActivity.group.getLocalActivityManager()  
			           .startActivity("Items", new Intent(getApplicationContext(),GridviewActivity.class)
			           .putExtra("from", "1")
			           .putExtra("tab", "1")
			           .putExtra("url","http://app.hairconstruction.co/api/Account/Favorites/"+userid+"?format=json")
			           .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
			           .getDecorView();  
			
		           TabActivityActivity.group.replaceView(vi);
		           
		}else if(v==imgviewhomeoff)
		{
			Log.e("imgviewhomeoff", "is clicked "+(TabActivityActivity.history.size()-1));
			TabActivityActivity.history.clear();
			  Window wd=TabActivityActivity.group. getLocalActivityManager().startActivity("profile",new Intent(getParent(),HomeScreenActivity.class)
	          .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
	          View view = wd.getDecorView();
	          TabActivityActivity.group.   replaceView(view);
			
		}else if(v==imgviewupdotaboff)
		{
			 
			View vi =TabActivityActivity.group.getLocalActivityManager()  
			           .startActivity("Items", new Intent(getApplicationContext(),GetitActivity.class)
			           .putExtra("imageurl", "")
			           .putExtra("stylename", "")
			           .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
			           .getDecorView();  
		
		           TabActivityActivity.group.replaceView(vi);
		}else if(v==imgviewmenoff)
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
	
	@Override  
    public void onBackPressed() { 
    TabActivityActivity.group.back();
    return;  
   }

}
