package com.example.final_project;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Time;

public class DayFragment extends Fragment {

    private RecyclerView recyclerView;
    //private SystemAdapter systemAdapter;
    private String day;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        //Getting Data
        getDataFromDb(); //here or in the next func
        //RecyclerView Data
        showRecyclerData(); //here or in the next func

        return inflater.inflate(R.layout.fragment_day, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.rvDailySystem);
        super.onViewCreated(view, savedInstanceState);
    }

    private void getDataFromDb() {
        String data = "";
        try {
            InputStream inputStream = getContext().getAssets().open(day + ".txt");
            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int size = inputStream.available();
                char[] buffer = new char[size];
                inputStreamReader.read(buffer);
                inputStream.close();
                data = new String(buffer);
                String[] dataArray = data.split(",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showRecyclerData() {
    }


    ////////////////////////////////////////////////////////////////////////////////////////

    public void onNewClick(String day) {
        this.day = day;
        //////////////////////
    }

/*    //activity connected to my fragment
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        systemAdapter = new SystemAdapter(getActivity().getApplication(), getContext(), getActivity());
        //connection with adapter
        recyclerView.setAdapter(systemAdapter);
        //Present the information as lines and not as a grid
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }*/


    ////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if((item.getItemId()) == R.id.adding) {
            showDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {

        FragmentManager fm = getFragmentManager();

        // Create and show the dialog.
        DialogFragment newFragment = MyDialogFragment.newInstance(day); //////////////
        newFragment.show(fm, "dialog");
    }

    public static class MyDialogFragment extends DialogFragment {
        String mDay;
        DayFragment dayFragment;

        /**
         * Create a new instance of MyDialogFragment, providing "num"
         * as an argument.
         */
        static MyDialogFragment newInstance(String day) {
            MyDialogFragment f = new MyDialogFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putString("day", day); /////////////
            f.setArguments(args); /////////////
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mDay = getArguments().getString("day"); //////////
            int style = DialogFragment.STYLE_NORMAL;
            int theme = android.R.style.Theme_Holo_Light_Dialog;
            setStyle(style, theme);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.add_layout, container);
            EditText etProfession = (EditText) v.findViewById(R.id.etProfession);
            TimePicker tpStartTime = (TimePicker) v.findViewById(R.id.timePicker_Start);
            TimePicker tpEndTime = (TimePicker) v.findViewById(R.id.timePicker_End);
            EditText etLocation = (EditText) v.findViewById(R.id.etLocation); ///
            TextView tvAlert = (TextView) v.findViewById(R.id.tvAlert);
            Button btnAdd = (Button)v.findViewById(R.id.bAdd);
            tpStartTime.setIs24HourView(true);
            tpEndTime.setIs24HourView(true);

            dayFragment = (DayFragment) getFragmentManager().findFragmentByTag("DAYFRAG");

            super.onCreate(savedInstanceState);
            getDialog().setTitle("Enter the following details:");

            btnAdd.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Get time from the time picker
                    final int start_hour = tpStartTime.getHour();
                    final int start_minute = tpStartTime.getMinute();
                    final int end_hour = tpEndTime.getHour();
                    final int end_minute = tpEndTime.getMinute();

                    if(TextUtils.isEmpty(etProfession.getText().toString())){
                        tvAlert.setText("Must enter the profession!");
                        tvAlert.setVisibility(View.VISIBLE);
                    }
                    else if((start_hour > end_hour) || ((start_hour == end_hour) && (start_minute > end_minute))){
                        tvAlert.setText("End time must be after start time!");
                        tvAlert.setVisibility(View.VISIBLE);
                    }
                    else {
                        tvAlert.setVisibility(View.GONE);
                        saveDataToDB(etProfession.getText().toString(), etLocation.getText().toString(), start_hour, start_minute, end_hour, end_minute, mDay);
                        getDialog().dismiss();
                    }
                }
            });

            return v;
        }

        private void saveDataToDB(String profession, String location, int startHour,int startMinute, int endHour, int endMinute, String day) {
            String lesson = profession + "," + location + "," + startHour + "," + startMinute + "," + endHour + "," + endMinute + "\n";
            StringBuilder data = new StringBuilder();

            try {
                InputStream inputStream = getContext().openFileInput(day + ".txt");
                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    int size = inputStream.available();
                    char[] buffer = new char[size];
                    inputStreamReader.read(buffer);
                    inputStream.close();
                    String temp = new String(buffer);
                    data.append(temp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            data.append(lesson);
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getContext().openFileOutput(day + ".txt",Context.MODE_PRIVATE));
                outputStreamWriter.write(data.toString());
                outputStreamWriter.flush();
                outputStreamWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    //the interface of this fragment that include the methods
    public interface DayFragmentListener{
        //put here methods you want to utilize to communicate with the hosting activity
    }

}
