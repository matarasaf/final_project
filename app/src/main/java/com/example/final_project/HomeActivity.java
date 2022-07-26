package com.example.final_project;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity implements WeekFragment.WeekFragmentListener{

    BatteryBroadcastReceiver receiver;

    public static final String BATTERY_LOW = "android.intent.action.ACTION_BATTERY_LOW";

    String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        receiver = new BatteryBroadcastReceiver();
        registerReceiver(receiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED)); ////////////////

        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }
 /*   @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(BatteryManager.EXTRA_BATTERY_LOW);
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        this.registerReceiver(receiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
*/

    @Override
    public void OnClickEvent(String day) {
        DayFragment frag;
        this.day = day;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragContainer, DayFragment.class,null,"DAYFRAG")
                .commit();
        getSupportFragmentManager().executePendingTransactions();

        frag = (DayFragment) getSupportFragmentManager().findFragmentByTag("DAYFRAG");

        frag.onNewClick(day);

    }

    public String getDay(){
        return day;
    }

}
