package com.example.final_project;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

abstract public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPrefs = getSharedPreferences("FONT_SIZE", MODE_PRIVATE);

        //The size variable contains the font size
        //setTheme must be called before setContentView
        String size = sharedPrefs.getString("SIZE", "");
        if (size.equals(" small "))
            setTheme(R.style.small_text);
        else if (size.equals(" medium "))
            setTheme(R.style.medium_text);
        else if (size.equals(" large "))
            setTheme(R.style.large_text);
    }

    public static class MyAlertDialogFragment extends DialogFragment {

        public static MainActivity.MyAlertDialogFragment newInstance() {
            MainActivity.MyAlertDialogFragment frag = new MainActivity
                    .MyAlertDialogFragment();
            Bundle args = new Bundle();
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            return new AlertDialog.Builder(getActivity())
                    .setIcon(R.drawable.ic_launcher)
                    .setTitle("Closing the application")
                    .setMessage("Are you sure? ")
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    BaseActivity.doPositiveClick();
                                }
                            }
                    )
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    BaseActivity.doNegativeClick(dialog);
                                }
                            }
                    )
                    .create();
        }
    }

    public static void doPositiveClick() {
        System.exit(0);
    }

    public static void doNegativeClick(DialogInterface dialog) {
        dialog.dismiss();
    }

    private void showDialog() {
        DialogFragment newFragment = BaseActivity.MyAlertDialogFragment.newInstance();
        newFragment.show(getSupportFragmentManager(),"dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return true;
    }

    //Menu buttons options implementation
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                startActivity(new Intent(this,SettingsActivity.class));
                return true;
            case R.id.action_exit:
                showDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //update settings- change font size
    //overridePendingTransition call immediately after finish()/startActivity() function
    //      for attention transit between activity
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

}