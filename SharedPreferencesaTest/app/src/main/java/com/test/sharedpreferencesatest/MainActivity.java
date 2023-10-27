package com.test.sharedpreferencesatest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         Button button = findViewById(R.id.save_data);
         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 SharedPreferences.Editor edit = getSharedPreferences("data", MODE_PRIVATE).edit();
                 edit.putString("name","Tom");
                 edit.putInt("age",12);
                 edit.putBoolean("married",false);
                 edit.apply();
             }
         });
         //还原数据按钮
        Button restore = findViewById(R.id.restore_data);
        restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                String name = sharedPreferences.getString("name", "");
                int age = sharedPreferences.getInt("age", 0);
                boolean married = sharedPreferences.getBoolean("married", false);
                Log.d("MainActivity","name is:"+name);
                Log.d("MainActivity","age is:"+age);
                Log.d("MainActivity","married is:"+married);

            }
        });
    }
}