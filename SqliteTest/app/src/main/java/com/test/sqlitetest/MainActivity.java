package com.test.sqlitetest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private MyDatabaseHelper myDatabaseHelper;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDatabaseHelper=new MyDatabaseHelper(this,"BookStrore.db",null,3);
         Button button = findViewById(R.id.create_database);
         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 myDatabaseHelper.getWritableDatabase();
             }
         });
         //新增数据
        Button add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase writableDatabase = myDatabaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //开始组装插入数据
                values.put("name","First Code");
                values.put("author","Guo Lin");
                values.put("pages",999);
                values.put("price",16.96);
                writableDatabase.insert("Book",null,values);
                values.clear();
                values.put("name","Java");
                values.put("author","Sun");
                values.put("pages",123);
                values.put("price",30.33);
                writableDatabase.insert("Book",null,values);
            }
        });
        //更新数据
        Button update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase writableDatabase = myDatabaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price",11.22);
                writableDatabase.update("Book",values,"name=?",new String[]{"Java"});
            }
        });
        //删除数据
        Button del = findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase writableDatabase = myDatabaseHelper.getWritableDatabase();
                writableDatabase.delete("Book","pages>?",new String[]{"500"});
            }
        });
        //查询数据
        Button query = findViewById(R.id.query);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase database = myDatabaseHelper.getWritableDatabase();
                //查询Book表中所有数据
                Cursor cursor = database.query("Book", null, null, null, null, null, null);
                if(cursor.moveToFirst()){
                    do {
                         String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity","book name is "+name);
                        Log.d("MainActivity","book author is "+author);
                        Log.d("MainActivity","book pages is "+pages);
                        Log.d("MainActivity","book price is "+price);

                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });

    }
}