package com.leonardociocan.androidkarma.Habit;

public class Habit {
    String Name = "";
    Integer Value = 150;
    Boolean Positive = true;
    long ID;

    public Habit(long ID , String name , Integer value , Boolean positive ) {
        this.ID = ID;
        this.Name = name;
        this.Value = value;
        this.Positive = positive;
    }

    public long getID() {

        return ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getValue() {
        return Value;
    }

    public void setValue(Integer value) {
        Value = value;
    }

    public Boolean getPositive() {
        return Positive;
    }

    public void setPositive(Boolean positive) {
        Positive = positive;
    }


    public  Habit(String name){
        Name = name;
    }
}