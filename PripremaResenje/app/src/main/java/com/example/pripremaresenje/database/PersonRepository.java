package com.example.pripremaresenje.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pripremaresenje.models.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonRepository {
    private SQLiteHelper dbHelper;
    private SQLiteDatabase database;

    public PersonRepository(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertPerson(Person person) {
        ContentValues values = new ContentValues();
        values.put("name", person.getName());
        values.put("birth_year", person.getBirthYear());
        return database.insert("person", null, values);
    }

    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        Cursor cursor = database.query("person", null, null, null, null, null, null);
        
        if (cursor.moveToFirst()) {
            do {
                Person person = new Person();
                person.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                person.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                person.setBirthYear(cursor.getInt(cursor.getColumnIndexOrThrow("birth_year")));
                persons.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return persons;
    }

    public Person getPersonById(int id) {
        Cursor cursor = database.query("person", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        Person person = null;
        
        if (cursor.moveToFirst()) {
            person = new Person();
            person.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            person.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            person.setBirthYear(cursor.getInt(cursor.getColumnIndexOrThrow("birth_year")));
        }
        cursor.close();
        return person;
    }
}
