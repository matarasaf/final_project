package com.example.final_project;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

public class FileManager {
    public static ArrayList<String> getDataFromDb(Context context, String day) {
        ArrayList<String> list = new ArrayList<>();
        String data = "";
        try {
            InputStream inputStream = context.openFileInput(day + ".txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int size = inputStream.available();
                char[] buffer = new char[size];
                inputStreamReader.read(buffer);
                inputStream.close();
                data = new String(buffer);
                String[] dataArray = data.split("\n");
                for (int i = 0; i < dataArray.length; i++) {
                    list.add(dataArray[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static ArrayList<Lesson> getLessonsFromDb(ArrayList<String> dataList,String day,boolean includeOld) {

        ArrayList<Lesson> list = new ArrayList<>();
        String[] temp = new String[7];

        //for notification
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        //

        if(dataList != null) {
            for (int i = 0; i < dataList.size(); i++) {
                temp = dataList.get(i).split(",");
                Lesson lesson = new Lesson(temp[0], temp[1], Integer.valueOf(temp[2]), Integer.valueOf(temp[3]), Integer.valueOf(temp[4]), Integer.valueOf(temp[5]), day, Boolean.valueOf(temp[6]));
                ///for notification
                if (includeOld || (lesson.day.equals(lesson.getDayString(c.get(Calendar.DAY_OF_WEEK)))
                        && lesson.startHour >= c.get(Calendar.HOUR_OF_DAY)
                        && lesson.startMinute > c.get(Calendar.MINUTE)))
                         list.add(lesson);
            }
        }
        return list;
    }

    public static ArrayList<String> sortStringDataList(ArrayList<Lesson> lessonList) {
        ArrayList<String> list = new ArrayList<>();
        String data = "";
        if (lessonList != null) {
            for (int i = 0; i < lessonList.size(); i++) {
                String lessonData = lessonList.get(i).getProfession() + "," + lessonList.get(i).getLocation() + "," + lessonList.get(i).getStartHour() + "," + lessonList.get(i).getStartMinute() + "," + lessonList.get(i).getEndHour() + "," + lessonList.get(i).getEndMinute() + "," + lessonList.get(i).getAttendance() + "\n";
                list.add(lessonData);
            }
        }

        return list;
    }
}