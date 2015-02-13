package com.stylez.activities1;



import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.stylez.helpers.CheckInternetConnection;
import com.stylez.helpers.MyProgressDialog;
import com.stylez.helpers.UrltoValue;

public class ForgotPasswordActivity extends Activity implements OnClickListener
{
	ImageView imgviewback,imgviewsubmitoff,imgviewsubmiton;
	EditText edttextemail;
	MyProgressDialog  dialog;
	String jsonresponse="",strstatus,strmessage,stremail;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgotpassword);
		imgviewback=(ImageView)findViewById(R.id.imgviewback);
		imgviewsubmitoff=(ImageView)findViewById(R.id.imgviewsubmitoff);
		imgviewsubmiton=(ImageView)findViewById(R.id.imgviewsubmiton);
		edttextemail=(EditText)findViewById(R.id.edttextemail);
		imgviewsubmitoff.setOnClickListener(this);
		imgviewback.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if(v.equals(imgviewback))
		{
			Intent login=new Intent(ForgotPasswordActivity.this,LoginScreenActivity.class);
       	 	startActivity(login);
       	 	finish();
		}
		if(v.equals(imgviewsubmitoff))
		{
			validation();
			stremail=edttextemail.getText().toString();
		}
	}
	
	public void validation()
	{
		if(edttextemail.getText().toString().trim().length()==0)
		{
			toast("Please Enter Email");
		}
		else
		{
			if(checkEmailCorrect(edttextemail.getText().toString().trim()))
			{
				 if(CheckInternetConnection.isOnline(this))
				 {
					 new ForgotPassword().execute();
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
	
	public void toast(String msg)
	{
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
	
	//for email validation
	boolean checkEmailCorrect(String email)
     {
      String pttn = "^\\D.+@.+\\.[a-z]+";
      Pattern p = Pattern.compile(pttn);
      Matcher m = p.matcher(email);
      if(m.matches())
      { 
    	  return true;
      }
      else
      {
    	  return false;
    	}
     }
	
	class ForgotPassword extends AsyncTask<URL, Integer, Long>
	{
		protected void onPreExecute() 
		  { 
			 dialog=MyProgressDialog.show(ForgotPasswordActivity.this, null,null);
			 dialog.getWindow().setGravity(Gravity.CENTER);
		  }
		
		protected Long doInBackground(URL... arg0) 
		{
			try
			{
			jsonresponse=UrltoValue.getValuefromUrl("http://app.hairconstruction.co/api/Account/login/"+stremail+"?format=json");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			Log.e("json response is",jsonresponse);
			if(!jsonresponse.equals("") && !jsonresponse.equals("null"))
            {
          	  try
          	  {
          		  JSONObject jo=new JSONObject(jsonresponse);
          		  strstatus=jo.getString("status");
          		 strmessage=jo.getString("message");
          		 
          	  }catch(Exception e)
          	  {
          		  e.printStackTrace();
          	  }
            }
			else
			{
          	  
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
	
	@SuppressWarnings("deprecation")
	private void alertDialog(String msg) 
	{
		// TODO Auto-generated method stub
		AlertDialog alertDialog = new AlertDialog.Builder(ForgotPasswordActivity.this).create();
        alertDialog.setTitle("StyleZ");
        alertDialog.setMessage(msg);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() 
        {
        public void onClick(DialogInterface dialog, int which) {
        	
        // Write your code here to execute after dialog closed
       dialog.dismiss();
       
       if(strstatus.equals("success"))
       {
    	   imgviewsubmitoff.setVisibility(View.GONE);
    	   imgviewsubmiton.setVisibility(View.VISIBLE);
    	   Intent i=new Intent(ForgotPasswordActivity.this,LoginScreenActivity.class);
    	   startActivity(i);
    	   finish();
       }
        }
     });
     alertDialog.show();
	}
}
