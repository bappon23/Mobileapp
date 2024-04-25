package com.example.appbanhang.app.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appbanhang.R;
import com.example.appbanhang.app.models.CartItem;

import java.util.List;

public class CartItemAdapter extends ArrayAdapter<CartItem> {

    private Context mContext;
    private SparseArray<Integer> productIdQuantityMap;
    private OnDeleteItemClickListener mListener;

    public CartItemAdapter(@NonNull Context context, @NonNull List<CartItem> objects, OnDeleteItemClickListener listener) {
        super(context, 0, objects);
        mContext = context;
        productIdQuantityMap = new SparseArray<>();
        mergeItems(objects);
        mListener = listener;
    }

    private void mergeItems(List<CartItem> items) {
        for (CartItem item : items) {
            int productId = item.getProductId();
            int quantity = item.getQuantity();
            if (productIdQuantityMap.get(productId) != null) {
                quantity += productIdQuantityMap.get(productId);
            }
            productIdQuantityMap.put(productId, quantity);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.cart_item_layout, parent, false);
            holder = new ViewHolder();
            holder.textViewProductName = view.findViewById(R.id.textViewProductName);
            holder.textViewQuantity = view.findViewById(R.id.textViewQuantity);
            holder.textViewProductPrice = view.findViewById(R.id.textViewPrice);
            holder.textViewTotalPrice = view.findViewById(R.id.textViewTotalPrice);
            holder.btnDelete = view.findViewById(R.id.btnDelete);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        CartItem currentItem = getItem(position);
        if (currentItem != null) {
            holder.textViewProductName.setText(currentItem.getProductName());
            holder.textViewQuantity.setText(String.valueOf(currentItem.getQuantity()));

            double productPrice = currentItem.getProductPrice();
            int quantity = currentItem.getQuantity();
            double totalPrice = productPrice * quantity;
            holder.textViewProductPrice.setText(String.valueOf(productPrice));
            holder.textViewTotalPrice.setText(String.valueOf(totalPrice));

            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onDeleteItemClick(currentItem.getProductId());
                    }
                }
            });

        }

        return view;
    }

    static class ViewHolder {
        TextView textViewProductName;
        TextView textViewQuantity;
        TextView textViewProductPrice;
        TextView textViewTotalPrice;
        ImageButton btnDelete;
    }

    public interface OnDeleteItemClickListener {
        void onDeleteItemClick(int cartItemId);
    }
}
