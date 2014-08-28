package com.leonardociocan.androidkarma;

public class Log {
    String Name;
    String Time;
    boolean Positive;
    long id;
    int Value;

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

    public Log(long id , String name, String time, boolean positive , int value) {
        this.id = id;
        this.Value = value;
        Name = name;
        Time = time;
        Positive = positive;
    }

    public Log(String name, String time, boolean positive , int value) {
        this.Value = value;
        Name = name;
        Time = time;
        Positive = positive;
    }

    public int getValue() {
        return Value;
    }
}
