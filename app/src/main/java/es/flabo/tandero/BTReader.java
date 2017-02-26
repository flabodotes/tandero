package es.flabo.tandero;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Fou on 26/02/2017.
 */

public class BTReader extends Thread {

    public static final String UUID = "00001101-0000-1000-8000-00805F9B34FB";
    private BluetoothDevice device;

    public BTReader(BluetoothDevice device){
        this.device=device;
    }

    @Override
    public void run() {
        try {
            while (Common.getClientSocket()==null || !Common.getClientSocket().isConnected()){
                Log.d("BTReader", "Connecting... ");
                this.connectToBT();
                Thread.currentThread().sleep(2000);
            }

            if (Common.getClientSocket().isConnected()) {
                Common.setBtConnected(true);
                Log.d("BTReader", "Is connected, reading ");
                byte[] buffer = new byte[1024];  // buffer store for the stream
                int bytesReaded; // bytesReaded returned from read()

                // Keep listening to the InputStream until an exception occurs
                InputStream mmInStream = Common.getClientSocket().getInputStream();
                String temp = "";
                while (Common.getClientSocket().isConnected()) {
                    // Read from the InputStream
                    //vcc#resistance_reference_VDO_temp#raw_VDO_temp#resistance_reference_VDO_press#raw_VDO_press
                    bytesReaded = mmInStream.read(buffer);
                    String strReceived = new String(buffer, 0, bytesReaded);

                    temp += strReceived;
                    //Log.d("BTReader", "Temp:" + temp);
                    if (temp.contains("%")) {
                        int pos = temp.indexOf("%");
                        String valid = temp.substring(0, pos);
                        Log.d("BTReader", valid);
                        temp = temp.substring(pos + 1);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("BTReader", "END");
    }

    private void connectToBT() {
        Log.d("connectToBT", "Connecting to "+this.device.getName());
        try {
            if (Common.getClientSocket()==null) {
                Log.d("connectToBT", "Creating socket");
                Common.setClientSocket(this.device.createInsecureRfcommSocketToServiceRecord(java.util.UUID.fromString(UUID)));
            }

            if (Common.getClientSocket()!=null && !Common.getClientSocket().isConnected()) {
                Log.d("connectToBT", "Conecting");
                Common.getClientSocket().connect();
            }else{
                Common.setBtConnected(false);
                Log.d("connectToBT", "Not Connected");
            }
        } catch (Exception ex) {
            Common.setBtConnected(false);
            ex.printStackTrace();
        }
    }
}
