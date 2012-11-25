package com.richard.service.views;

import com.richard.service.R;
import com.richard.service.services.ServiceExample;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

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
    
	public void onButtonClick(View v) {
		Intent i = new Intent(this, ServiceExample.class);
		switch (v.getId()) {
		case R.id.btn_start_service:
			Toast.makeText(this, "Start Service", Toast.LENGTH_SHORT).show();
			startService(i);
			break;
		case R.id.btn_stop_service:
			Toast.makeText(this, "Stop Service", Toast.LENGTH_SHORT).show();
			stopService(i);
			break;

		default:
			break;
		}
    }
}
