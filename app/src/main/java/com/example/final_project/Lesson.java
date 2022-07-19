package com.example.final_project;

import java.sql.Time;

public class Lesson {
    String profession;
    String location;
    //String startTime;
    //String endTime;
    String day;
    Time startTime;
    Time endTime;

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Lesson(String profession, String location, Time startTime, Time endTime, String day){
        this.profession = profession;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }

    /*public int compare(Lesson other) {
        if(this.day.)
        return  this.name.compareTo(other.name);
    }*/

   /* @Override
    public String toString() {
        return name;
    }*/

}
