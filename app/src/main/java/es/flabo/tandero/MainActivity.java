package es.flabo.tandero;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetooth;

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume");

        TextView bluetoothStatus=(TextView)findViewById(R.id.bluetoothStatus);
        if (bluetooth != null) {
            if (!bluetooth.isEnabled()) {
                bluetoothStatus.setText("OFF");
            }else{
                String mydevicename = bluetooth.getName();
                bluetoothStatus.setText("ON - "+mydevicename);
                getSynchronizedDevices();
            }
        }else{
            bluetoothStatus.setText("BT no disponible");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate");
        setContentView(R.layout.activity_main);
        bluetooth = BluetoothAdapter.getDefaultAdapter();
    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }*/

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


    private void getSynchronizedDevices(){
        System.out.println("getSynchronizedDevices");
        Set<BluetoothDevice> pairedDevices = bluetooth.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                System.out.println(device.getName() + " - " + device.getAddress());
            }
        }
    }

    private void connectToBT(BluetoothDevice device) {
        try {
//            UUID uuid = UUID.fromString("f05e00cf-3934-4ac5-afed-007c971ad9bc");
//            BluetoothServerSocket socket = bluetooth.listenUsingInsecureRfcommWithServiceRecord("Tandero", uuid);

        } catch (Exception ex) {

        }
    }
}
