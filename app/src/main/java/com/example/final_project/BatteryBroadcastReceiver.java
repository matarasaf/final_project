package com.example.final_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

public class BatteryBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int percentage = intent.getIntExtra("level", 0);

        if(percentage != 0){
            Toast.makeText(context,percentage + "%" , Toast.LENGTH_LONG).show();
        }


/*
        Toast.makeText(context, "Low battery!\n" + "Check if there are lessons with compulsory attendance coming soon.", Toast.LENGTH_LONG).show();
*/
    }
}
