package com.stylez.activities1;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;

@ReportsCrashes(
        formKey = "", // This is required for backward compatibility but not used
        mailTo = "mohanraj.k@stellentsoft.com",
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text
        )
    

public class MyApplication  extends Application{
	
	public void onCreate() {
        super.onCreate();

        // The following line triggers the initialization of ACRA
        ACRA.init(this);
    }

}
