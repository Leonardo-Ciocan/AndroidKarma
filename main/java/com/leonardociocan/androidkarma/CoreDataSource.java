package com.leonardociocan.androidkarma;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.leonardociocan.androidkarma.Habit.Habit;
import com.leonardociocan.androidkarma.Reward.Reward;
import com.leonardociocan.androidkarma.Todo.Todo;

import java.util.ArrayList;

public class CoreDataSource {
    SQLiteDatabase database;
    DBHelper helper;

    public  CoreDataSource(Context context){
        helper = new DBHelper(context);
    }

    public void open(){
        database = helper.getWritableDatabase();
    }

    public void close(){
        helper.close();
    }

    public void addItem(String name , Integer value, Boolean positive , String type){
        ContentValues values = new ContentValues();
        values.put(helper.NAME_COLUMN , name);
        values.put(helper.VALUE_COLUMN , value);
        values.put(helper.POSITIVE_COLUMN , positive ? "1" : "0");
        values.put(helper.TYPE_COLUMN , type);
        database.insert(helper.TABLE , null , values);
    }

    public void addLog(String name  , String date , Boolean positive){
        ContentValues values = new ContentValues();
        values.put(helper.NAME_COLUMN , name);
        values.put(helper.POSITIVE_COLUMN , positive ? "1" : "0");
        values.put("date" , date);
        database.insert("logs" , null , values);
    }

    public void updateItem(long id , String name , Integer value, Boolean positive , String type){
        ContentValues values = new ContentValues();
        values.put(helper.NAME_COLUMN , name);
        values.put(helper.VALUE_COLUMN , value);
        values.put(helper.POSITIVE_COLUMN , positive ? "1" : "0");
        values.put(helper.TYPE_COLUMN , type);
        database.update(helper.TABLE, values, helper.ID_COLUMN + "=" + id, null);
    }

    public void delete(long id){
        database.delete(helper.TABLE , helper.ID_COLUMN +"=" + id , null);
    }

    public void deleteLog(long id){
        database.delete("logs" , helper.ID_COLUMN +"=" + id , null);
    }

    public ArrayList<Habit> GetHabits(){
        ArrayList<Habit> habits = new ArrayList<Habit>();
        Cursor cursor = database.query(helper.TABLE , new String[] {helper.ID_COLUMN , helper.NAME_COLUMN , helper.VALUE_COLUMN , helper.POSITIVE_COLUMN}
        ,"type='habit'",null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getLong(0);
            String name = cursor.getString(1);
            Integer value = cursor.getInt(2);
            Boolean positive = cursor.getString(3).equals("1");
            habits.add(new Habit(id,name,value,positive));
            cursor.moveToNext();
        }
        cursor.close();
        return habits;
    }

    public ArrayList<Reward> GetRewards(){
        ArrayList<Reward> rewards = new ArrayList<Reward>();
        Cursor cursor = database.query(helper.TABLE , new String[] {helper.ID_COLUMN , helper.NAME_COLUMN , helper.VALUE_COLUMN}
                ,"type='reward'",null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getLong(0);
            String name = cursor.getString(1);
            Integer value = cursor.getInt(2);
            rewards.add(new Reward(id,name,value,true));
            cursor.moveToNext();
        }
        cursor.close();
        return rewards;
    }

    public ArrayList<Todo> GetTodos(){
        ArrayList<Todo> todos = new ArrayList<Todo>();
        Cursor cursor = database.query(helper.TABLE , new String[] {helper.ID_COLUMN , helper.NAME_COLUMN , helper.VALUE_COLUMN, helper.POSITIVE_COLUMN}
                ,"type='todo'",null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getLong(0);
            String name = cursor.getString(1);
            Integer value = cursor.getInt(2);
            Boolean positive = cursor.getString(3).equals("1");
            todos.add(new Todo(id,name,value,positive));
            cursor.moveToNext();
        }
        cursor.close();
        return todos;
    }

    public ArrayList<Log> GetLogs(){
        ArrayList<Log> todos = new ArrayList<Log>();
        Cursor cursor = database.query("logs" , new String[] {helper.ID_COLUMN , helper.NAME_COLUMN , "date", helper.POSITIVE_COLUMN}
                ,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getLong(0);
            String name = cursor.getString(1);
            String date = cursor.getString(2);
            Boolean positive = cursor.getString(3).equals("1");
            todos.add(new Log(id,name,date,positive));
            cursor.moveToNext();
        }
        cursor.close();
        return todos;
    }
}
