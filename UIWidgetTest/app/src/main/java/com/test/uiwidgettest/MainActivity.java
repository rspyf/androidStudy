package com.test.uiwidgettest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private  EditText editText;
    private ImageView imageView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册点击监听器
//        Button button = findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this,"click !",Toast.LENGTH_SHORT).show();
//            }
//        });
        //注册EditText组件
        editText = findViewById(R.id.edit_text);
        //注册ImageView组件
        imageView = findViewById(R.id.image_view1);
        //注册progressBar组件
        progressBar = findViewById(R.id.progress_bar);
        //实现 View.OnClickListener注册监听器
        Button button = findViewById(R.id.button);
        //AlerDialog
//        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
//        dialog.setTitle("This is a Dialog!");
//        dialog.setMessage("Somtion important !!");
//        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//        dialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//        dialog.show();
        //ProgressDialog 弹窗组件
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("This is ProgressDialog");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.button){
            //点击获取输入文字信息
            //Toast.makeText(MainActivity.this,editText.getText(),Toast.LENGTH_SHORT).show();
            //点击切换图片
            // imageView.setImageResource(R.drawable.img_10002);
            //判断ProgressBar是否可见
//            if(progressBar.getVisibility()==View.GONE){
            //设置可见
//                progressBar.setVisibility(View.VISIBLE);
//            }else{
            //设置不可见
//                progressBar.setVisibility(View.GONE);
//            }
            //android:max设置最大值，更改进度条进度
            int progress = progressBar.getProgress();
            progress=progress+10;
            progressBar.setProgress(progress);
        }
    }
}