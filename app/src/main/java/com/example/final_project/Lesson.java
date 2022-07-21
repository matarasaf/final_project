package com.example.final_project;


public class Lesson implements Comparable<Lesson> {
    String profession;
    String location;
    String day;
    int startHour;
    int startMinute;
    int endHour;
    int endMinute;
    boolean attendance;

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

    public void setAttendance(boolean attendance) {
        this.attendance = attendance;
    }

    public boolean getAttendance() { return attendance; }

    public Lesson(String profession, String location, int startHour,int startMinute, int endHour, int endMinute, String day, boolean attendance){
        this.profession = profession;
        this.location = location;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.day = day;
        this.attendance = attendance;
    }

    @Override
    public int compareTo(Lesson other) {
        int temp;

        temp = Integer.valueOf(this.getStartHour()).compareTo(Integer.valueOf(other.getStartHour()));
        if(temp != 0)
            return temp;

        //start hour equal
        temp = Integer.valueOf(this.getStartMinute()).compareTo(Integer.valueOf(other.getStartMinute()));
        if(temp != 0)
            return temp;

        //start time equal
        temp = Integer.valueOf(this.getEndHour()).compareTo(Integer.valueOf(other.getEndHour()));
        if(temp != 0)
            return temp;

        //start time and end hour equal
        temp = Integer.valueOf(this.getEndMinute()).compareTo(Integer.valueOf(other.getEndMinute()));
        if(temp != 0)
            return temp;

        //start time and end time equal
        return 0;
    }
}
