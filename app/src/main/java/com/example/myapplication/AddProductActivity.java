package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddProductActivity extends AppCompatActivity {

    private int editIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Ánh xạ chuẩn xác ID từ file XML của bạn
        TextView btnBack = findViewById(R.id.btnBackAdd);
        EditText edtMaSP = findViewById(R.id.edtProductId);
        EditText edtTenSP = findViewById(R.id.edtProductName);
        EditText edtGia = findViewById(R.id.edtPrice);
        EditText edtSoLuong = findViewById(R.id.edtQuantity);
        Button btnLuuSanPham = findViewById(R.id.btnSaveProduct);

        // Kiểm tra xem Admin đang thêm mới hay bấm vào để sửa
        editIndex = getIntent().getIntExtra("edit_index", -1);
        if (editIndex != -1) {
            Product oldProduct = DataManager.productList.get(editIndex);
            edtMaSP.setText(oldProduct.getId());
            edtTenSP.setText(oldProduct.getName());
            edtGia.setText(String.valueOf(oldProduct.getPrice()));
            edtSoLuong.setText(String.valueOf(oldProduct.getQuantity()));
            btnLuuSanPham.setText("CẬP NHẬT SẢN PHẨM");
        }

        // Xử lý nút Trở về
        btnBack.setOnClickListener(v -> finish());

        // Xử lý nút Lưu Sản Phẩm
        btnLuuSanPham.setOnClickListener(v -> {
            String maSP = edtMaSP.getText().toString().trim();
            String tenSP = edtTenSP.getText().toString().trim();
            String giaSP = edtGia.getText().toString().trim();
            String soLuong = edtSoLuong.getText().toString().trim();

            if (maSP.isEmpty() || tenSP.isEmpty() || giaSP.isEmpty() || soLuong.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ Mã, Tên, Giá và Số lượng!", Toast.LENGTH_SHORT).show();
                return;
            }

            long giaBan = 0;
            int slKho = 0;
            try {
                giaBan = Long.parseLong(giaSP);
                slKho = Integer.parseInt(soLuong);
            } catch (Exception e) {
                Toast.makeText(this, "Giá và Số lượng phải là số!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (editIndex != -1) {
                // Cập nhật sản phẩm cũ
                Product p = DataManager.productList.get(editIndex);
                p.setId(maSP);
                p.setName(tenSP);
                p.setPrice(giaBan);
                p.setQuantity(slKho);
                Toast.makeText(this, "CẬP NHẬT THÀNH CÔNG: " + tenSP, Toast.LENGTH_SHORT).show();
            } else {
                // Thêm sản phẩm mới toanh vào DataManager
                Product sanPhamMoi = new Product(maSP, tenSP, giaBan, slKho, R.drawable.coca);
                DataManager.productList.add(sanPhamMoi);
                Toast.makeText(this, "LƯU THÀNH CÔNG: " + tenSP, Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }
}