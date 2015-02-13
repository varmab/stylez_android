package com.stylez.activities1;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.squareup.picasso.Picasso;
import com.stylez.adapters.SaloonsAdapter;
import com.stylez.helpers.DataUrls;
import com.stylez.helpers.ImageLoader;
import com.stylez.helpers.MyProgressDialog;
import com.stylez.helpers.UrltoValue;
import com.stylez.pojos.SaloonsItem;
import com.stylez.thirdpartyTool.HorizontalListView;
 
public class GetitActivity extends Activity implements OnClickListener
{
	String imageurl="",stylename="",url="", zipcode="",image="",styleId="";
	ImageView imgviewhomeoff,imgviewsearchbynear,imgviewzip,imgviewbgimg,imgviewmenoff,imgviewback,imgviewhearttaboff,imgviewback1,imgviewback2,imgviewsearch,imgviewzero,imgviewone,imgviewtwo,imgviewthree,imgviewfour,imgviewfive,imgviewsix,imgviewseven,imgvieweight,
	imgviewnine,imgviewbigrrow,imgviewupdotaboff,imgviewupdotabon;
	ListView listofsaloons;
	TextView txtviewstylename;
	SaloonsItem saloonsitem;
	List<SaloonsItem> listsaloons=new ArrayList<SaloonsItem>();
	SaloonsAdapter saloonsadapter;
	
	HorizontalListView horizontalListview;
	ImageLoader imageLoader;
	ArrayList<String> al_listsubstyles;
	String[] str_listsubstyles={"http://hairconstruction.co/media/1068383/pics/10683831.png", 
			"http://hairconstruction.co/media/1068383/pics/10683832.png",
			"http://hairconstruction.co/media/1068383/pics/10683833.png",
			"http://hairconstruction.co/media/1068383/pics/10683834.png",
			"http://hairconstruction.co/media/1068383/pics/10683835.png", 
			"http://hairconstruction.co/media/1068383/pics/10683836.png"};
	
	ImageView imgviewone_dial,imgviewtwo_dial,imgviewthree_dial,imgviewfour_dial,imgviewfive_dial,imgviewsix_dial,imgviewseven_dial,imgvieweight_dial,imgviewnine_dial,imgviewzero_dial,imgviewdial,imgviewbigarrw_dial;
	
	RelativeLayout rel_getit,rel_zip,rel_dialing,imgviewcall;
	EditText edttextnumbox,edttextnumbox1;
	HashMap<ImageView, String> map=new HashMap<ImageView,String>();
	HashMap<ImageView,String> map_dial=new HashMap<ImageView, String>();
	HashMap<String,String> map_country=new HashMap<String,String>();
	LocationManager locationmanager;
	LatLng myCurrentLocation;
	double latitude;
	double longitude;
	Typeface tf;
	boolean enabled;
	Spinner spn_country;
	ArrayList<String> al_countryname,al_countrycode;
	String str_countryname="";
	 ArrayAdapter<String> dataAdapter;
	
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.getit);
		imgviewbgimg=(ImageView)findViewById(R.id.imgviewbgimg);
		imgviewsearchbynear=(ImageView)findViewById(R.id.imgviewsearchbynear);
		imgviewzip=(ImageView)findViewById(R.id.imgviewsearchbyzip);
		imgviewback=(ImageView)findViewById(R.id.imgviewback);
		txtviewstylename=(TextView)findViewById(R.id.txtviewstylename);
		listofsaloons=(ListView)findViewById(R.id.listviewofsaloons);
		
		spn_country=(Spinner)findViewById(R.id.spn_country);
