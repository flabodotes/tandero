package es.flabo.tandero;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;

/**
 * Created by Fou on 24/02/2017.
 */

public class Common {

    public static String APP_NAME="es.flabo.tandero";
    public static String KEY_GPS_UPDATE="UPDATE_GPS";

    private static BTReader btReader=null;
    private static BluetoothAdapter bluetooth;
    private static BluetoothSocket clientSocket=null;
    private static boolean btConnected;
    private static LocationManager locationManager=null;
    private static LocationListener locationListener = null;
    private static ConnectivityManager connectivityManager=null;

    static {
        Common.bluetooth = BluetoothAdapter.getDefaultAdapter();
    }

    public static boolean isBtConnected() {
        return btConnected;
    }

    public static void setBtConnected(boolean btConnected) {
        Common.btConnected = btConnected;
    }

    public static BluetoothAdapter getBluetooth() {
        return bluetooth;
    }

    public static void setBluetooth(BluetoothAdapter bluetooth) {
        Common.bluetooth = bluetooth;
    }

    public static BluetoothSocket getClientSocket() {
        return clientSocket;
    }

    public static void setClientSocket(BluetoothSocket clientSocket) {
        Common.clientSocket = clientSocket;
    }

    public static LocationManager getLocationManager() {
        return locationManager;
    }

    public static void setLocationManager(LocationManager locationManager) {
        Common.locationManager = locationManager;
    }

    public static LocationListener getLocationListener() {
        return locationListener;
    }

    public static void setLocationListener(LocationListener locationListener) {
        Common.locationListener = locationListener;
    }

    public static ConnectivityManager getConnectivityManager() {
        return connectivityManager;
    }

    public static void setConnectivityManager(ConnectivityManager connectivityManager) {
        Common.connectivityManager = connectivityManager;
    }

    public static BTReader getBtReader() {
        return btReader;
    }

    public static void setBtReader(BTReader btReader) {
        Common.btReader = btReader;
    }
}
