package com.example.final_project;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

public class MainViewModel extends AndroidViewModel {
    private static MainViewModel instance;
    String day;

    // ******* The observable vars *********************

    //  The MutableLiveData class has the setValue(T) and postValue(T)
    //  methods publicly and you must use these if you need to edit the value stored in a LiveData object.
    //  Usually, MutableLiveData is used in the ViewModel and then the ViewModel only exposes

    private MutableLiveData<ArrayList<Lesson>> lessonLiveData;
    private MutableLiveData<ArrayList<String>> dataLiveData;
    private MutableLiveData<Integer> positionSelected;

    public MainViewModel(@NonNull Application application) {
        super(application);
        init(application);
    }

    // Pay attention that MainViewModel is singleton it helps
    public static MainViewModel getInstance(Application application) {
        if (instance == null) {
            instance = new MainViewModel(application);
        }

        return instance;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public LiveData<ArrayList<Lesson>> getLessons() {
        return lessonLiveData;
    }

    public LiveData<ArrayList<String>> getData() { return  dataLiveData;}

    public void setLessonLiveData(ArrayList<Lesson> list) {
        lessonLiveData.setValue(list);
    }

    public void setDataLiveData(ArrayList<String> list) {
        dataLiveData.setValue(list);
    }

    public MutableLiveData<Integer> getSelectedItemPosition() {
        return positionSelected;
    }

    // This use the setValue
    public void init(Application application) {
        lessonLiveData = new MutableLiveData<>();
        dataLiveData = new MutableLiveData<>();
        positionSelected = new MutableLiveData<>();
        positionSelected.setValue(-1);

        ArrayList<String> dataList = FileManager.getDataFromDb(application.getApplicationContext(),day);
        ArrayList<Lesson> lessonList = FileManager.getLessonsFromDb(dataList,day,true);

        Collections.sort(lessonList);
        dataList = FileManager.sortStringDataList(lessonList);

        lessonLiveData.setValue(lessonList);
        dataLiveData .setValue(dataList);
    }


    public void addNewLesson(Context context,Lesson newLesson) {
        //getting lessonList and dataList update
        ArrayList<Lesson> lessonsList = getLessons().getValue();
        ArrayList<String> dataList = getData().getValue();

        //lessonData contain all the details of lesson
        String lessonData = newLesson.getProfession() + "," + newLesson.getLocation() + "," + newLesson.getStartHour() + "," + newLesson.getStartMinute() + "," + newLesson.getEndHour() + "," + newLesson.getEndMinute() + "," + newLesson.getAttendance() + "\n";
        StringBuilder data = new StringBuilder();
        int index;

        try {
            //open the relevant day file in according to function newLesson.getDay()
            InputStream inputStream = context.openFileInput(newLesson.getDay() + ".txt");
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                //inputStream.available() method is used to return the number of available bytes left
                // for reading from this InputStream without blocking by the next call
                // of the method from this InputStream.
                int size = inputStream.available();
                char[] buffer = new char[size];
                inputStreamReader.read(buffer);
                inputStream.close();
                String temp = new String(buffer);

                //adding temp string to data builder
                data.append(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //adding lessonData to data builder
        data.append(lessonData);

        //writing data builder to the day file
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(newLesson.getDay() + ".txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data.toString());
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        lessonsList.add(newLesson);
        Collections.sort(lessonsList);
        index = lessonsList.indexOf(newLesson);
        //adding tne newLesson to the right place in the list by index
        dataList.add(index, lessonData);

        //update LessonLiveData and DataLiveData
        setLessonLiveData(lessonsList);
        setDataLiveData(dataList);

        //if the lesson have mandatory attendance create noification in the right time
        if(newLesson.getAttendance()) {
            NotificationAppManager.setLessonNotification(context, newLesson);
        }

    }
}