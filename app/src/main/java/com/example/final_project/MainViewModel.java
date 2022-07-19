package com.example.final_project;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainViewModel extends AndroidViewModel {
    private static MainViewModel instance;
    String day;
    Context context;

    // ******* The observable vars *********************

    //  The MutableLiveData class has the setValue(T) and postValue(T)
    //  methods publicly and you must use these if you need to edit the value stored in a LiveData object.
    //  Usually, MutableLiveData is used in the ViewModel and then the ViewModel only exposes

    private MutableLiveData<ArrayList<Lesson>> lessonLiveData;
    private MutableLiveData<ArrayList<String>> dataLiveData;
    private MutableLiveData<Lesson> lessonSelected;
    private MutableLiveData<Integer> positionSelected;

    public MainViewModel(@NonNull Application application, Context context, Activity activity,String day) {
        super(application);
        this.day = day;
        this.context = context;
        init(application);
    }

    // Pay attention that MainViewModel is singleton it helps
    public static MainViewModel getInstance(Application application, Context context, Activity activity, String day) {
        if (instance == null) {
            instance = new MainViewModel(application, context, activity, day);
        }

        return instance;
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

    public MutableLiveData<Lesson> getSelectedItemCountry() {
        return lessonSelected;
    }

    public void setCountrySelect(Lesson lesson) {
        lessonSelected.setValue(lesson);
    }

    public void setPositionSelected(Integer index) {
        positionSelected.setValue(index);
    }

    // This use the setValue
    public void init(Application application) {
        lessonLiveData = new MutableLiveData<>();
        dataLiveData = new MutableLiveData<>();
        lessonSelected = new MutableLiveData<>();
        positionSelected = new MutableLiveData<>();
        positionSelected.setValue(-1);

        ArrayList<String> dataList = getDataFromDb();
        ArrayList<Lesson> lessonList = getLessonsFromDb(dataList);

        lessonLiveData.setValue(lessonList);
        dataLiveData .setValue(dataList);
    }

    public ArrayList<String> getDataFromDb() {
        ArrayList<String> list = new ArrayList<>();
        String data = "";
        try {
            InputStream inputStream = context.getAssets().open(day + ".txt");
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

    public ArrayList<Lesson> getLessonsFromDb(ArrayList<String> dataList) {

        ArrayList<Lesson> list = new ArrayList<>();
        String[] temp = new String[7];

        if(dataList != null) {
            for (int i = 0; i < dataList.size(); i++) {
                temp = dataList.get(i).split(",");
                Lesson lesson = new Lesson(temp[0], temp[1], Integer.valueOf(temp[2]), Integer.valueOf(temp[3]), Integer.valueOf(temp[4]), Integer.valueOf(temp[5]), temp[6]);
                list.add(lesson);
            }
        }

        return list;
    }

    public void addNewLesson(Lesson newLesson) {
        ArrayList<Lesson> lessonsList = getLessons().getValue();
        ArrayList<String> dataList = getData().getValue();
        String lessonData = newLesson.getProfession() + "," + newLesson.getLocation() + "," + newLesson.getStartHour() + "," + newLesson.getStartMinute() + "," + newLesson.getEndHour() + "," + newLesson.getEndMinute() + "\n";
        StringBuilder data = new StringBuilder();
        String[] tempArr = new String[7];

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

        lessonsList.add(newLesson);
        dataList.add(lessonData);

        setLessonLiveData(lessonsList);
        setDataLiveData(dataList);

    }
}
