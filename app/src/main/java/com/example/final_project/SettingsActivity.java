package com.example.final_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView textView;
    int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPrefs = getSharedPreferences("FONT_SIZE", MODE_PRIVATE);

        //The size variable contains the font size
        //setTheme must be called before setContentView
        String size = sharedPrefs.getString("SIZE", "");
        if (size.equals(" small ")){
            setTheme(R.style.small_text);
            progress =0;
        }
        else if (size.equals(" medium ")) {
            setTheme(R.style.medium_text);
            progress = 1;
        }
        else if (size.equals(" large ")) {
            setTheme(R.style.large_text);
            progress =2;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        seekBar =(SeekBar) findViewById(R.id.seekBar);
        textView =(TextView) findViewById(R.id.textView_currentFont);

        //keeping on the location of thumb
        switch(progress){
            case 0:
                seekBar.setProgress(0);
                textView.setText("Small");
                break;
            case 1:
                seekBar.setProgress(1);
                textView.setText("Medium");
                break;
            case 2:
                seekBar.setProgress(2);
                textView.setText("Large");
                break;
        }


        SharedPreferences sharedPreferences = getSharedPreferences("FONT_SIZE", MODE_PRIVATE);
        //Changing and store the value in the shared preferences (font size)
        final SharedPreferences.Editor editor = sharedPreferences.edit();



        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i == 0) {
                    textView.setText("Small");
                    editor.putString("SIZE", " small ");
                    editor.apply();

                } else if (i == 1) {
                    textView.setText("Medium");
                    editor.putString("SIZE", " medium ");
                    editor.apply();
                } else if (i == 2) {
                    textView.setText("Large");
                    editor.putString("SIZE", " large ");
                    editor.apply();
                }
                startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
                SettingsActivity.this.finish();

                //recreate();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });


        //Sign out from this FireBase instance, moving to the Authentication Activity and finish
        findViewById(R.id.button_LogOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                //moving by intent to LoginFragment and close SettingsActivity
                startActivity(new Intent(SettingsActivity.this, LoginFragment.class));
                SettingsActivity.this.finish();
            }
        });

    }




}
