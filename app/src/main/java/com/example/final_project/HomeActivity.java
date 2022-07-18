package com.example.final_project;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity implements WeekFragment.WeekFragmentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void OnClickEvent(String day) {
        DayFragment frag;
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragContainer, DayFragment.class,null,"DAYFRAG")
                .addToBackStack(null)
                .commit();
        getSupportFragmentManager().executePendingTransactions();

        frag = (DayFragment) getSupportFragmentManager().findFragmentByTag("DAYFRAG");

        frag.onNewClick(day);

    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }*/
}
