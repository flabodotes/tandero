package es.flabo.tandero;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume");

        //Bluetooth status
        TextView bluetoothStatus = (TextView) findViewById(R.id.bluetoothStatus);
        if (Common.getBluetooth() != null) {
            if (!Common.getBluetooth().isEnabled()) {
                bluetoothStatus.setText("OFF");
            } else {
                bluetoothStatus.setText("ON");
            }
        } else {
            bluetoothStatus.setText("BT no compatible");
        }

        //GPS status
        TextView gpsStatus = (TextView) findViewById(R.id.gpsStatus);
        if (MiLocationListener.active) {
            gpsStatus.setText("ON");
        } else {
            gpsStatus.setText("OFF");
        }

        //Internet Status
        NetworkInfo networkInfo = Common.getConnectivityManager().getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo.isConnected();
        networkInfo = Common.getConnectivityManager().getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();
        Log.d("NetworkStatusExample", "Wifi connected: " + isWifiConn);
        Log.d("NetworkStatusExample", "Mobile connected: " + isMobileConn);

        TextView netStatus = (TextView) findViewById(R.id.internetStatus);
        if (isMobileConn || isWifiConn){
            netStatus.setText("ON");
        }else{
            netStatus.setText("OFF");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate");

        Common.setLocationManager((LocationManager) getSystemService(Context.LOCATION_SERVICE));

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
        } else {
            Common.getLocationManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, Common.getLocationListener());
        }

        Common.setConnectivityManager((ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE));

        setContentView(R.layout.activity_main);
    }

    public void goToRecord(View view) {
        Intent intent = new Intent(this, Record.class);
        startActivity(intent);
    }

    public void goToSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void goToHistory(View view) {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
//        try {
//            if ((Common.getBluetooth() != null) && Common.getBluetooth().isEnabled() && Common.getClientSocket()!=null) {
//                    Common.getClientSocket().close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }else {
            System.out.println("GPS removed listener");
            Common.getLocationManager().removeUpdates(Common.getLocationListener());
        }

        super.onStop();
    }
}
