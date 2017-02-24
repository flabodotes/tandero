package es.flabo.tandero;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by Fou on 24/02/2017.
 */

public class MiLocationListener implements LocationListener{

    public static boolean active=false;

    public void onLocationChanged(Location loc){
        MiLocationListener.active=true;
        loc.getLatitude();
        loc.getLongitude();
        String coordenadas = this.toString()+" - GPS Latitud = " + loc.getLatitude() + " Longitud = " + loc.getLongitude();
        System.out.println(coordenadas);
    }
    public void onProviderDisabled(String provider){
        System.out.println("GPS apagado");
        MiLocationListener.active=false;
    }
    public void onProviderEnabled(String provider){
        System.out.println("GPS activado");
        MiLocationListener.active=true;
    }
    public void onStatusChanged(String provider, int status, Bundle extras){
        System.out.println("GPS status changed");
    }
}