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
public interface CategoryDao {
    @Insert
    void insertAll(Category...categories);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Category categories);
    @Delete
    int del(Category category);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateCategory(Category...categories);
    @Query("SELECT * FROM Category")
    List<Category> getAll();
    @Query("SELECT * FROM Category WHERE id= :id")
    Category getById(String id);
}
