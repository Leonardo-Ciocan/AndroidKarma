package com.leonardociocan.androidkarma;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.StaticLayout;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE = "items";
    public static final String NAME_COLUMN = "name";
    public static final String ID_COLUMN = "_id";

    public static final String VALUE_COLUMN = "value";
    public static final String POSITIVE_COLUMN = "positive";
    public static final String TYPE_COLUMN = "type";

    public static final String DATABASE_NAME = "karma.db";

    private static final String DATABASE_CREATE = "create table "
            + TABLE + "("+
    ID_COLUMN
    + " integer primary key autoincrement, " + NAME_COLUMN
            + " text, " + VALUE_COLUMN
            + " integer, " + POSITIVE_COLUMN
            +" integer,"+ TYPE_COLUMN
            +" text);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
        sqLiteDatabase.execSQL("create table logs(_id integer primary key autoincrement, name text , positive integer , date text );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
