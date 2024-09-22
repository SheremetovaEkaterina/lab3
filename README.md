# Лабораторная работа №3. Работа с базой данных
- _Выполнила:_ Шереметова
- _Язык программирования:_ Java

## Что делает приложение?
1. Состоит из:
   - [Меню](#activity1).
   - [Экрана вывода данных из таблицы "Одногруппники"](#activity2).
   - Т.к. происходит взаимодействие с БД, был создан отдельный класс `DBHelper` (файл `DBHelper.java`).
2. Создает базу данных и таблицу "Одногруппники" при запуске, если они не существуют.
3. Удаляет все записи и добавляет 5 новых при первом запуске.
4. Выводит данные из таблицы на отдельный экран при нажатии на кнопку "посмотреть".
5. Добавляет новую запись при нажатии на кнопку "добавить".
6. Обновляет последнюю запись при нажатии на кнопку "заменить".
7. При изменении версии базы данных удаляет таблицу «Одногруппники» и создает таблицу «Одногруппники», содержащую больше полей (ветка: task-2).

---
### <a id="activity1"> Меню </a>

Меню состоит из 3 кнопок:
- **"посмотреть"**. По тапу открывается [Экран вывода данных из таблицы "Одногруппники"](#activity2).
- **"добавить"**. По тапу добавляется новая строка в таблицу "Одногруппники":
  - для ветки `main`
    -  ``` java
        btnAddRecord.setOnClickListener(v -> {
            ContentValues contentValues = new ContentValues();
            contentValues.put("FIO", "Новый Одногруппник");
            db.insert("classmates", null, contentValues);
        });
        ```
  - для ветки`task-2`
    -  ``` java
        btnAddRecord.setOnClickListener(v -> {
            ContentValues contentValues = new ContentValues();
            contentValues.put("LastName", "Новый");
            contentValues.put("FirstName", "Одногруппник");
            contentValues.put("MiddleName", "Крутышкович");
            db.insert("classmates", null, contentValues);
        });
       ```
- **"заменить".** По тапу последняя запись таблицы изменяется на "Иванов Иван Иванович":
  - ``` java
    btnUpdateLastRecord.setOnClickListener(v -> {
            updateLastRecord();
        });
    ```
   - для ветки `main`
     -  ``` java
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
        ```
  - для ветки`task-2`
    - ``` java
      private void updateLastRecord() {
        Cursor cursor = db.rawQuery("SELECT * FROM classmates ORDER BY ID DESC LIMIT 1", null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("ID"));
            ContentValues contentValues = new ContentValues();
            contentValues.put("LastName", "Иванов");
            contentValues.put("FirstName", "Иван");
            contentValues.put("MiddleName", "Иванович");
            db.update("classmates", contentValues, "ID = ?", new String[]{String.valueOf(id)});
        }
        cursor.close();
      }
      ``` 

<p align="center">
    <img src="https://github.com/user-attachments/assets/f1d31f0a-a472-47e1-a046-ea7ecd852348" width="300"> 
</p> 

---
### <a id="activity2"> Экран вывода данных из таблицы "Одногруппники" </a>
На этом экране также происходит обращение к БД.

#### Для ветки `main`
Таблица "Одногруппники" состоит из полей:
- ID
- ФИО
- время добавления записи

На экране выводятся записи в виде списка, для загрузки записей используется следующая фнукция:
``` java
private void loadRecords() {
        Cursor cursor = db.rawQuery("SELECT * FROM classmates", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String fio = cursor.getString(cursor.getColumnIndex("FIO"));
                @SuppressLint("Range") String addedTime = cursor.getString(cursor.getColumnIndex("added_time"));
                studentList.add(fio + " - " + addedTime);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
```
Скрины 1 - 3 демонстрируют данные:
- при первом запуске приложения
- после добавления 2 новых записей
- после замены последней
<p align="center">
    <img src="https://github.com/user-attachments/assets/2ab0f8d9-5f09-4314-8609-16b6a0c55314" width="200"> 
    <img src="https://github.com/user-attachments/assets/0516d5de-c189-4633-b626-df87b45acad8" width="200"> 
    <img src="https://github.com/user-attachments/assets/34c8ab00-e11c-4dff-aa8f-2e811c459eb2" width="200"> 
</p> 

#### Для ветки `task-2`
Таблица "Одногруппники" состоит из полей:
- ID
- Фамилия
- Имя
- Отчество
- время добавления записи

На экране выводятся записи в виде списка, для загрузки записей используется следующая фнукция:
``` java
private void loadRecords() {
        Cursor cursor = db.rawQuery("SELECT * FROM classmates", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex("LastName"));
                @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex("FirstName"));
                @SuppressLint("Range") String middleName = cursor.getString(cursor.getColumnIndex("MiddleName"));
                @SuppressLint("Range") String addedTime = cursor.getString(cursor.getColumnIndex("added_time"));
                studentList.add(lastName + " " + firstName + " " + middleName + " - " + addedTime);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
```
Скрины 1 - 3 демонстрируют данные:
- при первом запуске приложения
- после добавления новой записи
- после замены последней
<p align="center">
    <img src="https://github.com/user-attachments/assets/4f769849-33c6-4392-80bb-99425f95bc82" width="200"> 
    <img src="https://github.com/user-attachments/assets/3e13f5f2-e6b1-424b-93c1-42b0412318e5" width="200"> 
    <img src="https://github.com/user-attachments/assets/a997f903-6972-4c59-b85c-906464e0a4cf" width="200"> 
</p> 

---


