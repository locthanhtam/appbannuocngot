package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        LinearLayout container = findViewById(R.id.layoutContainer);
        DecimalFormat formatter = new DecimalFormat("#,###");

        for (Product p : DataManager.productList) {
            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setOrientation(LinearLayout.VERTICAL);
            itemLayout.setBackgroundColor(Color.WHITE);
            itemLayout.setPadding(24, 20, 24, 20);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 16);
            itemLayout.setLayoutParams(params);

            TextView tvTitle = new TextView(this);
            tvTitle.setText("🥤 " + p.getName() + " (" + p.getBrand() + ")");
            tvTitle.setTextSize(16);
            tvTitle.setTextColor(Color.BLACK);

            TextView tvDetails = new TextView(this);
            tvDetails.setText("💰 Giá: " + formatter.format(p.getPrice()) + " VNĐ   |   📦 Kho: " + p.getQuantity());
            tvDetails.setTextSize(14);
            tvDetails.setTextColor(Color.DKGRAY);
            tvDetails.setPadding(0, 6, 0, 0);

            itemLayout.addView(tvTitle);
            itemLayout.addView(tvDetails);
            container.addView(itemLayout);
        }

        findViewById(R.id.btnBackHome).setOnClickListener(v -> finish());
    }
}