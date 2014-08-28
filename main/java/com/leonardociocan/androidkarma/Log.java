package com.leonardociocan.androidkarma;

public class Log {
    String Name;
    String Time;
    boolean Positive;
    long id;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public boolean isPositive() {
        return Positive;
    }

    public void setPositive(boolean positive) {
        Positive = positive;
    }

    public Log(long id , String name, String time, boolean positive) {
        this.id = id;
        Name = name;
        Time = time;
        Positive = positive;
    }

    public Log(String name, String time, boolean positive) {

        Name = name;
        Time = time;
        Positive = positive;
    }

}
