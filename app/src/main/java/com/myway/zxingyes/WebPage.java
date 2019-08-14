package com.myway.zxingyes;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

/***************
 ***** Created by fan on 2019/8/14.
 ***************/
public class WebPage extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        WebView wb = (WebView)findViewById(R.id.web_view1);
        //支持js语言
        wb.getSettings().setJavaScriptEnabled(true);
        // 缩放至屏幕的大小
        wb.getSettings().setLoadWithOverviewMode(true);
        //支持缩放
        wb.getSettings().setSupportZoom(true);
        //声明一个Intent意图，用来接受MainActivity传过来的值
        Intent intent = getIntent();
        //拿到MainActivity传过来的值并返回一个字符串，
        //口令要一致
        String jxString = intent.getStringExtra("path");
        //把字符串赋值给输入框
        //webVew去加载网页
        wb.loadUrl(jxString);
        //设置用自己的浏览器打开
        wb.setWebViewClient(new WebViewClient());


        //设置它的进度
        /*wb.setWebChromeClient(new WebChromeClient() {


            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                //拿到当前进度的int值

            }
        });

    }*/

        //是否允许返回
   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wb.canGoBack()) {
            //让webView返回上一级
            wb.goBack();
        }
        return true;
    }*/


    }
}
