package es.flabo.tandero;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Record extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter filter = new IntentFilter();

        filter.addAction(Common.APP_NAME);

        BroadcastReceiver updateUIReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Double message = intent.getDoubleExtra(Common.KEY_GPS_UPDATE,Double.MIN_VALUE);
                TextView visualSpeed = (TextView) findViewById(R.id.visualSpeed);
                visualSpeed.setText(message+" km/h");
//                Log.d("receiver", "Got message: " + message);
            }
        };
        registerReceiver(updateUIReceiver,filter);

        setContentView(R.layout.activity_record);
    }
}
