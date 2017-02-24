package es.flabo.tandero;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by Fou on 24/02/2017.
 */

public class Common {

    private static BluetoothAdapter bluetooth;
    private static BluetoothSocket clientSocket;
    private static LocationManager locationManager;
    private static LocationListener locationListener = new MiLocationListener();

    static {
        Common.bluetooth = BluetoothAdapter.getDefaultAdapter();
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
}
