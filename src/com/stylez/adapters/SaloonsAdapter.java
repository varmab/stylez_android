package com.stylez.adapters;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.stylez.activities1.R;
import com.stylez.helpers.DataUrls;
import com.stylez.helpers.ImageLoader1;
import com.stylez.helpers.RoundedTransformationBuilder;
import com.stylez.pojos.SaloonsItem;

public class SaloonsAdapter extends ArrayAdapter<SaloonsItem>
{
	Activity activity;
	DisplayImageOptions optionsprofileimge ;
	ImageLoader1 loader;
	Transformation transformation;
	List<SaloonsItem> zItems= new ArrayList<SaloonsItem>();
	Typeface tf;
	public SaloonsAdapter(Activity activity,List<SaloonsItem> zItems)
	{
		super(activity,0,zItems);
		this.activity=activity;
		this.zItems=zItems;
	
		 transformation = new RoundedTransformationBuilder()
		          .borderColor(Color.parseColor("#1d1a3d"))
		          .borderWidthDp(1)
		          .cornerRadiusDp(60)
		          .oval(false)
		          .build();
		 
		
	}
	
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		final SaloonsItem item= zItems.get(position);
		View gridView = convertView;
		LayoutInflater inflater=activity.getLayoutInflater();	
		
			gridView = inflater.inflate(R.layout.inflatedlistview, null);
			TextView txtviewname=(TextView)gridView.findViewById(R.id.txtviewname);
			ImageView imgviewstyles=(ImageView)gridView.findViewById(R.id.imgviewsaloonprofile);
			TextView txtviewdistance=(TextView)gridView.findViewById(R.id.txtviewdistance);		
			TextView txtviewaddressandcity=(TextView)gridView.findViewById(R.id.txtviewaddressandcity);		
			
			String distance=item.getDistance();
			
			DecimalFormat dtime = new DecimalFormat("#.#");
			
			//double miles=Double.valueOf(distance)*0.00062137;
			
			double miles=Double.valueOf(distance);
			
			double miles1=Double.valueOf(dtime.format(miles));
			
			tf = Typeface.createFromAsset(activity.getAssets(), "fonts/HelveticaNeue-ThinCond.otf");
			txtviewname.setTypeface(tf);
			txtviewaddressandcity.setTypeface(tf);			
			txtviewdistance.setTypeface(tf);	
			
			txtviewname.setText(item.getFirstname());
			
			txtviewaddressandcity.setText(item.getAddress()+", "+item.getCity());
			
			txtviewdistance.setText(""+miles1+" mi");

			Log.e("image path","<><>"+item.getProfilepic());
		
			if(item.getProfilepic().equals(""))
			{
				Log.e("image is","empty");
			}
			
			else
			{
				Picasso.with(activity)
		        .load(item.getProfilepic().replace(" ", "%20"))
		        .fit()
		        .transform(transformation)
		        
		        .into(imgviewstyles);
			}
		  
		return gridView;
	}
}
