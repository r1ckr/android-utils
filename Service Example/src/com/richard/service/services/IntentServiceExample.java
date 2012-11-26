package com.richard.service.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

/**
 * @author Richard Pablo
 */
public class IntentServiceExample extends IntentService{
	
	public static final String DELAY_TIME = "delayTime";
	private int mDelayTime; //Milliseconds
	private Handler mHandler;
	
	public IntentServiceExample() {
		super("IntentServiceExample");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.w(getClass().getName(), "IntentService starts");
		mDelayTime = intent.getIntExtra(DELAY_TIME, 1000);
		mHandler= new Handler();
		start();
	}

	@Override
	public void onDestroy() {
		stop();
	}
	
	private void start(){
		repetitiveTask.run();
	}
	
	private void stop(){
		mHandler.removeCallbacks(repetitiveTask);
		Log.w(getClass().getName(), "IntentService stopped");
	}
	
	Runnable repetitiveTask = new Runnable()
	{
	     @Override 
	     public void run() {
	    	 Log.w(getClass().getName(), "Log every "+mDelayTime/1000+" seconds");
	          mHandler.postDelayed(repetitiveTask, mDelayTime);
	     }
	};
}
