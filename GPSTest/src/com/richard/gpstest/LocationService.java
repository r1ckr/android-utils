package com.richard.gpstest;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

/* Esta es una clase con una clase abstracta dentro de ella para hacer callback, 
 * lo que hacemos con esto es poder usar metodos en otras clases que se activen 
 * al dar un return desde un método de esta clase, funciona de la si guiente manera:
 * - Se instancia esta clase (LocationService) desde en otra
 * - Se crea un objeto en la otra clase del tipo de la clase abstracta (LocationResult)
 * - Luego se llama al metodo getLocation() pasandole como parámetro un objeto 
 * 	 del tipo de la clase abstracta (LocationResult), esto es el callback
 * - Este método ejecuta toda su lógica y en el return se llama a ese método de
 * 	 la clase abstracta gotLocation()
 * - Como resultado vamos a tener en la clase que se instancia un listener llamado 
 *   LocationResult que activará el gotLocation() cuando se obtenga alguna ubicación
 *   en esta clase
 */

/**
 * @author Richard
 * */
public class LocationService {
	
	boolean gps_enabled=false;
    boolean network_enabled=false;
    LocationManager mLocationManager;
    LocationResult locationResult;
    Location mCurrentBestLocation=null;
    Long mRefreshTime;
    Float mMinAccuracy;
    Context mContext;
    
    /**
     * @param context The context to use. Usually your Application or Activity object.
     * @param result The callback that will run, must be a LocationResult object.
     * @param refreshTime The minimum time interval for notifications, in milliseconds. 
     * @param minAccuracy The minimum accuracy that you want to get in your location result, in meters
     * @return */
    public boolean startUpdates(Context context, LocationResult result, Integer refreshTime, Integer minAccuracy)
    {
    	mContext=context;
    	mMinAccuracy=minAccuracy.floatValue();
    	mRefreshTime=refreshTime.longValue();
        //I use LocationResult callback class to pass location value from MyLocation to user code.
        locationResult=result;
        if(mLocationManager==null)
        	mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        
        //exceptions will be thrown if provider is not permitted.
        try{gps_enabled=mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);}catch(Exception ex){}
        try{network_enabled=mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);}catch(Exception ex){}

        //don't start listeners if no provider is enabled
        if(!gps_enabled && !network_enabled)
            return false;

        if(gps_enabled)
        	mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
        if(network_enabled)
        	mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
        return true;
    }
    
    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
        	if (location.getAccuracy()<=mMinAccuracy){
	        	if (isBetterLocation(location, mCurrentBestLocation)){
	        		locationResult.gotLocation(location, LocationManager.GPS_PROVIDER);
	        	}
        	}
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
        	if (location.getAccuracy()<=mMinAccuracy){
        		if (isBetterLocation(location, mCurrentBestLocation)){
            		locationResult.gotLocation(location, LocationManager.NETWORK_PROVIDER);
            	}
        	}
        	
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };
    
    public void stopUpdates(){
    	mLocationManager.removeUpdates(locationListenerNetwork);
    	mLocationManager.removeUpdates(locationListenerGps);
    }
    
    
    private static final int TWO_MINUTES = 1000 * 60 * 2;

    /** Determines whether one Location reading is better than the current Location fix
      * @param location  The new Location that you want to evaluate
      * @param currentBestLocation  The current Location fix, to which you want to compare the new one
      */
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
        // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
          return provider2 == null;
        }
        return provider1.equals(provider2);
    }
    
    public void checkGpsStatus(LocationManager locationManager){
    	boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // Check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to 
        // go to the settings
        if (!enabled) {
          Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
          mContext.startActivity(intent);
        }
    }
    
    /* Esta el método abstracto de esta clase debe ser vacío para que 
     * se rellene la lógica en la clase desde la que se instancie 
     */
    public static abstract class LocationResult{
    	/**Called when a location is fixed
    	 * @param location The location fixed
    	 * @param source The source of the fix, can be LocationManager.NETWORK_PROVIDER or LocationManager.GPS_PROVIDER*/
        public abstract void gotLocation(Location location, String source);
    }

}
