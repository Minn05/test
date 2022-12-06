package com.example.multithreadexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText edtNumOfViews;
    Button btnDraw;
    TextView txtPercent;
    ProgressBar pbPercent;
    LinearLayout layoutContainer;

    Random random = new Random();
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200,LinearLayout.LayoutParams.WRAP_CONTENT);

    //Main Thread / UO Thread
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            int percent = message.arg1;
            int randomNum = (int) message.obj;
            if (percent == 100){
                Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
            }else {
                ImageView imv = new ImageView(MainActivity.this);
                imv.setLayoutParams(params);
                if (randomNum % 2 == 0){
                    imv.setImageResource(R.drawable.ic_baseline_check_circle_24);
                }else {
                    imv.setImageResource(R.drawable.ic_baseline_control_point_24);
                }
                layoutContainer.addView(imv);
            }
            txtPercent.setText(String.valueOf(percent)+"%");
            pbPercent.setProgress(percent);
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkViews();
        addEvents();
    }
    private void linkViews(){
        edtNumOfViews = findViewById(R.id.edtNumOfViews);
        btnDraw = findViewById(R.id.btnDraw);
        txtPercent = findViewById(R.id.txtPercent);
        pbPercent = findViewById(R.id.pbPercent);
        layoutContainer = findViewById(R.id.layoutContainer);
    }
    private void addEvents(){
        btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runBackgroundThread();
            }
        });
    }
    private void runBackgroundThread(){
        int numb = Integer.parseInt(edtNumOfViews.getText().toString());
        layoutContainer.removeAllViews();
        Thread backgroundTheard = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= numb ; i++){
                    Message message = handler.obtainMessage();
                    message.arg1 = i*100/numb;
                    message.obj = random.nextInt(100); //random number
                    handler.sendMessage(message);
                    SystemClock.sleep(100);
                }
            }
        });
        backgroundTheard.start();
    }
}