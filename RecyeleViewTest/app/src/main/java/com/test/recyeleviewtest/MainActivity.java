package com.test.recyeleviewtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Fruit> fruits=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化水果
        initFruits();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        //LinearLayoutManager manager = new LinearLayoutManager(this);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        //设置布局方式：横向
        manager.setOrientation(RecyclerView.HORIZONTAL);
        //item间隔
//        recyclerView.addItemDecoration(new SpacesItemDecoration(1));
        recyclerView.setLayoutManager(manager);
        FruitAdapter adapter = new FruitAdapter(fruits);
        recyclerView.setAdapter(adapter);
    }

    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            fruits.add(new Fruit(getRandomLengthName("Apple"),R.drawable.t10003));
            fruits.add(new Fruit(getRandomLengthName("Banana"),R.drawable.t10004));
            fruits.add(new Fruit(getRandomLengthName("Orange"),R.drawable.t10005));
            fruits.add(new Fruit(getRandomLengthName("Watermelon"),R.drawable.t10006));
            fruits.add(new Fruit(getRandomLengthName("Pear"),R.drawable.t10007));
            fruits.add(new Fruit(getRandomLengthName("Grape"),R.drawable.t10008));
            fruits.add(new Fruit(getRandomLengthName("Pineapple"),R.drawable.t10011));
            fruits.add(new Fruit(getRandomLengthName("Strawberry"),R.drawable.t10013));
            fruits.add(new Fruit(getRandomLengthName("Cherry"),R.drawable.t10014));
            fruits.add(new Fruit(getRandomLengthName("Mango"),R.drawable.t10015));
        }
    }

    private String getRandomLengthName(String name){
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(name);
        }
        return builder.toString();
    }
}