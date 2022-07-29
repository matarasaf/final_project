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
        ArrayList<Lesson> lessonsList = getLessons().getValue();
        ArrayList<String> dataList = getData().getValue();
        String lessonData = newLesson.getProfession() + "," + newLesson.getLocation() + "," + newLesson.getStartHour() + "," + newLesson.getStartMinute() + "," + newLesson.getEndHour() + "," + newLesson.getEndMinute() + "," + newLesson.getAttendance() + "\n";
        StringBuilder data = new StringBuilder();
        int index;

        try {
            InputStream inputStream = context.openFileInput(newLesson.getDay() + ".txt");
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int size = inputStream.available();
                char[] buffer = new char[size];
                inputStreamReader.read(buffer);
                inputStream.close();
                String temp = new String(buffer);
                data.append(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        data.append(lessonData);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(newLesson.getDay() + ".txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data.toString());
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(newLesson.getAttendance())
            NotificationAppManager.setLessonNotification(context,newLesson);

        lessonsList.add(newLesson);
        Collections.sort(lessonsList);
        index = lessonsList.indexOf(newLesson);
        dataList.add(index, lessonData);

        setLessonLiveData(lessonsList);
        setDataLiveData(dataList);
    }
}