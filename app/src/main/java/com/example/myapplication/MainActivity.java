package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView tvProductCount, tvTotalValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvProductCount = findViewById(R.id.tvProductCount);
        tvTotalValue = findViewById(R.id.tvTotalValue);

        findViewById(R.id.btnProductList).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ProductListActivity.class)));

        findViewById(R.id.btnAddProduct).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddProductActivity.class)));

        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        findViewById(R.id.btnExit).setOnClickListener(v -> finishAffinity());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật số liệu động
        int count = DataManager.getTotalQuantity();
        long totalVal = DataManager.getTotalValue();
        DecimalFormat formatter = new DecimalFormat("#,###");

        tvProductCount.setText("Số lượng sản phẩm: " + count);
        tvTotalValue.setText("Tổng giá trị: " + formatter.format(totalVal) + " VNĐ");
    }
}