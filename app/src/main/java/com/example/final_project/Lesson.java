package com.example.final_project;

import java.sql.Time;

public class Lesson {
    String profession;
    String location;
    String day;
    int startHour;
    int startMinute;
    int endHour;
    int endMinute;

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

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Lesson(String profession, String location, int startHour,int startMinute, int endHour, int endMinute, String day){
        this.profession = profession;
        this.location = location;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
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
