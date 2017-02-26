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

    public static final String UUID = "00001101-0000-1000-8000-00805F9B34FB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Common.isBtConnected()){
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
                    connectToBT(device);
                }
            }
        }
    }

    private void connectToBT(BluetoothDevice device) {
        Log.d("connectToBT", "Connecting to "+device.getName());
        try {
            if (Common.getClientSocket()==null) {
                Log.d("connectToBT", "Creating socket");
                Common.setClientSocket(device.createInsecureRfcommSocketToServiceRecord(java.util.UUID.fromString(UUID)));
                //clientSocket = device.createRfcommSocketToServiceRecord(uuid);
            }

            if (!Common.getClientSocket().isConnected()) {
                Log.d("connectToBT", "Conecting");
                Common.getClientSocket().connect();
            }

            if (Common.getClientSocket().isConnected()) {
                Common.setBtConnected(true);
                Log.d("connectToBT", "Is connected, reading ");
                byte[] buffer = new byte[8];  // buffer store for the stream
                int bytesReaded; // bytesReaded returned from read()

                // Keep listening to the InputStream until an exception occurs
                InputStream mmInStream = Common.getClientSocket().getInputStream();
                while (true) {
                    try {
                        // Read from the InputStream
                        //vcc#resistance_reference_VDO_temp#raw_VDO_temp#resistance_reference_VDO_press#raw_VDO_press
                        bytesReaded = mmInStream.read(buffer);
                        String readed=new String(buffer);
                        Log.d("connectToBT", "Readed "+bytesReaded+" :"+readed);
                    } catch (IOException e) {
                        break;
                    }
                }
            }else{
                Common.setBtConnected(false);
                Log.d("connectToBT", "Not Connected");
            }
        } catch (Exception ex) {
            Common.setBtConnected(false);
            ex.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            if (Common.getClientSocket()!=null) {
                Common.getClientSocket().close();
                Common.setClientSocket(null);
            }
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
