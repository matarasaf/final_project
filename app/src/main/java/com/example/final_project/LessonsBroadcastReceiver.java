package com.example.final_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

public class LessonsBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //putExtra- allows us to save inside the Intent our information
        String lessonString = intent.getStringExtra("lesson");
        Lesson lesson = new Gson().fromJson(lessonString,Lesson.class);
        NotificationAppManager.sendNotification(context,
                "This is a reminder for a lesson" + lesson.getProfession() + "today at " + lesson.getStartHour());
    }
}
