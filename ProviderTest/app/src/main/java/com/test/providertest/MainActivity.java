package com.test.providertest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button add = findViewById(R.id.add);
        //添加数据
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.test.roomdatabasetest.provider/book");
                ContentValues values = new ContentValues();
                values.put("name","A Clash of Kings");
                values.put("author","GM");
                values.put("pages",1020);
                values.put("price",30.12);
                Uri newUri = getContentResolver().insert(uri, values);
                newId=newUri.getPathSegments().get(1);
                Log.d("MainActivity.class","newId: "+newId);
            }
        });
        //查询数据
        Button query = findViewById(R.id.query);
        query.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.test.roomdatabasetest.provider/book");
                Cursor cursor = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    cursor = getContentResolver().query(uri, null, null, null);
                }
                if(cursor!=null){
                    while (cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity","name: "+name);
                        Log.d("MainActivity","author: "+author);
                        Log.d("MainActivity","pages: "+pages);
                        Log.d("MainActivity","price: "+price);
                    }
                }
            }
        });
        //更新数据
        Button update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.test.roomdatabasetest.provider/book/" + newId);
                ContentValues contentValues = new ContentValues();
                contentValues.put("name","a new book");
                contentValues.put("pages",10);
                contentValues.put("price",9.9);
                contentValues.put("author","pyf");
                getContentResolver().update(uri,contentValues,null,null);
            }
        });
        //删除数据
        Button del = findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.test.roomdatabasetest.provider/book/" + newId);
                getContentResolver().delete(uri,null,null);
            }
        });
    }
}