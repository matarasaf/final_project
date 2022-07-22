package com.example.final_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.View.OnClickListener;


public class WeekFragment extends Fragment implements OnClickListener {
    Button bSunday, bMonday, bTuesday, bWednesday, bThursday, bFriday;
    WeekFragmentListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        try{
            this.listener = (WeekFragmentListener)context;
        }catch(ClassCastException e){
            throw new ClassCastException("the class " +
                    context.getClass().getName() +
                    " must implements the interface 'WeekFragmentListener'");
        }
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

      /*  SharedPreferences sharedPreferences = getActivity().getSharedPreferences("FONT_SIZE", Context.MODE_PRIVATE);
        //If size not declare initialize to empty
        String size = sharedPreferences.getString("SIZE", "");
        if (size.equals(" small "))
            getContext().setTheme(R.style.small_text);
        else if (size.equals(" medium "))
            getContext().setTheme(R.style.medium_text);
        else if (size.equals(" large "))
            getContext().setTheme(R.style.large_text);*/


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        bSunday = (Button) view.findViewById(R.id.bSunday);
        bMonday = (Button) view.findViewById(R.id.bMonday);
        bTuesday = (Button) view.findViewById(R.id.bTuesday);
        bWednesday = (Button) view.findViewById(R.id.bWednesday);
        bThursday = (Button) view.findViewById(R.id.bThursday);
        bFriday = (Button) view.findViewById(R.id.bFriday);

        bSunday.setOnClickListener(this);
        bMonday.setOnClickListener(this);
        bTuesday.setOnClickListener(this);
        bWednesday.setOnClickListener(this);
        bThursday.setOnClickListener(this);
        bFriday.setOnClickListener(this);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        String day = "";

        switch(view.getId()){
            case R.id.bSunday:
                day = "Sunday";
                break;
            case R.id.bMonday:
                day = "Monday";
                break;
            case R.id.bTuesday:
                day = "Tuesday";
                break;
            case R.id.bWednesday:
                day = "Wednesday";
                break;
            case R.id.bThursday:
                day = "Thursday";
                break;
            case R.id.bFriday:
                day = "Friday";
                break;
        }
        listener.OnClickEvent(day);
    }

    public interface WeekFragmentListener{
        public void OnClickEvent(String day);
    }
}
