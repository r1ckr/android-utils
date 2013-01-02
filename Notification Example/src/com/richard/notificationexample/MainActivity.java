package com.richard.notificationexample;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	
	public static final int NOTIFICATION_ID = 0;
	
	NotificationManager mNotificationManager;
	NotificationCompat.Builder mBuilder;
	Notification.Builder mBuilderHoney;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mNotificationManager =
			    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		/*Si la notificacion se quiere cancelar al entrar en el Activity se hace así y 
		 * se quita el autocancel en la construcción, de esta manera es mejor */
		mNotificationManager.cancel(NOTIFICATION_ID);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.btn_add_simple_notification:
			
			
			buildNotification("My Simple notification", "Hello World!");
			// The Id allows you to update the notification later on.
			
			break;
		case R.id.btn_update_notification:
			buildNotification("My Simple notification", "Notification updated!!");
			// Here we use the Id to update the notification
			break;
		case R.id.btn_add_old_notification:
			buildOldNotification("My Simple notification", "Hi! this is an old notification!!");
			// Here we use the Id to update the notification
			break;
		case R.id.btn_add_selected_notification:
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
				buildOldNotification("My Simple notification", "Hi! this is an old notification!!");
			}else{
				buildNotificationForJelly("My Simple notification", "Hi! this is an old notification only for Honeycomb and beyond versions!!");
			}
			break;
		default:
			break;
		}
	}
	
	private void buildNotification(String title, String content){
		
		mBuilder = new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle(title)
		        .setContentText(content);
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, MainActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		//Si se quiere cancelar cuando el usuario haga clic en ella se setea setAutoCancel a true
		//mBuilder.setAutoCancel(true);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}
	
	private void buildNotificationForJelly(String title, String content){
		
		mBuilderHoney = new Notification.Builder(this)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle(title)
		        .setContentText(content);
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, MainActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		android.app.TaskStackBuilder stackBuilder = android.app.TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilderHoney.setContentIntent(resultPendingIntent);
		//Si se quiere cancelar cuando el usuario haga clic en ella se setea setAutoCancel a true
		//mBuilder.setAutoCancel(true);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilderHoney.build());
	}

	@SuppressWarnings("deprecation")
	private void buildOldNotification(String title, String content){

        Notification notification = new Notification();
        notification.icon=R.drawable.ic_launcher;
        notification.when=System.currentTimeMillis();
        // The PendingIntent will launch activity if the user selects this
        // notification
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, new Intent(this, MainActivity.class), 0);
        notification.setLatestEventInfo(this, title, content,
                contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, notification);
	}
	
}
