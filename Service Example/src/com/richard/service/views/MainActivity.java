package com.richard.service.views;

import com.richard.service.R;
import com.richard.service.services.IntentServiceExample;
import com.richard.service.services.ServiceExample;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

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
 */
public class MainActivity extends Activity {

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
    
    /*Con esto nos daremos cuenta de que el Service se mantiene activo hasta que lo
     * detengamos mediante un stopService(i), o hasta que se detenga dentro mediante un
     * stopSelf().
     * 
     * El IntentService se lanza y se detiene solo cuando termina el proceso, por eso
     * nuestro bucle no pasa de la primera repetición, porque al llegar al final del 
     * proceso llama al onDestroy() y detiene los callbacks del Handler.
     * */
	public void onButtonClick(View v) {
		Intent iService = new Intent(this, ServiceExample.class);
		Intent iIntentService = new Intent(this, IntentServiceExample.class);
		
		switch (v.getId()) {
		case R.id.btn_start_service:
			iService.putExtra(ServiceExample.DELAY_TIME, 5000);
			Toast.makeText(this, "Start Service", Toast.LENGTH_SHORT).show();
			startService(iService);
			break;
		case R.id.btn_stop_service:
			Toast.makeText(this, "Stop Service", Toast.LENGTH_SHORT).show();
			stopService(iService);
			break;
		case R.id.btn_start_intent_service:
			iIntentService.putExtra(IntentServiceExample.DELAY_TIME, 5000);
			Toast.makeText(this, "Start Service", Toast.LENGTH_SHORT).show();
			startService(iIntentService);
			break;
		case R.id.btn_stop_intent_service:
			Toast.makeText(this, "Stop Service", Toast.LENGTH_SHORT).show();
			stopService(iIntentService);
			break;

		default:
			break;
		}
    }
}
