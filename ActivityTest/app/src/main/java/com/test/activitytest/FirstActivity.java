package com.test.activitytest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        Button button1 = (Button) findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击按钮效果
//                Toast.makeText(FirstActivity.this,"You Clicked Button 1",Toast.LENGTH_SHORT).show();
                //销毁一个活动
//                finish();
                //显式Intent打开SecondActivity活动
//                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                //隐式Intent
//                Intent intent = new Intent("com.test.activitytest.ACTION_START");
//                intent.addCategory("com.test.activitytest.MY_CATEGORY");
                //隐式Intent调用其他程序活动
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("http://www.baidu.com"));
                //Intent调用拨打电话
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:10086"));
                //传递数据
//                String data="Hello SecondActivity";
//                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
//                intent.putExtra("extra_data",data);
                //接收返回数据
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivityForResult(intent,1);
//                startActivity(intent);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String data_return = data.getStringExtra("data_return");
            Log.d("FirstActivity",data_return);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add_item){
            Toast.makeText(this,"You clicked Add",Toast.LENGTH_SHORT).show();
        }else if(item.getItemId()==R.id.remove_item){
            Toast.makeText(this,"You clicked Remove",Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}