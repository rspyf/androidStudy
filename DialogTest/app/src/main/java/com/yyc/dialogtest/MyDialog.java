package com.yyc.dialogtest;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

public class MyDialog extends Dialog {

    public MyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        //对话框布局
        setContentView(R.layout.dialog_layout);

        findViewById(R.id.yes_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
        findViewById(R.id.no_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
