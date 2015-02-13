package com.stylez.activities1;

import java.util.ArrayList;

import android.app.ActivityGroup;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;

@SuppressWarnings("deprecation")
public class TabActivityActivity extends ActivityGroup {
	
	public static TabActivityActivity group;  
	 public static ArrayList<View> history;  
	 public static String userid;
	   public View view;
	   
	   @SuppressWarnings("deprecation")
	@Override  
	    protected void onCreate(Bundle savedInstanceState) {  
	         super.onCreate(savedInstanceState);  
	       	  this.history = new ArrayList<View>(); 
	       	  Log.e("<><><>><><><><><><>","<><><><><><><><><><>>");
	          group = this;  
	          SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
	 	     userid=preferences.getString("userid", "");
	 	     
	 	     
	          Window wd= getLocalActivityManager().startActivity("profile",new Intent(getParent(),HomeScreenActivity.class)
//	          .putExtra("friend", "1")
	          .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
	          view=wd.getDecorView();
	          replaceView(view);
	   }
	
	   public void replaceView(View v) {  
           // Adds the old one to history  
	
	
   history.add(v);  
   Log.e("View is","added=="+history.size());
   
   //Log.e("arraylilst size is",String.valueOf(history.size()));         // Changes this Groups View to the new View.  
    this.setContentView(v);  
}  
	   @SuppressWarnings("deprecation")
	public void back() {  
	       if(history.size() > 1) {  
	            history.remove(history.size()-1);  
	            setContentView(history.get(history.size()-1));  
	            View   v = history.get(history.size()-1);
	            if(history.size()==1)
	            {
	            	history.clear();

	  	          Window wd= getLocalActivityManager().startActivity("profile",new Intent(getParent(),HomeScreenActivity.class)
//	  	          .putExtra("friend", "1")
	  	          .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
	  	          view=wd.getDecorView();
	  	          replaceView(view);
	            }
//	            SwytchboardTabActivity.reload();
	       }else {  
	            finish();  
	       }  
	    }  
	 
	   @Override  
	    public void onBackPressed() { 
//		   super.onBackPressed();
		   Log.e("Back pressed at","TabActivityActivity"+history.size());
		   back();
		   Log.e("size","<>"+history.size());

		   return;  
	   } 
	   

	// Called when the startActivityForResult() is executed. 
	    @SuppressWarnings("deprecation")
		@Override
	  protected void onActivityResult(int requestCode,int resultCode,Intent data)
	     {
//	     Log.e("its in","camera Tab");
	      Log.v("In OnActivityResult TabCam ", "<tabactivityactrivity>"+requestCode);
	      
	      if(requestCode==0||requestCode==1)
	      {
	    	  EditProfileActivity editprofile = (EditProfileActivity) getLocalActivityManager().getCurrentActivity(); 
	    	  editprofile.onActivityResult(requestCode, resultCode, data); 
	      }
	      
	      else
	      {
	    	  GridviewItemActivity gridviewitem=(GridviewItemActivity)getLocalActivityManager().getCurrentActivity();
	    	  gridviewitem.onActivityResult(requestCode, resultCode, data);
	      }
	      
	     }
	   
}
