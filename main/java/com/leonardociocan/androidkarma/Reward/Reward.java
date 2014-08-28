package com.leonardociocan.androidkarma.Reward;
public class Reward {
    String Name = "";
    Integer Value = 150;
    Boolean Positive = true;
    long ID;

    public Reward(long ID , String name , Integer value , Boolean positive ) {
        this.ID = ID;
        this.Name = name;
        this.Value = value;
        this.Positive = positive;
    }

    public Reward(String name , Integer value , Boolean positive ) {
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


    public  Reward(String name){
        Name = name;
    }
}