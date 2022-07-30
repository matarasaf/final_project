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

    public int getDayNum() {
        int d = 0;
        switch(getDay()){
            case "Sunday":
                d = 1;
                break;
            case "Monday":
                d = 2;
                break;
            case  "Tuesday":
                d = 3;
                break;
            case "Wednesday":
                d = 4;
                break;
            case "Thursday":
                d = 5;
                break;
            case "Friday":
                d = 6;
                break;
        }
        return d;
    }
    public String getDayString(int day) {
        String d = "";
        switch(day){
            case 1:
                d = "Sunday";
                break;
            case 2:
                d = "Monday";
                break;
            case  3:
                d = "Tuesday";
                break;
            case 4:
                d = "Wednesday";
                break;
            case 5:
                d = "Thursday";
                break;
            case 6:
                d = "Friday";
                break;
        }
        return d;
    }
    public String getProfession() {
        return profession;
    }

    public String getLocation() {
        return location;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public String getDay() {
        return day;
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

    public String getStartTime(){
        String str = "";
        if(getStartHour() < 10)
            str = str + "0" + getStartHour() + ":";
        else str = str + getStartHour() + ":";
        if (getStartMinute() < 10)
            str = str + "0" + getStartMinute();
        else str = str + getStartMinute();
        return str;
    }
}