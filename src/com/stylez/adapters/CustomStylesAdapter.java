package com.stylez.adapters;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.stylez.activities1.CustomMultiPartEntity;
import com.stylez.activities1.R;
import com.stylez.activities1.CustomMultiPartEntity.ProgressListener;
import com.stylez.helpers.MyProgressDialog;
import com.stylez.helpers.RoundedTransformationBuilder;
import com.stylez.pojos.StyleZItem;


public class CustomStylesAdapter extends ArrayAdapter<StyleZItem>
{
	Activity activity;
	DisplayImageOptions optionsprofileimge ;
	 
	List<StyleZItem> zItems= new ArrayList<StyleZItem>();
	int enable;
	Typeface tf;
	String from="";
	Transformation transformation;
	
	public CustomStylesAdapter(Activity activity,List<StyleZItem> zItems,String from)
	{
		super(activity,0,zItems);
		this.activity=activity;
		this.zItems=zItems;
		this.from=from;
		
		transformation = new RoundedTransformationBuilder()
        .borderColor(Color.parseColor("#1d1a3d"))
        .borderWidthDp(1)
        .cornerRadiusDp(60)
        .oval(false)
        .build();
	}
	
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		final StyleZItem item= zItems.get(position);
		View gridView = convertView;
		LayoutInflater inflater=activity.getLayoutInflater();	
		final	int n=position;
			gridView = inflater.inflate(R.layout.inflatedgridview, null);
			TextView txtviewname=(TextView)gridView.findViewById(R.id.txtviewname);
			ImageView imgviewstyles=(ImageView)gridView.findViewById(R.id.imgviewstyles);
			final ImageView imgviewheartoff=(ImageView)gridView.findViewById(R.id.imgviewheartoff);
			final ImageView imgviewhearton=(ImageView)gridView.findViewById(R.id.imgviewhearton);
			
			Log.e("count is",""+getCount());

			tf = Typeface.createFromAsset(activity.getAssets(), "fonts/HelveticaNeue-ThinCond.otf");
			txtviewname.setTypeface(tf);
			
			txtviewname.setText(item.getStyleName());
			if(item.getEnable().equals("false"))
			{
			  imgviewheartoff.setVisibility(View.VISIBLE);
			  imgviewhearton.setVisibility(View.GONE);
			}
			
			else
			{
				imgviewheartoff.setVisibility(View.GONE);
				imgviewhearton.setVisibility(View.VISIBLE);
			}
		  
		  imgviewheartoff.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				item.setEnable("true");
				imgviewhearton.setVisibility(View.VISIBLE);
				imgviewheartoff.setVisibility(View.GONE);
				new GridItem(n).execute();
			}
		});
		  
		  imgviewhearton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					item.setEnable("false");
					imgviewheartoff.setVisibility(View.VISIBLE);
					imgviewhearton.setVisibility(View.GONE);
					new GridItem(n).execute(); 
					
				}
			});
		  
		  
		  Picasso.with(activity).load(item.getImage().replace(" ", "%20")).into(imgviewstyles);
		  
		return gridView;
	}
	
	class GridItem extends AsyncTask<URL, Integer, Long>
	{
		MyProgressDialog dialog;
		String jsonresponse;
		int nt;
		public GridItem(int nt) {
			super();
			this.nt= nt;
		}

		protected void onPreExecute() 
		  { 
			  dialog = MyProgressDialog.show(activity, null,null);
			 dialog.getWindow().setGravity(Gravity.CENTER);
		  }
		
		protected Long doInBackground(URL... arg0) 
		{
			
			 SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
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
				String styleId=zItems.get(nt).getStyleId();
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
			
			if(from.equals("1"))
			{
				zItems.remove(nt);
				notifyDataSetChanged();
			}
			
		 }
		
		}
	
}
