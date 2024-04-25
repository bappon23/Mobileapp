package com.example.appbanhang.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appbanhang.R;
import com.example.appbanhang.app.models.CartItem;

import java.util.List;

public class CartListAdapter extends ArrayAdapter<CartItem> {

    private Context context;
    private List<CartItem> cartItems;

    public CartListAdapter(Context context, List<CartItem> cartItems) {
        super(context, 0, cartItems);
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.activity_cart_item, parent, false);
        }

        // Ánh xạ các phần tử trong layout của mỗi mục trong giỏ hàng
        TextView productNameTextView = listItemView.findViewById(R.id.text_product_name);
        TextView productPriceTextView = listItemView.findViewById(R.id.text_product_price);
        TextView productQuantityTextView = listItemView.findViewById(R.id.text_product_quantity);

        // Lấy đối tượng CartItem ở vị trí hiện tại
        CartItem currentItem = getItem(position);

        // Hiển thị thông tin sản phẩm trong mục giỏ hàng
        productQuantityTextView.setText("Quantity: " + currentItem.getQuantity());

        return listItemView;
    }
}
