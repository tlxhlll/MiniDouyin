package com.MiniDouyin.PersonDatabase;

import android.content.Context;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {PersonEntity.class}, version = 1,exportSchema = false)
public abstract class PersonDatabase extends RoomDatabase {
    private static PersonDatabase INSTANCE;

    public abstract PersonDao personDao();

    public PersonDatabase() {

    }

    public static PersonDatabase inst(Context context) {
        if (INSTANCE == null) {
            synchronized (PersonDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PersonDatabase.class, "person.db").build();
                }
            }
        }
        return INSTANCE;
    }
}
