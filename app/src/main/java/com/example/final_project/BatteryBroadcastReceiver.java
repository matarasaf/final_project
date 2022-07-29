package com.example.final_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BatteryBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int percentage = intent.getIntExtra("level", 0);

        if(percentage != 0){
            Toast.makeText(context,"Battery percentage: " + percentage + "%" , Toast.LENGTH_LONG).show();
        }
    }
}