//		spn_country.setSelection(25);
		
		saloonsadapter= new SaloonsAdapter(GetitActivity.this, listsaloons);
        listofsaloons.setAdapter(saloonsadapter);
        
        imgviewmenoff=(ImageView)findViewById(R.id.imgviewmenoff);
        imgviewmenoff.setOnClickListener(this);
        
        imgviewhomeoff=(ImageView)findViewById(R.id.imgviewhomeoff);
        imgviewhomeoff.setOnClickListener(this);
        
        al_countryname=new ArrayList<String>();
        al_countrycode=new ArrayList<String>();
        
        //for zip
        
        rel_zip=(RelativeLayout)findViewById(R.id.rel_zip);
        rel_getit=(RelativeLayout)findViewById(R.id.rel_getit);
        rel_dialing=(RelativeLayout)findViewById(R.id.rel_dialing);
        imgviewcall=(RelativeLayout)findViewById(R.id.imgviewcall);
        
        imgviewback1=(ImageView)findViewById(R.id.imgviewback1);
        imgviewback2=(ImageView)findViewById(R.id.imgviewback2);
		imgviewsearch=(ImageView)findViewById(R.id.imgviewsearch);
		imgviewbigrrow=(ImageView)findViewById(R.id.imgviewarrowbig);
		imgviewzero=(ImageView)findViewById(R.id.imgviewzero);
		imgviewone=(ImageView)findViewById(R.id.imgviewone);
		imgviewtwo=(ImageView)findViewById(R.id.imgviewtwo);
		imgviewthree=(ImageView)findViewById(R.id.imgviewthree);
		imgviewfour=(ImageView)findViewById(R.id.imgviewfour);
		imgviewfive=(ImageView)findViewById(R.id.imgviewfive);
		imgviewsix=(ImageView)findViewById(R.id.imgviewsix);
		imgviewseven=(ImageView)findViewById(R.id.imgviewseven);
		imgvieweight=(ImageView)findViewById(R.id.imgvieweight);
		imgviewnine=(ImageView)findViewById(R.id.imgviewnine);
		
		//for dialing
		
		imgviewone_dial=(ImageView)findViewById(R.id.imgviewone_dial);
		imgviewtwo_dial=(ImageView)findViewById(R.id.imgviewtwo_dial);
		imgviewthree_dial=(ImageView)findViewById(R.id.imgviewthree_dial);
		imgviewfour_dial=(ImageView)findViewById(R.id.imgviewfour_dial);
		imgviewfive_dial=(ImageView)findViewById(R.id.imgviewfive_dial);
		imgviewsix_dial=(ImageView)findViewById(R.id.imgviewsix_dial);
		imgviewseven_dial=(ImageView)findViewById(R.id.imgviewseven_dial);
		imgvieweight_dial=(ImageView)findViewById(R.id.imgvieweight_dial);
		imgviewnine_dial=(ImageView)findViewById(R.id.imgviewnine_dial);
		imgviewzero_dial=(ImageView)findViewById(R.id.imgviewzero_dial);
		imgviewbigarrw_dial=(ImageView)findViewById(R.id.imgviewarrowbig_dial);
		imgviewdial=(ImageView)findViewById(R.id.imgviewdial);
		
		imgviewupdotaboff=(ImageView)findViewById(R.id.imgviewupdotaboff);
		imgviewupdotaboff.setOnClickListener(this);

		imgviewupdotabon=(ImageView)findViewById(R.id.imgviewupdotabon);
		imgviewupdotabon.setOnClickListener(this);
		
		imgviewupdotaboff.setVisibility(View.GONE);
		imgviewupdotabon.setVisibility(View.VISIBLE);
		
		imgviewhearttaboff=(ImageView)findViewById(R.id.imgviewhearttaboff);
		imgviewhearttaboff.setOnClickListener(this);
		
		edttextnumbox=(EditText)findViewById(R.id.edttextnumbox);
		edttextnumbox.setCursorVisible(false);
		imgviewsearch.setOnClickListener(this);
		imgviewbigrrow.setOnClickListener(this);
		imgviewzero.setOnClickListener(this);
		imgviewone.setOnClickListener(this);
		imgviewtwo.setOnClickListener(this);
		imgviewthree.setOnClickListener(this);
		imgviewfour.setOnClickListener(this);
		imgviewfive.setOnClickListener(this);
		imgviewsix.setOnClickListener(this);
		imgviewseven.setOnClickListener(this);
		imgvieweight.setOnClickListener(this);
		imgviewnine.setOnClickListener(this);
                
		//for dialing
		edttextnumbox1=(EditText)findViewById(R.id.edttextnumbox1);
		imgviewone_dial.setOnClickListener(this);
		imgviewtwo_dial.setOnClickListener(this);
		imgviewthree_dial.setOnClickListener(this);
		imgviewfour_dial.setOnClickListener(this);
		imgviewfive_dial.setOnClickListener(this);
		imgviewsix_dial.setOnClickListener(this);
		imgviewseven_dial.setOnClickListener(this);
		imgvieweight_dial.setOnClickListener(this);
		imgviewnine_dial.setOnClickListener(this);
		imgviewzero_dial.setOnClickListener(this);
		imgviewbigarrw_dial.setOnClickListener(this);
		imgviewdial.setOnClickListener(this);
		
		//for dialing
		map_dial.put(imgviewzero_dial, "0");
		map_dial.put(imgviewone_dial, "1");
		map_dial.put(imgviewtwo_dial, "2");
		map_dial.put(imgviewthree_dial, "3");
		map_dial.put(imgviewfour_dial, "4");
		map_dial.put(imgviewfive_dial, "5");
		map_dial.put(imgviewsix_dial, "6");
		map_dial.put(imgviewseven_dial, "7");
		map_dial.put(imgvieweight_dial, "8");
		map_dial.put(imgviewnine_dial, "9");
		
		//for zip
		map.put(imgviewzero, "0");
		map.put(imgviewone, "1");
		map.put(imgviewtwo, "2");
		map.put(imgviewthree, "3");
		map.put(imgviewfour, "4");
		map.put(imgviewfive, "5");
		map.put(imgviewsix, "6");
		map.put(imgviewseven, "7");
		map.put(imgvieweight, "8");
		map.put(imgviewnine, "9");
		
		al_listsubstyles=new ArrayList<String>();
		
		imageurl= getIntent().getExtras().getString("imageurl");
        stylename= getIntent().getExtras().getString("stylename");
        styleId=getIntent().getExtras().getString("styleId");
       
        Log.e("style name is",stylename);
        
        if(stylename.equals(""))
        {
        	stylename="CHRYSALIS-02";
        	Log.e("str length is",""+str_listsubstyles.length);
        	for(int i=0;i<str_listsubstyles.length;i++)
        	{
        		al_listsubstyles.add(str_listsubstyles[i]);
        		Log.e("size is",""+al_listsubstyles.size());
        	}
        }
        else
        {
        	 al_listsubstyles=getIntent().getExtras().getStringArrayList("al_listsubstyles");
             Log.e("array list is",""+al_listsubstyles);
        }
        
        horizontalListview=(HorizontalListView)findViewById(R.id.horizontalListview);
        
        HorizontalImageadapter imageadapter= new HorizontalImageadapter(this, 123,  al_listsubstyles);
      
        horizontalListview.setAdapter(imageadapter);

         dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,al_countryname);
         
        dataAdapter.setDropDownViewResource
        (android.R.layout.simple_spinner_dropdown_item);
         
        spn_country.setAdapter(dataAdapter);
        
      spn_country.setOnItemSelectedListener(new OnItemSelectedListener() 
      {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) 
		{
			// TODO Auto-generated method stub
			 ((TextView) arg0.getChildAt(0)).setTextSize(20);
			str_countryname=arg0.getItemAtPosition(arg2).toString();
			Log.e("str_countryname in spiiner on","<><>"+str_countryname);
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) 
		{
			// TODO Auto-generated method stub
			
		}
	}); 
        
        
    	tf = Typeface.createFromAsset(this.getAssets(), "fonts/HelveticaNeue-ThinCond.otf");
    	txtviewstylename.setTypeface(tf);
    	
        listofsaloons.setOnItemClickListener(new OnItemClickListener() 
        {

			@SuppressWarnings("deprecation")
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) 
			{
				// TODO Auto-generated method stub
				SaloonsItem item=listsaloons.get(arg2);
				
				Log.e("image is","<><>"+image);
				Log.e("phonenumber is","<><>"+item.getPhone());
				
				if(item.getProfilepic().equals(""))
				{
					image="";
				}
				else
				{
					image=item.getProfilepic();
				}

				View vi =TabActivityActivity.group.getLocalActivityManager()  
				           .startActivity("Items", new Intent(getApplicationContext(),ListviewItemActivity.class)
				           .putExtra("styleId", styleId)
				           .putExtra("saloonname", item.getFirstname())
							.putExtra("address", item.getAddress())
							.putExtra("city", item.getCity())
							.putExtra("distance", item.getDistance())
							.putExtra("phonenumber", item.getPhone())
							.putExtra("mail", item.getEmail())
							.putExtra("listofsaloonpics", item.getListsaloons().toString())
							.putExtra("imageurl", image)
							.putExtra("stylename", stylename)
				           .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))  
				           .getDecorView();  
				
			           TabActivityActivity.group.replaceView(vi);
			}
        	
		});
        
        Log.e("imageurl is","<><>"+imageurl);
		Log.e("style name is",stylename);
		imageLoader=new ImageLoader(this);
		
		txtviewstylename.setText(stylename);
		imgviewzip.setOnClickListener(this);
		imgviewback.setOnClickListener(this);
		imgviewback1.setOnClickListener(this);
		imgviewsearchbynear.setOnClickListener(this);
		imgviewcall.setOnClickListener(this);
		imgviewback2.setOnClickListener(this);
		
		listsaloons.clear();
		
		// Acquire a reference to the system Location Manager
		
		locationmanager = (LocationManager) getSystemService(LOCATION_SERVICE);
		 enabled= locationmanager
		  .isProviderEnabled(LocationManager.GPS_PROVIDER);
		
		if(enabled==true)
		{
			getCurrentLocationValues();
			new Saloons().execute();
		}
		else
		{
			showDialog();
		}
		
		
		
	}
	
	public void getCurrentLocationValues() 
	{
		try
		{
		// TODO Auto-generated method stub
		// Define a listener that responds to location updates
		   LocationListener locationListener = new LocationListener() 
		   {
		       public void onLocationChanged(Location location) 
		       {
		         // Called when a new location is found by the network location provider.
//		         makeUseOfNewLocation(location);
		       }

		       public void onStatusChanged(String provider, int status, Bundle extras) {}

		       public void onProviderEnabled(String provider) {}

		       public void onProviderDisabled(String provider) {}
		     };
		     
		     // Register the listener with the Location Manager to receive location updates
		     locationmanager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		     
		     
		     Location location = locationmanager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		     
		     //Log.e("new location",""+location.getLatitude()+""+location.getTime());
		          
		   myCurrentLocation=  new LatLng(location.getLatitude(),location.getLongitude());
		   
		   Log.e("current location is ",""+myCurrentLocation);
		   
		   Log.e("latitude for current location is",""+myCurrentLocation.latitude);
		   
		   Log.e("longitude for current location is",""+myCurrentLocation.longitude);
		   
		   latitude=myCurrentLocation.latitude;
		
		   longitude=myCurrentLocation.longitude;
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void showDialog()
	{
		 try
	       {
	           final Dialog dialog = new Dialog(getParent(),R.style.PauseDialog);
	           dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
	           dialog.setContentView(R.layout.dialog);
	           dialog.setCancelable(false);
	           dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	             
	           TextView  txtviewok=(TextView)dialog.findViewById(R.id.txtviewok);
	              
	           txtviewok.setOnClickListener(new View.OnClickListener() 
	           {
	              public void onClick(View view) 
	              {
	            	dialog.dismiss();
	              }
	           });
	           
	           dialog.show();
	        }
	        catch(Exception e)
	        {}
	}
	
	
	class Saloons extends AsyncTask<URL, Integer, Long>
	{
		MyProgressDialog dialog;
		String jsonresponse;
		protected void onPreExecute() 
		  { 
			  dialog = MyProgressDialog.show(getParent(), null,null);
			 dialog.getWindow().setGravity(Gravity.CENTER);
		  }
		
		protected Long doInBackground(URL... arg0) 
		{
			if(DataUrls.click==1)
			{
				String str_countrycode=map_country.get(str_countryname);
				Log.e("str_country code is","<><."+str_countrycode);
				//url="http://app.hairconstruction.co/api/customers/null/null/"+zipcode+"?format=json";
				url="http://app.hairconstruction.co/api/Customers/"+zipcode+"/"+str_countrycode+"?format=json";
			}
			else if(DataUrls.click==2)
			{
				url="http://app.hairconstruction.co/api/customers/"+latitude+"/"+longitude+"/null?format=json";
			}
			else if(DataUrls.click==0)
			{
				url="http://app.hairconstruction.co/api/customers/"+latitude+"/"+longitude+"/null?format=json";
			}
			Log.e("url is",url);
			
			try
			{
				jsonresponse=UrltoValue.getValuefromUrl(url);
				Log.e("json response is",jsonresponse);
			}
			catch(TimeoutException e){}
			return null;	
		}
		
		protected void onPostExecute(Long result) 
		 {
			dialog.dismiss();
			
			try{
				JSONObject jsonobject=new JSONObject(jsonresponse);
				if(jsonobject.has("listcustomers"))
				{
				JSONArray jsonarry=jsonobject.getJSONArray("listcustomers");
				Log.e("length is","<><>"+jsonarry.length());
				for(int i=0;i<jsonarry.length();i++)
				{
					JSONObject object=jsonarry.getJSONObject(i);
					saloonsitem=new SaloonsItem();
					saloonsitem.setId(object.getString("id"));
					saloonsitem.setEmail(object.getString("email"));
					saloonsitem.setLng(object.getString("lng"));
					saloonsitem.setLat(object.getString("lat"));
					saloonsitem.setValid(object.getString("valid"));
					saloonsitem.setSurname(object.getString("surname"));
					saloonsitem.setFirstname(object.getString("firstname"));
					saloonsitem.setBirth_date(object.getString("birth_date"));
					saloonsitem.setWorkplace(object.getString("workplace"));
					saloonsitem.setAddress(object.getString("address"));
					saloonsitem.setCity(object.getString("city"));
					saloonsitem.setState(object.getString("state"));
					if(object.has("phone"))
					{
						saloonsitem.setPhone(object.getString("phone"));
					}
					else
					{
						saloonsitem.setPhone("null");
					}
					if(object.has("zip"))
					{
						saloonsitem.setZip(object.getString("zip"));
					}
					else
					{
						saloonsitem.setZip("null");
					}
					saloonsitem.setCountry(object.getString("country"));
					saloonsitem.setCreatedBy(object.getString("createdBy"));
					saloonsitem.setCreatedDate(object.getString("createdDate"));
					saloonsitem.setUpdatedBy(object.getString("updatedBy"));
					saloonsitem.setModifiedDate(object.getString("modifiedDate"));
					saloonsitem.setWorkplaceId(object.getString("workplaceId"));
					saloonsitem.setExperience(object.getString("experience"));
					if(object.has("zip"))
					{
						saloonsitem.setStatus(object.getString("status"));
					}
					else
					{
						saloonsitem.setStatus("null");
					}
					saloonsitem.setSalonfinder(object.getString("salonfinder"));
					saloonsitem.setCut(object.getString("cut"));
					saloonsitem.setColor(object.getString("color"));
					saloonsitem.setExtentions(object.getString("extentions"));
					saloonsitem.setMen(object.getString("men"));
					saloonsitem.setWomen(object.getString("women"));
					saloonsitem.setPromoId(object.getString("promoId"));
					saloonsitem.setDistance(object.getString("distance"));
					saloonsitem.setProfilepic(object.getString("profilepic"));
					saloonsitem.setListsaloons(object.getJSONArray("listsaloons"));
					listsaloons.add(saloonsitem);
				}
				}
				else
				{
					Toast.makeText(getParent(), "No Records Found", Toast.LENGTH_LONG).show();
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			saloonsadapter.notifyDataSetChanged();
		 }
		
		}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		
		if(v.equals(imgviewzip))
		{
//			spn_country.setSelection(40);
			rel_getit.setVisibility(View.GONE);
			rel_zip.setVisibility(View.VISIBLE);
			saloonsadapter.notifyDataSetChanged();
			new Country().execute();
		}
		else if(v.equals(imgviewback))
		{
			DataUrls.click=0;
			TabActivityActivity.group.back();
		}
		else if(v.equals(imgviewsearchbynear))
		{
			DataUrls.click=2;
			
			if(enabled==true)
			{
				listsaloons.clear();
				saloonsadapter.notifyDataSetChanged();
				getCurrentLocationValues();
				new Saloons().execute();
			}
			else
			{
				showDialog();
			}
			
		}
		
		else if(v.equals(imgviewback1))
		{
			rel_zip.setVisibility(View.GONE);
			rel_getit.setVisibility(View.VISIBLE);
			edttextnumbox.setText("");
		}
		
		
		else if(v.equals(imgviewsearch))
		{
			if(str_countryname.equals(""))
			{
				Log.e("str_countryname is","null    "); 
				Toast.makeText(getParent(), "Please select country", Toast.LENGTH_SHORT).show();
			}
			else
			{
				saloonsadapter.notifyDataSetChanged();
				listsaloons.clear();
				rel_zip.setVisibility(View.GONE);
				rel_getit.setVisibility(View.VISIBLE);
				DataUrls.click=1;
				zipcode=edttextnumbox.getText().toString();
				edttextnumbox.setText("");
				new Saloons().execute();
				Log.e("str_countryname is","<><>"+str_countryname);
			}
		}
		
		else if(v.equals(imgviewbigrrow))
		{
			if(!(edttextnumbox.length()==0))
			    edttextnumbox.setText(edttextnumbox.getText().delete(edttextnumbox.length() - 1, edttextnumbox.length()));
		}
		
		else if(map.containsKey(v))
		{
			if(edttextnumbox.length()<6)
			{
				String s=map.get(v);
				edttextnumbox.setText(edttextnumbox.getText() + s);
			}
		}
		
		else if(v.equals(imgviewbigarrw_dial))
		{
			if(!(edttextnumbox1.length()==0))
			    edttextnumbox1.setText(edttextnumbox1.getText().delete(edttextnumbox1.length() - 1, edttextnumbox1.length()));
		}
		
		else if(map_dial.containsKey(v))
		{
			String s=map_dial.get(v);
			edttextnumbox1.setText(edttextnumbox1.getText() + s);
		}
		
		else if(v.equals(imgviewcall))
		{
			rel_getit.setVisibility(View.GONE);
			rel_dialing.setVisibility(View.VISIBLE);
		}
		
		else if(v.equals(imgviewback2))
		{
			rel_dialing.setVisibility(View.GONE);
			rel_getit.setVisibility(View.VISIBLE);
			edttextnumbox1.setText("");
		}
		
		else if(v.equals(imgviewdial))
		{
			showDialog1();
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
		else if(v==imgviewhearttaboff)
		{
//			DataUrls.from=1;
			Log.e("clicked on","favourites");
				
			View vi =TabActivityActivity.group.getLocalActivityManager()  
			           .startActivity("Items", new Intent(getApplicationContext(),GridviewActivity.class)
//			           .putExtra("imageBo", ""+gson.toJson(imagesBO)) 
//			           .putExtra("parent", "Activity")
			           .putExtra("from", "1")
			           .putExtra("tab", "0")
			           .putExtra("url","http://app.hairconstruction.co/api/Account/Favorites/"+TabActivityActivity.userid+"?format=json")
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
    public void onBackPressed() 
	{ 
    TabActivityActivity.group.back();
    return;  
   }
	 public void showDialog1()
 	{
 		 try
 	       {
 	              final Dialog dialog = new Dialog(getParent(),R.style.PauseDialog);
 	              dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
 	              dialog.setContentView(R.layout.calltosaloon);
 	              
 	              dialog.setCancelable(false);
 	              dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
 	             
 	              TextView    txtviewcancel=(TextView)dialog.findViewById(R.id.txtviewcancel);
 	              TextView    txtviewok=(TextView)dialog.findViewById(R.id.txtviewok);
 	              TextView    txtviewsaloonnumber=(TextView)dialog.findViewById(R.id.txtviewsaloonnumber);
 	              
 	              txtviewsaloonnumber.setText(edttextnumbox1.getText().toString());
 	              
 	              Log.e("phonenumber is",edttextnumbox1.getText().toString());
 	              
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
 	            	 i.setData(Uri.parse("tel:" + edttextnumbox1.getText().toString()));
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
	 
	 public class HorizontalImageadapter extends ArrayAdapter<String>
		{

			ArrayList<String> listsubstyles = new ArrayList<String>();
			Context context;
			DisplayImageOptions optionsprofileimge1;
			public HorizontalImageadapter(Context context, int textViewResourceId,ArrayList<String> listsubstyles) {
				super(context, listsubstyles.size(),listsubstyles);
				// TODO Auto-generated constructor stub
				this.listsubstyles=listsubstyles;
				this.context=context;
			}

			public View getView(int position, View convertView, ViewGroup parent) 
			{
				LayoutInflater inflater=GetitActivity.this.getLayoutInflater();	
				View  gridView = inflater.inflate(R.layout.horizontal_item_styles, null);
		         ImageView iv = (ImageView)gridView.findViewById(R.id.imgviewbuzz) ;
		         Log.e("list is","<><><>"+listsubstyles.get(position));
		         
		         Picasso.with(context).load(listsubstyles.get(position).replaceAll(" ", "%20")).into(iv);
		        RelativeLayout rel_text= (RelativeLayout)gridView.findViewById(R.id.rel_text);
		        rel_text.setVisibility(View.GONE);
				return gridView;
			}
		}
	 
	 
	 class Country extends AsyncTask<URL, Integer, Long>
		{
			MyProgressDialog dialog;
			String jsonresponse;
			protected void onPreExecute() 
			  { 
				  dialog = MyProgressDialog.show(getParent(), null,null);
				 dialog.getWindow().setGravity(Gravity.CENTER);
			  }
			
			protected Long doInBackground(URL... arg0) 
			{
				try
				{
					jsonresponse=UrltoValue.getValuefromUrl("http://app.hairconstruction.co/api/Countries?format=json");
					Log.e("respoonse is","<><>"+jsonresponse);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				return null;	
			}
			
			protected void onPostExecute(Long result) 
			 {
				dialog.dismiss();
				
				try 
				{
					JSONArray jsonarray=new JSONArray(jsonresponse);
					for(int i=0;i<jsonarray.length();i++)
					{
						JSONObject jsonobject=jsonarray.getJSONObject(i);
						String str_countryname=jsonobject.getString("countryName");
						String str_countrycode=jsonobject.getString("countryCode");
						
						al_countryname.add(str_countryname);
						
						map_country.put(str_countryname, str_countrycode);
					}
					for(int i=0;i<al_countryname.size();i++){
			            
//			          Log.e("age specified is","age is"+stateage.get(i).toString()+"age"+strage1);
			          if(al_countryname.get(i).equals("United States")){
//			           Log.e("age ","age is<><><<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>"+al_countryname.get(i)+"i valuecv,.......,.,.,<><><>>>>>>>>>>>>> "+i);
			           spn_country.setSelection(i);

			          }
			         }
				} 
				catch (JSONException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				dataAdapter.notifyDataSetChanged();
				
			 }
			
			}
   
}
