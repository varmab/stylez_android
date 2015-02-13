package com.stylez.helpers;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.content.Context;



public class DataUrls 
{
	public static int from;
	public static int click;
	public static String signup="http://app.hairconstruction.co/api/account/signup?format=json";
	public static String login="http://app.hairconstruction.co/api/account/login?format=json";
	public static String editprofile="http://app.hairconstruction.co/api/account/signup?format=json";
		
	
	
	
	public static void initImageLoader(Context context) {
		 
		 ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		   .threadPriority(Thread.NORM_PRIORITY - 2)
		   .denyCacheImageMultipleSizesInMemory()
		   .discCacheFileNameGenerator(new Md5FileNameGenerator())
		   .tasksProcessingOrder(QueueProcessingType.LIFO)
		   .writeDebugLogs() // Remove for release app
		   .build();
		 // Initialize ImageLoader with configuration.
		 ImageLoader.getInstance().init(config);
		}
}
