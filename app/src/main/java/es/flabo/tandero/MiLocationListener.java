package es.flabo.tandero;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Fou on 24/02/2017.
 */

public class MiLocationListener implements LocationListener{

    public static boolean active=false;
    private Context context;

    public MiLocationListener(Context context){
        this.context=context;
    }

    public void onLocationChanged(Location loc){
        MiLocationListener.active=true;
        String data = "Vel="+loc.getSpeed()+" - GPS Latitud = " + loc.getLatitude() + " Longitud = " + loc.getLongitude();
        Log.d("onLocationChanged",data);

        Intent local = new Intent().setAction(Common.APP_NAME);
        local.putExtra(Common.KEY_GPS_UPDATE, loc.getLatitude());

        this.context.sendBroadcast(local);
    }
    public void onProviderDisabled(String provider){
        Log.d("onProviderDisabled","GPS apagado");
        MiLocationListener.active=false;

    }
    public void onProviderEnabled(String provider){
        Log.d("onProviderEnabled","GPS encendido");
        MiLocationListener.active=true;
    }
    public void onStatusChanged(String provider, int status, Bundle extras){
        Log.d("onStatusChanged","GPS status changed");
    }
}