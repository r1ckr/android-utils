package com.richard.service.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

/**
 * @author Richard Pablo
 */
public class ServiceExample extends Service{
	public static final String DELAY_TIME = "delayTime";
	private int mDelayTime; //Milliseconds
	private Handler mHandler;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.w(getClass().getName(), "Service starts");
		mDelayTime = intent.getIntExtra(DELAY_TIME, 1000);
		mHandler= new Handler();
		start();
		return START_NOT_STICKY;
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
		Log.w(getClass().getName(), "Service stopped");
	}
	
	
	Runnable repetitiveTask = new Runnable()
	{
	     @Override 
	     public void run() {
	    	 Log.w(getClass().getName(), "Log every "+mDelayTime/1000+" seconds");
	          mHandler.postDelayed(repetitiveTask, mDelayTime);
	     }
	};
	

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
