package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private int totalCount = 0;
    private long totalMoney = 0;
    private TextView tvCartShopee;
    private DecimalFormat formatter = new DecimalFormat("#,###");
    private Map<Product, Integer> cartMap = new LinkedHashMap<>();
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCartShopee = findViewById(R.id.tvCartShopee);
        RecyclerView rcvProducts = findViewById(R.id.rcvProducts);
        EditText edtSearch = findViewById(R.id.edtSearch);

        TextView btnShopeePay = findViewById(R.id.btnShopeePay);
        TextView btnDiemDanh = findViewById(R.id.btnDiemDanh);
        TextView btnSPayLater = findViewById(R.id.btnSPayLater);
        TextView btnProfile = findViewById(R.id.btnProfile);

        rcvProducts.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new ProductAdapter(DataManager.productList,
                // 1. KHI NHẤN BÌNH THƯỜNG -> THÊM VÀO GIỎ HÀNG
                product -> {
                    int currentQty = cartMap.getOrDefault(product, 0);
                    cartMap.put(product, currentQty + 1);
                    totalCount++;
                    totalMoney += product.getPrice();
                    updateCart();
                    Toast.makeText(MainActivity.this, "Đã thêm vào giỏ!", Toast.LENGTH_SHORT).show();
                },
                // 2. KHI NHẤN GIỮ LÂU -> HIỆN MENU SỬA/XÓA (CHỈ DÀNH CHO ADMIN)
                (product, position) -> {
                    if (LoginActivity.isAdmin) {
                        showEditDeleteDialog(product, position);
                    }
                }
        );
        rcvProducts.setAdapter(adapter);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().toLowerCase().trim();
                List<Product> searchResults = new ArrayList<>();
                for (Product p : DataManager.productList) {
                    if (p.getName().toLowerCase().contains(keyword)) searchResults.add(p);
                }
                adapter.filterList(searchResults);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnShopeePay.setOnClickListener(v -> Toast.makeText(this, "💳 Đang mở Ví ShopeePay...", Toast.LENGTH_SHORT).show());
        btnDiemDanh.setOnClickListener(v -> Toast.makeText(this, "🎉 Điểm danh thành công!", Toast.LENGTH_SHORT).show());
        btnSPayLater.setOnClickListener(v -> Toast.makeText(this, "📉 SPayLater: Hạn mức 5.000.000đ", Toast.LENGTH_SHORT).show());

        tvCartShopee.setOnClickListener(v -> {
            if (cartMap.isEmpty()) Toast.makeText(this, "Giỏ hàng đang trống!", Toast.LENGTH_SHORT).show();
            else if (LoginActivity.userName.isEmpty()) {
                Toast.makeText(this, "Vui lòng đăng nhập để thanh toán!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            } else showCheckoutDialog();
        });

        btnProfile.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            overridePendingTransition(0, 0);
        });
    }

    // HÀM HIỆN BẢNG CHỌN SỬA HAY XÓA
    private void showEditDeleteDialog(Product product, int position) {
        String[] options = {"✏️ Sửa sản phẩm", "🗑️ Xóa sản phẩm (Tháo xuống)"};
        new AlertDialog.Builder(this)
                .setTitle("Quản lý: " + product.getName())
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        // CHỌN SỬA: Chuyển sang màn hình AddProduct nhưng mang theo vị trí (position) để sửa
                        Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
                        intent.putExtra("edit_index", position);
                        startActivity(intent);
                    } else if (which == 1) {
                        // CHỌN XÓA: Hỏi lại lần nữa cho chắc
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Xác nhận xóa")
                                .setMessage("Bạn có chắc chắn muốn xóa " + product.getName() + " khỏi kho hàng?")
                                .setPositiveButton("Xóa ngay", (d, w) -> {
                                    DataManager.productList.remove(position); // Xóa khỏi danh sách
                                    adapter.notifyDataSetChanged(); // Yêu cầu vẽ lại giao diện
                                    Toast.makeText(MainActivity.this, "Đã xóa sản phẩm!", Toast.LENGTH_SHORT).show();
                                })
                                .setNegativeButton("Hủy", null)
                                .show();
                    }
                })
                .show();
    }

    private void updateCart() { tvCartShopee.setText("🛒 " + totalCount); }

    private void showCheckoutDialog() {
        // (Giữ nguyên phần Checkout cũ)
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_checkout, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        TextView tvOrderDetails = dialogView.findViewById(R.id.tvOrderDetails);
        TextView tvOrderTotalPrice = dialogView.findViewById(R.id.tvOrderTotalPrice);
        RadioGroup rgPayment = dialogView.findViewById(R.id.rgPayment);
        LinearLayout layoutQRInfo = dialogView.findViewById(R.id.layoutQRInfo);
        Button btnConfirmPayment = dialogView.findViewById(R.id.btnConfirmPayment);
        Button btnCancelPayment = dialogView.findViewById(R.id.btnCancelPayment);

        StringBuilder summary = new StringBuilder();
        for (Map.Entry<Product, Integer> entry : cartMap.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            summary.append("• ").append(p.getName()).append("\n  SL: ").append(qty).append(" | ").append(formatter.format(p.getPrice() * qty)).append(" đ\n\n");
        }
        tvOrderDetails.setText(summary.toString().trim());
        tvOrderTotalPrice.setText("Tổng: " + formatter.format(totalMoney) + " đ");

        rgPayment.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbQR) layoutQRInfo.setVisibility(View.VISIBLE);
            else layoutQRInfo.setVisibility(View.GONE);
        });

        btnConfirmPayment.setOnClickListener(v -> {
            Toast.makeText(this, "🎉 Đặt hàng thành công!", Toast.LENGTH_LONG).show();
            cartMap.clear(); totalCount = 0; totalMoney = 0; updateCart(); dialog.dismiss();
            ((EditText) findViewById(R.id.edtSearch)).setText("");
        });
        btnCancelPayment.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) adapter.notifyDataSetChanged();
    }
}