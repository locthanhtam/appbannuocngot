package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView tvUserName = findViewById(R.id.tvUserName);
        TextView tvMemberTag = findViewById(R.id.tvMemberTag);
        Button btnLoginProfile = findViewById(R.id.btnLoginProfile);
        Button btnLogoutProfile = findViewById(R.id.btnLogoutProfile);
        LinearLayout btnGoToAddProduct = findViewById(R.id.btnGoToAddProduct);
        TextView btnHome = findViewById(R.id.btnHome);

        // KIỂM TRA XEM ĐÃ ĐĂNG NHẬP CHƯA ĐỂ HIỂN THỊ
        if (LoginActivity.userName.isEmpty()) {
            // NẾU CHƯA ĐĂNG NHẬP (Khách)
            tvUserName.setText("Bạn chưa đăng nhập");
            tvMemberTag.setVisibility(View.GONE);          // Ẩn chữ Thành viên
            btnLoginProfile.setVisibility(View.VISIBLE);   // Hiện nút Đăng Nhập
            btnLogoutProfile.setVisibility(View.GONE);     // Ẩn nút Đăng xuất
            btnGoToAddProduct.setVisibility(View.GONE);    // Ẩn Quản lý cửa hàng
        } else {
            // NẾU ĐÃ ĐĂNG NHẬP
            tvUserName.setText(LoginActivity.userName);
            tvMemberTag.setVisibility(View.VISIBLE);       // Hiện chữ Thành viên
            btnLoginProfile.setVisibility(View.GONE);      // Ẩn nút Đăng Nhập
            btnLogoutProfile.setVisibility(View.VISIBLE);  // Hiện nút Đăng xuất

            // Nếu là Admin thì mới hiện Quản lý cửa hàng
            if (LoginActivity.isAdmin) {
                btnGoToAddProduct.setVisibility(View.VISIBLE);
            } else {
                btnGoToAddProduct.setVisibility(View.GONE);
            }
        }

        // BẤM NÚT ĐĂNG NHẬP TRÊN TRANG CÁ NHÂN
        btnLoginProfile.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish(); // Đóng trang cá nhân lại để sang đăng nhập
        });

        // BẤM NÚT ĐĂNG XUẤT
        btnLogoutProfile.setOnClickListener(v -> {
            LoginActivity.userName = "";  // Xóa tên
            LoginActivity.isAdmin = false; // Xóa quyền
            Toast.makeText(this, "Đã đăng xuất tài khoản!", Toast.LENGTH_SHORT).show();
            recreate(); // Tải lại ngay lập tức màn hình Cá nhân
        });

        // Bấm nút Quản lý cửa hàng
        btnGoToAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, AddProductActivity.class);
            startActivity(intent);
        });

        // Bấm nút Home để về trang chủ
        btnHome.setOnClickListener(v -> {
            finish();
            overridePendingTransition(0, 0);
        });
    }
}