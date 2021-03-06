package com.richard.service.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ServiceForScheduleExample extends Service{
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.w(getClass().getName(), "Scheduled Service starts");
		start();
		stopSelf();
		return START_NOT_STICKY;
	}
	
	@Override
	public void onDestroy() {
		stop();
	}
	
	private void start(){
		Log.w(getClass().getName(), "Log Scheduled Service");
	}
	
	private void stop(){
		Log.w(getClass().getName(), "Scheduled Service stopped");
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
