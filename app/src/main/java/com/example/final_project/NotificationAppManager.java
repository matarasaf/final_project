package com.example.final_project;

import static com.example.final_project.App.N_CHANNEL_ID;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;

import java.util.Calendar;

public class NotificationAppManager {

    public static void sendNotification(Context context, String message) {
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        Notification notification = notificationBuilder
                .setChannelId(N_CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentText(message)
                .build();
        notificationManager.notify(1,notification);
    }


    /*
           Sets a notification timer for a lesson
     */
    public static void setLessonNotification(Context context, Lesson lesson) {

        // transform the lesson to a strings of values of lesson
        String lessonString = new Gson().toJson(lesson);
        // intent for the alarm manager broadcast receiver
        // pass the lesson to the pending intent
        Intent intent = new Intent(context,LessonsBroadcastReceiver.class);
        intent.putExtra("lesson",lessonString);
        PendingIntent pendingIntent = PendingIntent
                .getBroadcast(context,
                0,intent,0);

        //convert string day to integer day for the using on calendar
        int day = 0;
        switch(lesson.getDay()){
            case "Sunday":
                day = 1;
                break;
            case "Monday":
                day = 2;
                break;
            case  "Tuesday":
                day = 3;
                break;
            case "Wednesday":
                day = 4;
                break;
            case "Thursday":
                day = 5;
                break;
            case "Friday":
                day = 6;
                break;
        }

        // Set Time millis for alarm manager
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.MONTH,  calendar.get(Calendar.MONTH));
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.HOUR_OF_DAY, lesson.startHour);
        calendar.set(Calendar.MINUTE, lesson.startMinute);
        calendar.set(Calendar.SECOND, 0);
        //calendar.getTimeInMillis()- method returns this Calendar's time in milliseconds.
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis() -1000*60 , pendingIntent);
    }
}