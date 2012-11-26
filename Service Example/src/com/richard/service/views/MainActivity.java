package com.richard.service.views;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.richard.service.R;
import com.richard.service.services.IntentServiceExample;
import com.richard.service.services.ServiceExample;
import com.richard.service.services.ServiceForScheduleExample;

/**
 * @author Richard Pablo
 * 
 * En este ejemplo nos daremos cuenta de que el Service se mantiene activo hasta que lo
 * detengamos mediante un stopService(i), o hasta que se detenga dentro mediante un
 * stopSelf().
 * 
 * El IntentService se lanza y se detiene el solo cuando termina el proceso, por eso
 * nuestro bucle no pasa de la primera repetición, porque al llegar al final del 
 * proceso llama al onDestroy() y detiene los callbacks del Handler.
 * 
 * A parte de los servicios normales, tambien tenemos tareas programadas (Scheduled Tasks)
 * que son aquellas que que se llaman después de pasado un tiempo, por ejemplo, actualizar
 * la base de datos. Se pueden hacer puntualmente o cada cierto tiempo, para ello se emplea
 * el alarmManager.set() o el alarmManager.setRepeating(), teniendo este ultimo un parámetro
 * más donde se le indica cada cuantos milisegundos debe repetirse esta tarea, estos milisegundos
 * pueden setearse a mano o mediante las constantes AlarmManager.INTERVAL_...
 */
public class MainActivity extends Activity {
	
	private PendingIntent mPendingIntent;
	private PendingIntent mRepetitivePendingIntent;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
	public void onButtonClick(View v) {
		
		Intent serviceIntent = new Intent(this, ServiceExample.class);
		Intent intentServiceIntent = new Intent(this, IntentServiceExample.class);
		
		int secondsToStart = 5;
		Intent intentScheduleServiceIntent = new Intent(this, ServiceForScheduleExample.class);
		AlarmManager alarmManager;
		switch (v.getId()) {
		
		case R.id.btn_start_service:
			serviceIntent.putExtra(ServiceExample.DELAY_TIME, 5000);
			Toast.makeText(this, "Start Service", Toast.LENGTH_SHORT).show();
			startService(serviceIntent);
			break;
		case R.id.btn_stop_service:
			Toast.makeText(this, "Stop Service", Toast.LENGTH_SHORT).show();
			stopService(serviceIntent);
			break;
			
		case R.id.btn_start_intent_service:
			intentServiceIntent.putExtra(IntentServiceExample.DELAY_TIME, 5000);
			Toast.makeText(this, "Start Service", Toast.LENGTH_SHORT).show();
			startService(intentServiceIntent);
			break;
		case R.id.btn_stop_intent_service:
			Toast.makeText(this, "Stop Service", Toast.LENGTH_SHORT).show();
			stopService(intentServiceIntent);
			break;
			
		case R.id.btn_set_scheduled_service:
			Toast.makeText(this, "Will start the Service in "+secondsToStart+" seconds", Toast.LENGTH_SHORT).show();
			
			mPendingIntent = PendingIntent.getService(this, 0, intentScheduleServiceIntent, 0);
			
			alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.add(Calendar.SECOND, secondsToStart);
			
			alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), mPendingIntent);

			break;
		case R.id.btn_cancel_scheduled_service:
			Toast.makeText(this, "Scheduled Service canceled", Toast.LENGTH_SHORT).show();
			
			alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
			alarmManager.cancel(mPendingIntent);
			
			break;
			
		case R.id.btn_set_scheduled_rep_service:
			
			int secondsForRepeat = 10;
			Toast.makeText(this, "Will start the Service in "+secondsToStart+" seconds and repeat every "+secondsForRepeat+" seconds", Toast.LENGTH_SHORT).show();
			
			mRepetitivePendingIntent = PendingIntent.getService(this, 0, intentScheduleServiceIntent, 0);
			
			alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			
			Calendar triggerCalendar = Calendar.getInstance();
			triggerCalendar.setTimeInMillis(System.currentTimeMillis());
			triggerCalendar.add(Calendar.SECOND, secondsToStart);
			
			
			secondsForRepeat = secondsForRepeat * 1000; // Parse seconds to milliseconds
			// El intervalo pueden ser miliegundos puestos a mano como aqui o con las constantes de AlarmManager.INTERVAL_...
			alarmManager.setRepeating(AlarmManager.RTC, triggerCalendar.getTimeInMillis(), secondsForRepeat, mRepetitivePendingIntent);

			break;
		case R.id.btn_cancel_scheduled_rep_service:
			Toast.makeText(this, "Scheduled Repetitive Service canceled", Toast.LENGTH_SHORT).show();
			
			alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
			alarmManager.cancel(mRepetitivePendingIntent);
			break;

		default:
			break;
		}
    }
}
