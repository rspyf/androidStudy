package com.standzl.demo;

import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);

        // 配置WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 启用JavaScript

        webView.setWebViewClient(new WebViewClient()); // 在应用内打开链接
        webView.setWebChromeClient(new WebChromeClient()); // 支持JavaScript弹窗

        //通过JavaScriptInterface对象与JS交互
        JavaScriptInterface jsInterface = new JavaScriptInterface();
        webView.addJavascriptInterface(jsInterface, "AndroidInterface");//为JS添加交互对象,名称为AndroidInterface.在JS中直接用此对象调用方法即可

        // 加载本地网页(网页位置位于assets下的index.html)
        //这里的file:///android_asset/是固定写法
        webView.loadUrl("file:///android_asset/index.html");

    }


    /**
     * 此类的对象作为与JS交互的桥梁
     * 根据业务需求扩展此类即可
     */
    public class JavaScriptInterface {
        @JavascriptInterface
        public void showToast(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }
}
