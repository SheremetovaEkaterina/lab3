package com.example.lab3;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 2;  // Увеличение версии БД

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createClassMatesTable(db);
        insertInitialData(db);
    }

    private void createClassMatesTable(SQLiteDatabase db) {
        // Создаем новую таблицу с полями Фамилия, Имя, Отчество
        db.execSQL("CREATE TABLE IF NOT EXISTS classmates ("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "LastName TEXT, FirstName TEXT, MiddleName TEXT, "
                + "added_time DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ");");
    }

    private void insertInitialData(SQLiteDatabase db) {
        // Вставляем 5 записей в обновленную таблицу
        for (int i = 1; i <= 5; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("LastName", "Фамилия " + i);
            contentValues.put("FirstName", "Имя " + i);
            contentValues.put("MiddleName", "Отчество " + i);
            db.insert("classmates", null, contentValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Удаление старой таблицы
        db.execSQL("DROP TABLE IF EXISTS classmates;");

        // Создание новой таблицы с полями Фамилия, Имя и Отчество
        createClassMatesTable(db);

        // Вставляем новые записи в обновленную таблицу
        insertInitialData(db);
    }
}

