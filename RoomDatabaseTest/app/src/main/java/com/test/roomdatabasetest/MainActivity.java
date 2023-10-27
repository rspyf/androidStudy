package com.test.roomdatabasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    ExecutorService pool = Executors.newCachedThreadPool();
    MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button createDatabase = findViewById(R.id.create_database);
        Button add = findViewById(R.id.add);
        Button update = findViewById(R.id.update);
        Button del = findViewById(R.id.del);
        Button query = findViewById(R.id.query);
        db = MyDatabase.getInstance(MainActivity.this);
        //新增
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pool.execute(()->{
                    BookDao bookDao = db.getBookDao();
                    Book book = new Book();
                    book.setAuthor("sun");
                    book.setName("Java");
                    book.setPages(900);
                    book.setPrice(20.55);
                    bookDao.insertAll(book);
                });
            }
        });
        //更新
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pool.execute(()->{
                    BookDao bookDao = db.getBookDao();
                    Book book = new Book();
                    book.setId(1);
                    book.setName("Python");
                    bookDao.updateBook(book);
                });
            }
        });
        //删除
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pool.execute(()->{
                    BookDao bookDao = db.getBookDao();
                    Book book = new Book();
                    book.setId(1);
                    bookDao.del(book);
                });
            }
        });
        //查询
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookDao bookDao = db.getBookDao();
                pool.execute(()->{
                    List<Book> all = bookDao.getAll();
                    Gson gson = new Gson();
                    Log.d("MainActivity", gson.toJson(all));
                });
            }
        });
    }
}