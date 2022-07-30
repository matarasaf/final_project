package com.example.final_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

import java.util.Calendar;

//BroadcastReceiver listening to intent
public class LessonsBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //getExtra-get the information about the class we need
        //Gson()- convert from string to lesson object

        String lessonString = intent.getStringExtra("lesson");
        Lesson lesson = new Gson().fromJson(lessonString,Lesson.class);




        //choose the right day
        Calendar c =Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        //lesson needs to meet conditions
        if((c.get(Calendar.DAY_OF_WEEK) != lesson.getDayNum()) )
            return;


        NotificationAppManager.sendNotification(context,
                "This is a reminder for a lesson " + lesson.getProfession() + " today at " + lesson.getStartHour()
                    +":" + lesson.getStartMinute());
    }
}
