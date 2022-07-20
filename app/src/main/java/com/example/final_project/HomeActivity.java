package com.example.final_project;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class HomeActivity extends AppCompatActivity implements WeekFragment.WeekFragmentListener{

    String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        getSupportFragmentManager().executePendingTransactions();

        frag = (DayFragment) getSupportFragmentManager().findFragmentByTag("DAYFRAG");

        frag.onNewClick(day);

    }

    public String getDay(){
        return day;
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }*/
}
