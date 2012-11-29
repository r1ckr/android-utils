/**
 * @author Richard Pablo
 * */

package com.richard.service.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class ServiceForResultExample extends IntentService{
	public static final String ACTION_COMPLETE="com.richard.service.services.serviceforresult.action.COMPLETE";
	
	public ServiceForResultExample() {
		super("ServiceForResult");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.w(getClass().getName(), "Service For Result Started");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sendBroadcast(new Intent(ACTION_COMPLETE));
	}

}
