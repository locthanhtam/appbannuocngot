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

        // ĐÃ SỬA LỖI: Bổ sung đủ 3 thành phần (Danh sách, Click thường, Nhấn giữ)
        ProductAdapter productAdapter = new ProductAdapter(
                DataManager.productList,

                // 1. Hành động khi click bình thường
                product -> {
                    Toast.makeText(ProductListActivity.this, "Đã thêm " + product.getName(), Toast.LENGTH_SHORT).show();
                },

                // 2. Hành động khi nhấn giữ (Bổ sung tham số này để dập tắt lỗi đỏ)
                (product, position) -> {
                    if (LoginActivity.isAdmin) {
                        Toast.makeText(ProductListActivity.this, "Hãy về Trang Chủ (Home) để Sửa/Xóa nhé!", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        rcvProducts.setAdapter(productAdapter);

        // Nút quay lại
        findViewById(R.id.btnBackHome).setOnClickListener(v -> finish());
    }
}