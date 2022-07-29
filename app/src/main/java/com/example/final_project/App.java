package com.example.final_project;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

//class app execute before any other class when the process for your application/package is created
public class App extends Application {
    public static final String N_CHANNEL_ID = "MY_CHANNEL";
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

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
}
