package com.jungu.zootycoon;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ZooTycoonHelper extends SQLiteOpenHelper {
    public ZooTycoonHelper(Context context) {
        super(context, "zooTycoonDB", null, 7);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE animals (id INTEGER PRIMARY KEY AUTOINCREMENT, viewId INTEGER, x INTEGER, y INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE options (id TEXT, option INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS animals");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS options");
        onCreate(sqLiteDatabase);
    }
}