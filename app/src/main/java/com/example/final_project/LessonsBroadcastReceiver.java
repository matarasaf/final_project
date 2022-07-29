package com.example.final_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

public class LessonsBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //putExtra- allows us to save inside the Intent our information
        String lessonString = intent.getStringExtra("lesson");
        Lesson lesson = new Gson().fromJson(lessonString,Lesson.class);
/*        ArrayList<String> dataList = FileManager.getDataFromDb(context, lesson.getDay());
        ArrayList<Lesson> lessonsList = FileManager.getLessonsFromDb(dataList, lesson.getDay(),true);*/
        //choose the right day
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        //lesson needs to meet conditions
        if((c.get(Calendar.DAY_OF_WEEK) != lesson.getDayNum()) )
            return;

/*
        if(lessonsList.contains(lesson))
*/
            NotificationAppManager.sendNotification(context,
                "A reminder for a lesson with compulsory attendance.\n Check the system.");
    }
}