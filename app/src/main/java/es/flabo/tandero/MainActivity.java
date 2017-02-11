package es.flabo.tandero;

import android.bluetooth.BluetoothAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView bluetoothStatus=(TextView)findViewById(R.id.bluetoothStatus);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            if (!mBluetoothAdapter.isEnabled()) {
                bluetoothStatus.setText("OFF");
            }else{
                bluetoothStatus.setText("ON");
            }
        }
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

}
