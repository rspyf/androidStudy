package com.yyc.menutest;

import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //打开上下文菜单，长按按钮打开菜单
//        registerForContextMenu(findViewById(R.id.ctx_btn));
        //2.创建 覆盖onCreateContextMenu
        //3.菜单项的操作 覆盖onContextItemSelected
        //4.为按钮设置上下文操作模式
        //①实现ActionMode CallBack
        //②在view的长按事件中去启动上下文操作模式
        findViewById(R.id.ctx_btn).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActionMode(cb);
                return false;
            }
        });
        //弹出框功能是在android 7之后功能
        Button popupBtn = findViewById(R.id.popup_btn);
        popupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实例化PopupMenu对象
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, popupBtn);
                //加载菜单资源(popup),加载到菜单对象中
                popupMenu.getMenuInflater().inflate(R.menu.popup,popupMenu.getMenu());
                //给弹出菜单添加点击监听
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(R.id.copy==item.getItemId()){
                            Toast.makeText(MainActivity.this,"复制 "+item.getItemId(),Toast.LENGTH_SHORT).show();
                        } else if (R.id.paste==item.getItemId()) {
                            Toast.makeText(MainActivity.this,"粘贴: "+item.getItemId(),Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
                //显示
                popupMenu.show();
            }
        });
    }

    ActionMode.Callback cb = new ActionMode.Callback() {
        //创建，在启动上下文操作模式（startActionMode(Callback)）时调用
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            Log.e("TAG","创建");
            getMenuInflater().inflate(R.menu.context,menu);
            return true;
        }

        //在创建方法后进行调用
        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            Log.e("TAG","准备");
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            Log.e("TAG","点击");
            if(R.id.open==menuItem.getItemId()){
                Toast.makeText(MainActivity.this,"打开 "+menuItem.getItemId(),Toast.LENGTH_SHORT).show();
            } else if (R.id.close==menuItem.getItemId()) {
                Toast.makeText(MainActivity.this,"关闭: "+menuItem.getItemId(),Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        //上下文操作模式结束时被调用
        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            Log.e("TAG","结束");
        }
    };
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(R.id.open==item.getItemId()){
            Toast.makeText(this,"打开 "+item.getItemId(),Toast.LENGTH_SHORT).show();
        } else if (R.id.close==item.getItemId()) {
            Toast.makeText(this,"关闭: "+item.getItemId(),Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(R.id.save==item.getItemId()){
            Toast.makeText(this,"保存 "+item.getItemId(),Toast.LENGTH_SHORT).show();
        } else if (R.id.del==item.getItemId()) {
            Toast.makeText(this,"删除: "+item.getItemId(),Toast.LENGTH_SHORT).show();
        } else if (R.id.setting1==item.getItemId()) {
            Toast.makeText(this,"设置1: "+item.getItemId(),Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}