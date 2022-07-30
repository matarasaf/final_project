package com.example.final_project;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class HomeActivity extends BaseActivity implements WeekFragment.WeekFragmentListener{
    String day;
    BatteryBroadcastReceiver receiver = new BatteryBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Content View
        setContentView(R.layout.activity_home);
    }

    @Override
    public void onResume() {
        super.onResume();

        registerReceiver(receiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

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