package com.example.broacastreceiverex2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class SmsReceiver extends BroadcastReceiver {
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault());

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            Object[] arrMessages = (Object[]) bundle.get("pdus");
            if (arrMessages != null){
                String content, phone, time;
                long currentTimeStamp;
                byte[] data;
                for (Object msgObj: arrMessages){
                    data = (byte[]) msgObj;
                    SmsMessage message = SmsMessage.createFromPdu(data);
                    content = message.getDisplayMessageBody();
                    phone = message.getDisplayOriginatingAddress();
                    currentTimeStamp = message.getTimestampMillis();
                    time = dateFormat.format(currentTimeStamp);
                    Toast.makeText(context, "Phone: " + phone + "\nTime: " + time + "\nContent" + content, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
