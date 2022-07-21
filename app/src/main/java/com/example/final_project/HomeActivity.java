package com.example.final_project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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

}
