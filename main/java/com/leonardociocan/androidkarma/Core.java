package com.leonardociocan.androidkarma;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.leonardociocan.androidkarma.Habit.Habit;
import com.leonardociocan.androidkarma.Reward.Reward;
import com.leonardociocan.androidkarma.Todo.Todo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Core {

    public static Context context;

    public static String Recents;

    public static CoreDataSource source;

    public static ArrayList<Habit> Habits;
    public static ArrayList<Reward> Rewards;
    public static ArrayList<Todo> Todos;
    public static ArrayList<Log> Logs;

    private static int Karma;

    public static List<KarmaChangedListener> listener = new ArrayList<KarmaChangedListener>();

    public static void addKarmaEventListener(KarmaChangedListener e){
        listener.add(e);
    }

    public static void setKarma(int karma) {

        Karma = karma;

        if(PreferenceManager.getDefaultSharedPreferences(context) != null) {
            SharedPreferences sharedPreferences = (PreferenceManager.getDefaultSharedPreferences(context));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("karma", Karma);
            editor.commit();
        }
    }

    public static void addKarma(String name , int i){
        Karma += i;
        for(KarmaChangedListener ls : listener){
            ls.OnKarmaChanged();
        }

        SharedPreferences sharedPreferences = (PreferenceManager.getDefaultSharedPreferences(context));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("karma", Karma);
        editor.commit();

        Log log = new Log(name , DateFormat.getDateTimeInstance().format(new Date()) , i > 0 , i);
        Logs.add(log);
        source.addLog( log.getName() , log.getTime() , log.isPositive() , log.getValue());
    }

    public static void triggerChanged(){
        for(KarmaChangedListener ls : listener){
            ls.OnKarmaChanged();
        }
    }

    public static int getKarma(){
        return Karma;
    }

    public static int[] LogValues(){
        if (Logs == null)return new int[]{};
        int[] vs = new int[Logs.size()];
        int acc = 0;
        for(int x = 0; x< Logs.size();x++) {
            vs[x] = Logs.get(x).Value + acc;
            acc = vs[x];
        }
        return vs;
    }
}


