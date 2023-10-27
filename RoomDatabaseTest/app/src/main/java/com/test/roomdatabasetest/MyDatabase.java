package com.test.roomdatabasetest;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

/**
 * 数据库管理
 */
@Database(entities = {Book.class,Category.class},version = 3,exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    public static MyDatabase myDatabase;

    public static synchronized MyDatabase getInstance(Context context){
        if(myDatabase==null){
            myDatabase= Room.databaseBuilder(context,MyDatabase.class,"BookStrore2.db").fallbackToDestructiveMigration().build();
        }
        return myDatabase;
    }

    /**
     * 声明dao
     * @return
     */
    public abstract BookDao getBookDao();

    public abstract CategoryDao getCategoryDao();
    @Override
    public void clearAllTables() {

    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(@NonNull DatabaseConfiguration databaseConfiguration) {
        return null;
    }
}
