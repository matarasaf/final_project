package com.example.final_project;

public class Lesson {
    String profession;
    String location;
    String startTime;
    String endTime;
    String day;

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Lesson(String profession, String location, String startTime, String endTime, String day){
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
