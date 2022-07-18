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
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class DayFragment extends Fragment {

    private RecyclerView recyclerView;
    //private SystemAdapter systemAdapter;
    private String day;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_day, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.rvDailySystem);
        super.onViewCreated(view, savedInstanceState);
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
        String profession;
        String location;
        String startTime;
        String endTime;
        String mDay; ////////

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
            EditText etStartTime = (EditText) v.findViewById(R.id.etStartTime);
            EditText etEndTime = (EditText) v.findViewById(R.id.etEndTime);
            EditText etLocation = (EditText) v.findViewById(R.id.etLocation); ///
            TextView tvAlert = (TextView) v.findViewById(R.id.tvAlert);
            Button btnAdd = (Button)v.findViewById(R.id.bAdd);

            dayFragment = (DayFragment) getFragmentManager().findFragmentByTag("DAYFRAG");

            super.onCreate(savedInstanceState);
            getDialog().setTitle("Enter the following details:");

            btnAdd.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(TextUtils.isEmpty(etProfession.getText().toString())){
                        tvAlert.setText("Must enter the profession!");
                        tvAlert.setVisibility(View.VISIBLE);
                    }
                    else if(TextUtils.isEmpty(etStartTime.getText().toString())){
                        tvAlert.setText("Must enter the start time!");
                        tvAlert.setVisibility(View.VISIBLE);

                    }
                    else if(TextUtils.isEmpty(etEndTime.getText().toString())){
                        tvAlert.setText("Must enter the end time!");
                        tvAlert.setVisibility(View.VISIBLE);
                    }
                    else
                        tvAlert.setVisibility(View.GONE);
                }
            });

            return v;
        }
    }



    //the interface of this fragment that include the methods
    public interface DayFragmentListener{
        //put here methods you want to utilize to communicate with the hosting activity
    }

}
