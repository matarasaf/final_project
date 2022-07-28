package com.example.final_project;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class HomeActivity extends BaseActivity implements WeekFragment.WeekFragmentListener{
    String day;
    BatteryBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        receiver = new BatteryBroadcastReceiver();
        registerReceiver(receiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        //Content View
        setContentView(R.layout.activity_home);
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
