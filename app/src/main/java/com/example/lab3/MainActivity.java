package com.example.lab3;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        Button btnShowRecords = findViewById(R.id.button2);
        Button btnAddRecord = findViewById(R.id.button);
        Button btnUpdateLastRecord = findViewById(R.id.button3);

        // Переход ко второму активити, чтобы показать записи
        btnShowRecords.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutStudents.class);
            startActivity(intent);
        });

        // Добавление новой записи
        btnAddRecord.setOnClickListener(v -> {
            ContentValues contentValues = new ContentValues();
            contentValues.put("FIO", "Новый Одногруппник");
            db.insert("classmates", null, contentValues);
        });

        // Обновление последней записи
        btnUpdateLastRecord.setOnClickListener(v -> {
            updateLastRecord();
        });
    }

    private void updateLastRecord() {
        Cursor cursor = db.rawQuery("SELECT * FROM classmates ORDER BY ID DESC LIMIT 1", null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("ID"));
            ContentValues contentValues = new ContentValues();
            contentValues.put("FIO", "Иванов Иван Иванович");
            db.update("classmates", contentValues, "ID = ?", new String[]{String.valueOf(id)});
        }
        cursor.close();
    }
}
