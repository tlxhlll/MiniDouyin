package com.MiniDouyin.PersonDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

@Dao
public interface PersonDao {
//    @Query("SELECT * FROM person")
//    List<PersonEntity> loadAll();

    @Insert
    void addPerson(PersonEntity entity);

//    @Query("DELETE FROM todo")
//    void deleteAll();
//
//    @Delete
//    void deleteSome(PersonEntity entity);

    @Update
    void update(PersonEntity entity);
}
