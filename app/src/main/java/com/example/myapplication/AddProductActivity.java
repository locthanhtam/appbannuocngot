package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        EditText edtName = findViewById(R.id.edtProductName);
        EditText edtBrand = findViewById(R.id.edtBrand);
        EditText edtPrice = findViewById(R.id.edtPrice);
        EditText edtQty = findViewById(R.id.edtQuantity);

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String brand = edtBrand.getText().toString().trim();
            String strPrice = edtPrice.getText().toString().trim();
            String strQty = edtQty.getText().toString().trim();

            if (name.isEmpty() || brand.isEmpty() || strPrice.isEmpty() || strQty.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ các trường!", Toast.LENGTH_SHORT).show();
                return;
            }

            long price = Long.parseLong(strPrice);
            int qty = Integer.parseInt(strQty);

            DataManager.productList.add(0, new Product(name, brand, price, qty));
            Toast.makeText(this, "Đã lưu " + name + " vào kho!", Toast.LENGTH_SHORT).show();
            finish();
        });

        findViewById(R.id.btnCancel).setOnClickListener(v -> finish());
    }
}