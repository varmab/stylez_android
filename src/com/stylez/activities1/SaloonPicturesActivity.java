package com.stylez.activities1;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.stylez.helpers.DataUrls;
import com.stylez.thirdpartyTool.HorizontalListView;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class SaloonPicturesActivity extends Activity implements OnClickListener
{
	HorizontalListView horizontalListview;
	ArrayList<String> listsaloons;
	ImageView imgviewcross;
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.saloonpictures);
		listsaloons=getIntent().getStringArrayListExtra("listsaloons");
		imgviewcross=(ImageView)findViewById(R.id.imgviewcross);
		imgviewcross.setOnClickListener(this);
	
		horizontalListview=(HorizontalListView)findViewById(R.id.horizontalListview);
        
	    HorizontalImageadapter imageadapter= new HorizontalImageadapter(getParent(), 123, listsaloons);
	    horizontalListview.setAdapter(imageadapter);
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
			LayoutInflater inflater=SaloonPicturesActivity.this.getLayoutInflater();	
			
			View  gridView = inflater.inflate(R.layout.horizontal_sloonpics1, null);
	         ImageView iv = (ImageView)gridView.findViewById(R.id.imgviewbuzz) ;
	         
	         ImageLoader.getInstance().displayImage(imageUrl.replaceAll(" ", "%20"),iv,optionsprofileimge1, null);
			return gridView;
		}
	}

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		
		 if(v.equals(imgviewcross))
		{
			TabActivityActivity.group.back();
		}
	}
}
