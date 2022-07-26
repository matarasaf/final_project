package com.example.final_project;

import android.app.AlarmManager;
import android.app.AliasActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends BaseActivity implements WeekFragment.WeekFragmentListener{
    String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Content View
        setContentView(R.layout.activity_home);
    }


    @Override
    public void OnClickEvent(String day) {
        DayFragment frag;
        this.day = day;
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragContainer, DayFragment.class,null,"DAYFRAG")
                .addToBackStack(null)
                .commit();
        try {
            getSupportFragmentManager().executePendingTransactions();
        }catch (IndexOutOfBoundsException e) { e.printStackTrace();}
        frag = (DayFragment) getSupportFragmentManager().findFragmentByTag("DAYFRAG");
        frag.onNewClick(day);
    }


    public String getDay(){
        return day;
    }

}
