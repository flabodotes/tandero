package es.flabo.tandero;

import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

public class Settings extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //If is not connected and is not trying it
        if (!Common.isBtConnected() && Common.getBtReader()==null){
            getSynchronizedDevices();
        }
    }

    private void getSynchronizedDevices(){
        Log.d("getSynchronizedDevices", "Searching paired devices.");
        Set<BluetoothDevice> pairedDevices = Common.getBluetooth().getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                Log.d("getSynchronizedDevices", "Devices:"+device.getName() + " - " + device.getAddress());
                if (device.getName().contains("HC-06")){
                    BTReader bluetoothReader = new BTReader(device);
                    Common.setBtReader(bluetoothReader);
                    bluetoothReader.start();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
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

        super.onDestroy();
    }
}
