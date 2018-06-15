package com.jungu.zootycoon;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

//DB를 통합적으로 관리하는 클래스
public class ZooTycoonDB {
    private static String tableName = "animals";
    private static String optionTableName = "options";
    private static ZooTycoonHelper helper;
    private static ArrayList<Animal> animals = new ArrayList<>();

    public static ZooTycoonHelper getHelper() {
        return helper;
    }

    public static void setHelper(ZooTycoonHelper helper) {
        ZooTycoonDB.helper = helper;
    }

    public static boolean cleanTable() {
        if (helper != null) {
            animals.clear();

            SQLiteDatabase db = getHelper().getWritableDatabase();
            db.execSQL("DELETE FROM " + tableName);
            db.close();
            return true;
        } else {
            return false;
        }
    }

    public static boolean loadTable() {
        if (helper != null) {
            animals.clear();

            SQLiteDatabase db = getHelper().getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);

            while (cursor.moveToNext()) {
                Animal animal = new Animal(cursor.getInt(1), cursor.getInt(2), cursor.getInt(3));
                animals.add(animal);
            }

            cursor.close();
            db.close();
            return true;
        } else {
            return false;
        }
    }

    public static boolean addAnimal(Animal animal) {
        if (helper != null) {
            animals.add(animal);

            SQLiteDatabase db = getHelper().getWritableDatabase();
            db.execSQL("INSERT INTO " + tableName + " VALUES(?, ?, ?, ?)", new Object[]
                    {null, animal.viewId, animal.x, animal.y});

            db.close();
            return true;
        } else {
            return false;
        }
    }

    public static boolean removeAnimal(Animal animal) {
        if (helper != null) {
            for (int i = 0; i < animals.size(); i++) {
                Animal savedAnimal = animals.get(i);
                if (savedAnimal.viewId == animal.viewId && savedAnimal.x == animal.x && savedAnimal.y == animal.y) {
                    animals.remove(savedAnimal);

                    SQLiteDatabase db = getHelper().getWritableDatabase();
                    db.execSQL("DELETE FROM " + tableName + " WHERE viewId=? AND x=? AND y=?", new Object[]
                            {animal.viewId, animal.x, animal.y});

                    db.close();

                    return true;
                }
            }
        }
        return false;
    }

    public static int getAnimalsCount() {
        return animals.size();
    }

    public static Animal getAnimal(int index) {
        return animals.get(index);
    }

    public static class Animal {
        private int viewId, x, y;

        Animal(int viewId, int x, int y) {
            this.viewId = viewId;
            this.x = x;
            this.y = y;
        }

        public int getViewId() {
            return viewId;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public static boolean setOption(String key, int option) {
        if (helper != null) {
            SQLiteDatabase db = getHelper().getWritableDatabase();

            db.execSQL("DELETE FROM " + optionTableName + " WHERE id=" + key);

            db.execSQL("INSERT INTO " + optionTableName + " VALUES(?, ?)", new Object[]
                    {key, option});

            db.close();
            return true;
        } else {
            return false;
        }
    }

    public static int getOption(String key, int defaultValue) {
        if (helper != null) {
            SQLiteDatabase db = getHelper().getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT option FROM " + optionTableName + " WHERE id=" + key, null);

            int option = 0;
            while (cursor.moveToNext()) {
                option = cursor.getInt(0);
            }

            cursor.close();
            db.close();
            return option;
        }
        return defaultValue;
    }
}