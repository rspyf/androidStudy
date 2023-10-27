package com.test.roomdatabasetest;

import android.content.ContentValues;
import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {
    @Insert
    void insertAll(Book...books);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Book book);
    @Delete
    int del(Book book);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateBook(Book...books);
    @Query("SELECT * FROM Book")
    List<Book> getAll();
    @Query("SELECT * FROM Book WHERE id= :id")
    Book getById(String id);



}
