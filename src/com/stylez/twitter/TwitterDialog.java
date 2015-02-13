package com.stylez.twitter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.stylez.activities1.R;
import com.stylez.twitter.TwitterApp.TwDialogListener;

//280,420

public class TwitterDialog extends Dialog {

	static final float[] DIMENSIONS_LANDSCAPE = { 460, 260 };
	static final float[] DIMENSIONS_PORTRAIT = { 340, 540 };
	static final FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(
			ViewGroup.LayoutParams.FILL_PARENT,
			ViewGroup.LayoutParams.FILL_PARENT);
	static final int MARGIN = 4;
	static final int PADDING = 2;
	private String mUrl;
	private TwDialogListener mListener;
	private ProgressDialog mSpinner;
	private WebView mWebView;
	private ScrollView scroll;
	private LinearLayout mContent;
	private TextView mTitle;
	private boolean progressDialogRunning = false;
	Display display;
Context con;
	public TwitterDialog(Context context, String url, TwDialogListener listener) {
		super(context);
this.con=context;
		mUrl = url;
		mListener = listener;
	}
	private RelativeLayoutWithLayoutListener mMainLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.twitter);
		mWebView=(WebView)findViewById(R.id.webview);
		
//		scroll=(ScrollView)findViewById(R.id.scroll);
		
		mSpinner = new ProgressDialog(getContext());

		mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mSpinner.setMessage("Loading...");

		//mContent = new LinearLayout(getContext());

	//	mContent.setOrientation(LinearLayout.VERTICAL);
      
		
		
		
		
		//setUpTitle();
		setUpWebView();

		 display = getWindow().getWindowManager().getDefaultDisplay();
//		final float scale = getContext().getResources().getDisplayMetrics().density;
//		float[] dimensions = (display.getWidth() < display.getHeight()) ? DIMENSIONS_PORTRAIT
//				: DIMENSIONS_LANDSCAPE;
//
//		 addContentView(mContent, new FrameLayout.LayoutParams(
//				(int) (dimensions[0] *scale + 0.5f), (int) (dimensions[1]
//						* scale + 0.5f)));
//		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	//	requestWindowFeature(Window.FEATURE_NO_TITLE);
//		mWebView.getSettings().setJavaScriptEnabled(true);
//		mWebView.loadUrl(mUrl);
//		
		mMainLayout = (RelativeLayoutWithLayoutListener)findViewById(R.id.mainlayout);

	    mMainLayout.setLayoutListener(new LayoutListener() {
          @Override
	        public void onLayout() {
//	            mWebView.loadUrl("javascript:setSize(" + mMainLayout.getWidth() + "," + 500 + ")");   
        	  mWebView.scrollTo(0, (display.getHeight())/4);
	        }
	    });
	}

	private void setUpTitle() {
		
		try{
	//	requestWindowFeature(Window.FEATURE_NO_TITLE);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
      }

	@SuppressLint("SetJavaScriptEnabled")
	private void setUpWebView() {
		mWebView.setWebViewClient(new TwitterWebViewClient());
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl(mUrl);
	}

	private class TwitterWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (url.startsWith(TwitterApp.CALLBACK_URL)) {
				mListener.onComplete(url);

				TwitterDialog.this.dismiss();

				return true;
			} else if (url.startsWith("authorize")) {
				return false;
			}
			return true;
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			mListener.onError(description);
			TwitterDialog.this.dismiss();
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			mSpinner.show();
			progressDialogRunning = true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
//			String title = mWebView.getTitle();
//			if (title != null && title.length() > 0) {
//				mTitle.setText(title);
//			}
			progressDialogRunning = false;
			mSpinner.dismiss();
//			scroll.fullScroll(View.FOCUS_DOWN);    
		}
		
		

	}
	
	
	

	@Override
	protected void onStop() {
		progressDialogRunning = false;
		super.onStop();
	}

	public void onBackPressed() {
		if (!progressDialogRunning) {
			TwitterDialog.this.dismiss();
		}
	}
	
	
	
	
	
}
