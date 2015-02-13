package com.stylez.helpers;

import java.io.File;

import android.content.Context;
import android.util.Log;

public class FileCache {
    
    private File cacheDir;
    
    public FileCache(Context context){
        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
        {
        	Log.e("1","<><><><><><><><><>");
        	cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"LazyList_stylez");
        }
        else{
        	Log.e("2","<><><><><><><><><>");
        	cacheDir=context.getCacheDir();
        }
        if(!cacheDir.exists())
        {
        	Log.e("3","<><><><><><><><><>");
        	cacheDir.mkdirs();
        }
    }
    
    public File getFile(String url){
        //I identify images by hashcode. Not a perfect solution, good for the demo.
        String filename=String.valueOf(url.hashCode());
        File f = new File(cacheDir, filename);
        return f;
        
    }
    
    public void clear(){
        File[] files=cacheDir.listFiles();
        for(File f:files)
            f.delete();
    }

}