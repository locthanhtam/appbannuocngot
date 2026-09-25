package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {

    public static boolean isAdmin = false;
    public static String userName = "";

    // Biến lưu trữ tạm thời trong lúc chạy app
    public static Map<String, String> userDatabase = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // BƯỚC 1: VỪA MỞ TRANG ĐĂNG NHẬP LÀ TẢI NGAY TOÀN BỘ TÀI KHOẢN ĐÃ LƯU TRONG MÁY LÊN
        loadRegisteredUsers();

        EditText edtUsername = findViewById(R.id.edtUsername);
        EditText edtPassword = findViewById(R.id.edtPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);
        TextView btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        // CHỨC NĂNG ĐĂNG KÝ
        btnRegister.setOnClickListener(v -> {
            String user = edtUsername.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên TK và MK để đăng ký!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (userDatabase.containsKey(user)) {
                Toast.makeText(this, "Tài khoản này đã tồn tại!", Toast.LENGTH_SHORT).show();
            } else {
                // Thêm vào danh sách tạm
                userDatabase.put(user, pass);

                // BƯỚC 2: GHI VÀO BỘ NHỚ VĨNH VIỄN CỦA ĐIỆN THOẠI
                saveNewUser(user, pass);

                Toast.makeText(this, "🎉 Đăng ký thành công!", Toast.LENGTH_LONG).show();
            }
        });

        // CHỨC NĂNG ĐĂNG NHẬP
        btnLogin.setOnClickListener(v -> {
            String user = edtUsername.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra xem user có trong database không và pass có khớp không
            if (userDatabase.containsKey(user) && userDatabase.get(user).equals(pass)) {

                if (user.equals("admin")) {
                    isAdmin = true;
                    userName = "Admin Cửa Hàng";
                    Toast.makeText(this, "Xin chào Quản trị viên!", Toast.LENGTH_SHORT).show();
                } else {
                    isAdmin = false;
                    userName = user;
                    Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                }

                // Ghi nhớ phiên đang đăng nhập để lần sau không bắt đăng nhập lại (Đã làm ở bước trước)
                SharedPreferences prefs = getSharedPreferences("ShopeeApp", MODE_PRIVATE);
                prefs.edit()
                        .putString("SAVED_USER", userName)
                        .putBoolean("SAVED_IS_ADMIN", isAdmin)
                        .apply();

                startActivity(new Intent(this, ProfileActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // --- CÁC HÀM XỬ LÝ LƯU TRỮ (DATABASE MINI) ---

    // Hàm 1: Lưu tài khoản mới đăng ký vào bộ nhớ
    private void saveNewUser(String username, String password) {
        SharedPreferences prefs = getSharedPreferences("ShopeeDatabase", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Kéo danh sách các tên tài khoản cũ ra, thêm tên mới vào
        Set<String> userList = prefs.getStringSet("UserList", new HashSet<>());
        userList.add(username);

        // Lưu lại danh sách tên, và lưu Mật khẩu tương ứng
        editor.putStringSet("UserList", userList);
        editor.putString("Pass_" + username, password);
        editor.apply();
    }

    // Hàm 2: Tải toàn bộ tài khoản lên khi mở app
    private void loadRegisteredUsers() {
        // Cấp luôn tài khoản quản trị mặc định không bao giờ mất
        userDatabase.put("admin", "admin");

        SharedPreferences prefs = getSharedPreferences("ShopeeDatabase", MODE_PRIVATE);
        Set<String> userList = prefs.getStringSet("UserList", new HashSet<>());

        // Duyệt qua từng người trong danh sách để lấy mật khẩu ráp vào bộ nhớ tạm
        for (String user : userList) {
            String pass = prefs.getString("Pass_" + user, "");
            userDatabase.put(user, pass);
        }
    }
}