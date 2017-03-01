package es.flabo.tandero;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();

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
        if (isMobileConn || isWifiConn) {
            netStatus.setText("ON");
        } else {
            netStatus.setText("OFF");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //INIT all the services and connections

        //GPS
        if (Common.getLocationListener() == null) {
            Log.d("onCreate", "Create location listener");
            Common.setLocationListener(new MiLocationListener(this));
        }

        if (Common.getLocationManager() == null) {
            Log.d("onCreate", "Init location manager");
            Common.setLocationManager((LocationManager) getSystemService(Context.LOCATION_SERVICE));

            //Check permissions and register listener
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                return;
            }
            Common.getLocationManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, Common.getLocationListener());
        }


        //Internet
        if (Common.getConnectivityManager()==null) {
            Common.setConnectivityManager((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));
        }

        //Bluetooth

        //If is not connected and is not trying it
        if (!Common.isBtConnected() && Common.getBtReader()==null){
            connectToArduino();
        }

        IntentFilter filter = new IntentFilter();

        filter.addAction(Common.APP_NAME);

        BroadcastReceiver updateUIReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra(Common.KEY_GPS_UPDATE);
                Log.d("receiver", "Got message: " + message);
            }
        };
        registerReceiver(updateUIReceiver,filter);

        //Display view
        setContentView(R.layout.activity_main);
    }


    private void connectToArduino(){
        Log.d("connectToArduino", "Searching paired devices.");
        Set<BluetoothDevice> pairedDevices = Common.getBluetooth().getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                Log.d("connectToArduino", "Devices:"+device.getName() + " - " + device.getAddress());
                if (device.getName().contains("HC-06")){
                    BTReader bluetoothReader = new BTReader(device);
                    Common.setBtReader(bluetoothReader);
                    bluetoothReader.start();
                    break;
                }
            }
        }
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

        try {
//            if (Common.getClientSocket()!=null) {
//                Common.getClientSocket().close();
//                Common.setClientSocket(null);
//            }
//            if(Common.getBluetooth()!=null) {
//                Common.getBluetooth().disable();
//                Common.setBluetooth(null);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }else {
            System.out.println("GPS removed listener");
            Common.getLocationManager().removeUpdates(Common.getLocationListener());
        }

        super.onStop();
    }
}
