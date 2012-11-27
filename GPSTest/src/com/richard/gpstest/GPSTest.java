package com.richard.gpstest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.richard.gpstest.LocationService.LocationResult;

public class GPSTest extends Activity {
    
	private TextView mLatText;
	private TextView mLonText;
	private TextView mVelText;
	private TextView mAcuText;
	private TextView mInfoText;
	private Boolean mIsTraking;
	private LinearLayout mDataLayout;
	private SharedPreferences mPrefs;
	private LocationService mLocationService;
	private int mMinAccuracy;
	private int mRefreshTime;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mLatText = (TextView)findViewById(R.id.lat);
        mLonText = (TextView)findViewById(R.id.lon);
        mVelText = (TextView)findViewById(R.id.vel);
        mAcuText = (TextView)findViewById(R.id.acu);
        mInfoText = (TextView)findViewById(R.id.info);
        mDataLayout=(LinearLayout)findViewById(R.id.data);
        mIsTraking = false;
        mMinAccuracy = 300;
        mRefreshTime = 0;
        mPrefs=PreferenceManager.getDefaultSharedPreferences(GPSTest.this);
        mPrefs.registerOnSharedPreferenceChangeListener(prefsListener);

        mDataLayout.setOnClickListener(onLayoutClick);
        
        mLocationService = new LocationService();
        startStopGpsTrack();
    }
    @Override
	protected void onPause() {
		super.onPause();
		mIsTraking=true;
		startStopGpsTrack();
	}
	@Override
	protected void onResume() {
		super.onResume();
		mIsTraking=false;
		startStopGpsTrack();
	}
    
    /**Interruptor to start or stop tracking*/
    public void startStopGpsTrack(){
    	if(mIsTraking){
    		mDataLayout.setBackgroundResource(android.R.color.background_dark);
			mLatText.setTextColor(Color.WHITE);
			mLonText.setTextColor(Color.WHITE);
			mVelText.setTextColor(Color.WHITE);
			mAcuText.setTextColor(Color.WHITE);
			mInfoText.setText(getResources().getString(R.string.off));
    		mLocationService.stopUpdates();
    		mIsTraking=false;
    		Toast.makeText(this,getResources().getString(R.string.tracking_off), Toast.LENGTH_SHORT ).show();
    	}else {
    		mDataLayout.setBackgroundResource(android.R.color.background_light);
			mLatText.setTextColor(Color.BLACK);
			mLonText.setTextColor(Color.BLACK);
			mVelText.setTextColor(Color.BLACK);
			mAcuText.setTextColor(Color.BLACK);
			mInfoText.setText(getResources().getString(R.string.on));
			mLocationService.startUpdates(this, locationResult, mRefreshTime, mMinAccuracy);
    		mIsTraking=true;
    		Toast.makeText(this,getResources().getString(R.string.tracking_on), Toast.LENGTH_SHORT ).show();
    	}    	
    }

	// ----- Listeners start -----
	//Listener for the Layout Click
	OnClickListener onLayoutClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			startStopGpsTrack();
		}
	};
	
	//Location listener
	LocationResult locationResult = new LocationResult() {
		@Override
		public void gotLocation(Location location, String source) {
			if (source.equals(LocationManager.GPS_PROVIDER)){
				mDataLayout.setBackgroundColor(Color.rgb(152, 251, 152));
				mInfoText.setText(getResources().getString(R.string.gps));
			}else{
				mDataLayout.setBackgroundColor(Color.rgb(135, 206, 250));
				mInfoText.setText(getResources().getString(R.string.net));
			}
			
	          mLatText.setText(getResources().getString(R.string.lat)+String.valueOf(location.getLatitude()));
	          mLonText.setText(getResources().getString(R.string.lon)+String.valueOf(location.getLongitude()));
	          mVelText.setText(getResources().getString(R.string.vel)+String.valueOf(location.getSpeed())+"m/s");
	          mAcuText.setText(getResources().getString(R.string.acu)+String.valueOf(location.getAccuracy()));
		}
	};
    
    // Preferences listener
    SharedPreferences.OnSharedPreferenceChangeListener prefsListener= new OnSharedPreferenceChangeListener() {
		
		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
				String key) {
			if (key.equals("refresh_time")) {
				mRefreshTime=Integer.parseInt(mPrefs.getString("refresh_time", "1"));
			}
		}
	};
	// ----- Listeners end -----
	
	// ----- Menu start -----
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(this).inflate(R.menu.options, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==R.id.menu_preferences){
			startActivity(new Intent(GPSTest.this, EditPreferences.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	// ----- Menu end -----
}

