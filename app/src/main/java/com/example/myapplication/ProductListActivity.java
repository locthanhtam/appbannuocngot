package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        RecyclerView rcvProducts = findViewById(R.id.rcvProducts);

        // Chia lưới thành 2 cột
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvProducts.setLayoutManager(gridLayoutManager);

        // Đã bổ sung thêm hành động khi bấm nút "+ Mua" để hết báo lỗi
        ProductAdapter productAdapter = new ProductAdapter(DataManager.productList, product -> {
            Toast.makeText(ProductListActivity.this, "Đã thêm " + product.getName() + " vào giỏ!", Toast.LENGTH_SHORT).show();
        });
        rcvProducts.setAdapter(productAdapter);

        findViewById(R.id.btnBackHome).setOnClickListener(v -> finish());
    }
}