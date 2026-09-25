package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Product product, int position);
    }

    public ProductAdapter(List<Product> productList, OnItemClickListener listener, OnItemLongClickListener longClickListener) {
        this.productList = productList;
        this.listener = listener;
        this.longClickListener = longClickListener;
    }

    public void filterList(List<Product> filteredList) {
        this.productList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.tvProductName.setText(product.getName());
        DecimalFormat formatter = new DecimalFormat("#,###");
        holder.tvProductPrice.setText(formatter.format(product.getPrice()) + " đ");

        // ĐÂY LÀ DÒNG LỆNH MỚI ĐỂ HIỂN THỊ ĐÚNG ẢNH CỦA TỪNG SẢN PHẨM:
        // Lưu ý: Nếu chữ getImage() bị đỏ, hãy đổi nó thành tên hàm trong file Product.java của bạn (Ví dụ: getHinhAnh() hoặc getImg() ...)
        holder.imgProduct.setImageResource(product.getImage());

        // Bấm vào nút "+ Mua" hoặc ô sản phẩm
        holder.itemView.setOnClickListener(v -> listener.onItemClick(product));
        holder.btnBuyItem.setOnClickListener(v -> listener.onItemClick(product));

        // Nhấn giữ lâu
        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onItemLongClick(product, holder.getAdapterPosition());
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvProductName, tvProductPrice;
        Button btnBuyItem;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            btnBuyItem = itemView.findViewById(R.id.btnBuyItem);
        }
    }
}