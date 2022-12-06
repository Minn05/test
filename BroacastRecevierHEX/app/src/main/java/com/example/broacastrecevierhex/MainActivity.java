package com.example.broacastrecevierhex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView imvNetWorkState;
    TextView txtNetWorkState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkViews();
    }
    private  void linkViews(){
        imvNetWorkState = findViewById(R.id.invNetWorkState);
        txtNetWorkState = findViewById(R.id.txtNetWorkState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myReceiver, filter);
    }
    BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if(info != null && info.isConnected()){
                imvNetWorkState.setImageResource(R.drawable.ic_baseline_wifi_24);
                txtNetWorkState.setText("Connected Wifi");

            }else if(info.getType() == ConnectivityManager.TYPE_MOBILE){
                imvNetWorkState.setImageResource(R.drawable.ic_baseline_4g_mobiledata_24);
                txtNetWorkState.setText("Connected via Mobile Data");
            }
            else{
                imvNetWorkState.setImageResource(R.drawable.ic_baseline_not_interested_24);
                txtNetWorkState.setText("No connection");
            }

        }
    };


}