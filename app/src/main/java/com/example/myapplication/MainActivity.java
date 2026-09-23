package com.example.myapplication;

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
    private ProductAdapter adapter; // Khai báo adapter ở đây để dùng cho tìm kiếm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCartShopee = findViewById(R.id.tvCartShopee);
        RecyclerView rcvProducts = findViewById(R.id.rcvProducts);
        TextView btnProfile = findViewById(R.id.btnProfile);
        EditText edtSearch = findViewById(R.id.edtSearch); // Bắt lấy thanh tìm kiếm

        rcvProducts.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new ProductAdapter(DataManager.productList, product -> {
            int currentQty = cartMap.getOrDefault(product, 0);
            cartMap.put(product, currentQty + 1);
            totalCount++;
            totalMoney += product.getPrice();
            updateCart();
            Toast.makeText(MainActivity.this, "Đã thêm vào giỏ!", Toast.LENGTH_SHORT).show();
        });
        rcvProducts.setAdapter(adapter);

        // BỘ LỌC TÌM KIẾM SIÊU TỐC
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().toLowerCase().trim();
                List<Product> searchResults = new ArrayList<>();
                for (Product p : DataManager.productList) {
                    // Nếu tên sản phẩm có chứa chữ người dùng nhập vào
                    if (p.getName().toLowerCase().contains(keyword)) {
                        searchResults.add(p);
                    }
                }
                adapter.filterList(searchResults); // Cập nhật lại giao diện
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Bấm Giỏ hàng mở thanh toán
        tvCartShopee.setOnClickListener(v -> {
            if (cartMap.isEmpty()) {
                Toast.makeText(this, "Giỏ hàng đang trống!", Toast.LENGTH_SHORT).show();
            } else {
                showCheckoutDialog();
            }
        });

        btnProfile.setOnClickListener(v -> {
            Toast.makeText(this, "Sắp làm Màn hình Cá Nhân!", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateCart() {
        tvCartShopee.setText("🛒 " + totalCount);
    }

    private void showCheckoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_checkout, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        TextView tvOrderDetails = dialogView.findViewById(R.id.tvOrderDetails);
        TextView tvOrderTotalPrice = dialogView.findViewById(R.id.tvOrderTotalPrice);
        RadioGroup rgPayment = dialogView.findViewById(R.id.rgPayment);
        RadioButton rbQR = dialogView.findViewById(R.id.rbQR);
        LinearLayout layoutQRInfo = dialogView.findViewById(R.id.layoutQRInfo);
        Button btnConfirmPayment = dialogView.findViewById(R.id.btnConfirmPayment);
        Button btnCancelPayment = dialogView.findViewById(R.id.btnCancelPayment);

        StringBuilder summary = new StringBuilder();
        for (Map.Entry<Product, Integer> entry : cartMap.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            long itemTotal = p.getPrice() * qty;
            summary.append("• ").append(p.getName())
                    .append("\n  SL: ").append(qty)
                    .append(" | ").append(formatter.format(itemTotal)).append(" đ\n\n");
        }
        tvOrderDetails.setText(summary.toString().trim());
        tvOrderTotalPrice.setText("Tổng: " + formatter.format(totalMoney) + " đ");

        rgPayment.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbQR) {
                layoutQRInfo.setVisibility(View.VISIBLE);
            } else {
                layoutQRInfo.setVisibility(View.GONE);
            }
        });

        btnConfirmPayment.setOnClickListener(v -> {
            Toast.makeText(this, "🎉 Đặt hàng thành công!", Toast.LENGTH_LONG).show();
            cartMap.clear();
            totalCount = 0;
            totalMoney = 0;
            updateCart();
            dialog.dismiss();

            // Tự động xóa chữ ở thanh tìm kiếm và hiển thị lại toàn bộ sản phẩm sau khi mua
            EditText edtSearch = findViewById(R.id.edtSearch);
            edtSearch.setText("");
        });

        btnCancelPayment.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}