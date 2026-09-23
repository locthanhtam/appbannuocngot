package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Tự động nhảy thẳng vào màn hình mua sắm Shopee ngay khi mở app
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}