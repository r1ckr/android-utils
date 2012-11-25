package com.richard.service.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * @author Richard Pablo
 */
public class ServiceExample extends Service{
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.w(getClass().getName(), "Entra en el servicio");
		start();
		return START_NOT_STICKY;
	}
	
	@Override
	public void onDestroy() {
		stop();
	}
	
	private void start(){
		Log.w(getClass().getName(), "Empieza");
	}
	
	private void stop(){
		Log.w(getClass().getName(), "Detenido");	
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
