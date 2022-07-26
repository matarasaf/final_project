package com.example.final_project;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DayFragment extends Fragment {

    RecyclerView recyclerView;
    SystemAdapter systemAdapter;

    String day;
    AlertDialog addDialog = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        HomeActivity activity = (HomeActivity) getActivity();
        day = activity.getDay();
        return inflater.inflate(R.layout.fragment_day, container, false);
    }

    public void onNewClick(String day) {
        this.day = day;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.rvDailySystem);
        systemAdapter = new SystemAdapter(getActivity().getApplication(), getContext(), getActivity(), day);
        //connection with adapter
        recyclerView.setAdapter(systemAdapter);
        //Present the information as lines and not as a grid
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.system, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ((item.getItemId()) == R.id.adding) {
            showDialog();
            return true;
        }
        if(item.getItemId() == R.id.homeScreen) {

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragContainer, new WeekFragment())
                    .commit();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (this).getLayoutInflater();
        View view = inflater.inflate(R.layout.add_layout, null);

        //Setting all the views
        EditText etProfession = (EditText) view.findViewById(R.id.etProfession);
        TimePicker tpStartTime = (TimePicker) view.findViewById(R.id.timePicker_Start);
        TimePicker tpEndTime = (TimePicker) view.findViewById(R.id.timePicker_End);
        EditText etLocation = (EditText) view.findViewById(R.id.etLocation);
        TextView tvAlert = (TextView) view.findViewById(R.id.tvAlert);
        Button btnAdd = (Button) view.findViewById(R.id.bAdd);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox) ;
        tpStartTime.setIs24HourView(true);
        tpEndTime.setIs24HourView(true);

        builder.setView(view);
        addDialog = builder.create();
        addDialog.setTitle("Enter the following details:");

        addDialog.show();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get time from the time picker
                final int start_hour = tpStartTime.getHour();
                final int start_minute = tpStartTime.getMinute();
                final int end_hour = tpEndTime.getHour();
                final int end_minute = tpEndTime.getMinute();
                boolean attendance;

                if(checkBox.isChecked())
                    attendance = true;
                else
                    attendance = false;

                if (TextUtils.isEmpty(etProfession.getText().toString())) {
                    tvAlert.setText("Must enter the profession!");
                    tvAlert.setVisibility(View.VISIBLE);
                } else if ((start_hour > end_hour) || ((start_hour == end_hour) && (start_minute > end_minute))) {
                    tvAlert.setText("End time must be after start time!");
                    tvAlert.setVisibility(View.VISIBLE);
                } else {
                    tvAlert.setVisibility(View.GONE);
                    MainViewModel myViewModel = MainViewModel.getInstance(getActivity().getApplication());
                    Lesson newLesson = new Lesson(etProfession.getText().toString(),etLocation.getText().toString(), start_hour,start_minute,end_hour,end_minute, day, attendance);
                    myViewModel.addNewLesson(getContext(),newLesson);

                    addDialog.dismiss();
                }
            }
        });
    }

    //the interface of this fragment that include the methods
    public interface DayFragmentListener{
        //put here methods you want to utilize to communicate with the hosting activity
    }
}
