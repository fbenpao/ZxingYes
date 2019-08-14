package com.myway.zxingyes;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.WriterException;
import com.google.zxing.activity.CaptureActivity;
import com.google.zxing.common.BitmapUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtn1;
    private EditText mEt;
    private Button mBtn2;
    private ImageView mImage;
    private final static int REQ_CODE = 1028;
    private Context mContext;
    private TextView mTvResult;
    private ImageView mImageCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
        initView();
        mContext = this;
    }

    private void initView() {
        mBtn1 = (Button) findViewById(R.id.btn1);

        mBtn1.setOnClickListener(this);
        mEt = (EditText) findViewById(R.id.et);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn2.setOnClickListener(this);
        mImage = (ImageView) findViewById(R.id.image);
        mImage.setOnClickListener(this);
        mTvResult = (TextView) findViewById(R.id.tv_result);
        mTvResult.setOnClickListener(this);
        mImageCallback = (ImageView) findViewById(R.id.image_callback);
        mImageCallback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
//                startActivity(new Intent(MainActivity.this, CaptureActivity.class));
                Intent intent = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(intent, REQ_CODE);
                break;
            case R.id.btn2:
                mImage.setVisibility(View.VISIBLE);
                //隐藏扫码结果view
                mImageCallback.setVisibility(View.GONE);
                mTvResult.setVisibility(View.GONE);

                String content = mEt.getText().toString().trim();
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapUtils.create2DCode(content);//根据内容生成二维码
                    mTvResult.setVisibility(View.GONE);
                    mImage.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            mImage.setVisibility(View.GONE);
            mTvResult.setVisibility(View.VISIBLE);
            mImageCallback.setVisibility(View.VISIBLE);

            String result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
            Bitmap bitmap = data.getParcelableExtra(CaptureActivity.SCAN_QRCODE_BITMAP);

            String regex = "http://(([a-zA-z0-9]|-){1,}\\.){1,}[a-zA-z0-9]{1,}-*" ;
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher( result );
            //判断扫描的内容，如果为网址则跳转到相应的页面，如果为文本则显示出来
            if(matcher.matches()){
                Intent intent = new Intent(MainActivity.this, WebPage.class);
                    intent.putExtra("path", result);
                    startActivity(intent);
                }else {
                     mTvResult.setText("扫码结果："+result);
                     showToast("扫码结果：" + result);
                }




            if(bitmap != null){
                mImageCallback.setImageBitmap(bitmap);//现实扫码图片
            }
        }


    }

    private void showToast(String msg) {
        Toast.makeText(mContext, "" + msg, Toast.LENGTH_SHORT).show();
    }
}