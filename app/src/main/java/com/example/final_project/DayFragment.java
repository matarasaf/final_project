package com.example.final_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DayFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       /* SharedPreferences sharedPreferences = getActivity().getSharedPreferences("FONT_SIZE", Context.MODE_PRIVATE);
        //If size not declare initialize to empty
        String size = sharedPreferences.getString("SIZE", "");
        if (size.equals(" small "))
            getContext().setTheme(R.style.small_text);
        else if (size.equals(" medium "))
            getContext().setTheme(R.style.medium_text);
        else if (size.equals(" large "))
            getContext().setTheme(R.style.large_text);*/


        return inflater.inflate(R.layout.fragment_day, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

    public void onNewClick(String day) {

    }
}
