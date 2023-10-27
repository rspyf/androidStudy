package com.test.listviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String[] data={"Apple","Banana","Orange","Watermelon","Pear","Grape","Pineapple","Strawberry","Cherry","Mango","Apple","Banana","Orange","Watermelon","Pear","Grape","Pineapple","Strawberry","Cherry","Mango"};
    private List<Fruit> fruits=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, data);
//        ListView listView = findViewById(R.id.list_view);
//        listView.setAdapter(adapter);
        //初始化水果数据
        initFruits();
        FruitAdapter adapter = new FruitAdapter(MainActivity.this, R.layout.fruit_item, fruits);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fruit fruit = fruits.get(i);
                Toast.makeText(MainActivity.this,fruit.getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            fruits.add(new Fruit("Apple",R.drawable.t10003));
            fruits.add(new Fruit("Banana",R.drawable.t10004));
            fruits.add(new Fruit("Orange",R.drawable.t10005));
            fruits.add(new Fruit("Watermelon",R.drawable.t10006));
            fruits.add(new Fruit("Pear",R.drawable.t10007));
            fruits.add(new Fruit("Grape",R.drawable.t10008));
            fruits.add(new Fruit("Pineapple",R.drawable.t10011));
            fruits.add(new Fruit("Strawberry",R.drawable.t10013));
            fruits.add(new Fruit("Cherry",R.drawable.t10014));
            fruits.add(new Fruit("Mango",R.drawable.t10015));
        }
    }
}