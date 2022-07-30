package com.example.final_project;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class NotificationsService extends Service {

    public static final String[] daysInWeek = {
            "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday"
    };

    public static final String N_CHANNEL_ID = "MY_CHANNEL";
    private void createNotificationChannel() {
        CharSequence name = "MY_CHANNEL";
        String description = "Some channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(N_CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    // The service is being created- creating channel
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }


    // The service is starting, due to a call to startService() in MainActinity.onCreate
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setLessonsAlarms();
        return super.onStartCommand(intent, flags, startId);
    }

    //start the service on the existing classes
    private void setLessonsAlarms(){
        // for each day of week set lesson alarms
        for(String day  : daysInWeek) {
            ArrayList<String> lessonData = FileManager.getDataFromDb(getApplicationContext(),day);
            //getLessonsFromDb get false- not adding lesson to class list
            ArrayList<Lesson> lessons = FileManager.getLessonsFromDb(lessonData,day,false);
            for(Lesson lesson : lessons) {
                if(lesson.getAttendance())
                    NotificationAppManager.setLessonNotification(getApplicationContext(), lesson);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}