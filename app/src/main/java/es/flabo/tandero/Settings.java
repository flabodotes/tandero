package es.flabo.tandero;

import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

    private void getSynchronizedDevices(){
        System.out.println("getSynchronizedDevices");
        Set<BluetoothDevice> pairedDevices = Common.getBluetooth().getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                System.out.println(device.getName() + " - " + device.getAddress());
                if (device.getName().contains("HC-06")){
                    connectToBT(device);
                }
            }
        }
    }

    private void connectToBT(BluetoothDevice device) {
        System.out.println("connectToBT");
        try {
            //UUID uuid = UUID.fromString("f05e00cf-3934-4ac5-afed-007c971ad9bc");//Random uuid
            //UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");//HC-06
            Common.setClientSocket(device.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")));
            //clientSocket = device.createRfcommSocketToServiceRecord(uuid);
            Common.getClientSocket().connect();

            if (Common.getClientSocket().isConnected()) {
                System.out.println("Connected");
                byte[] buffer = new byte[1024];  // buffer store for the stream
                int bytes; // bytes returned from read()

                // Keep listening to the InputStream until an exception occurs
                InputStream mmInStream = Common.getClientSocket().getInputStream();
                while (true) {
                    try {
                        // Read from the InputStream
                        bytes = mmInStream.read(buffer);
                        System.out.println("-" + bytes);
                    } catch (IOException e) {
                        break;
                    }
                }
            }else{
                System.out.println("Not connected");
            }
        } catch (Exception ex) {
            System.out.println("Exception: "+ex);
            ex.printStackTrace();
        }
    }
}
