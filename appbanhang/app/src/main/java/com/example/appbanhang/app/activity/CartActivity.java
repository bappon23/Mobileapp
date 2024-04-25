package com.example.appbanhang.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhang.R;
import com.example.appbanhang.app.activity.HomeActivity;
import com.example.appbanhang.app.activity.LoginActivity;
import com.example.appbanhang.app.activity.SearchActivity;
import com.example.appbanhang.app.adapter.CartItemAdapter;
import com.example.appbanhang.app.models.CartItem;
import com.example.appbanhang.service.ApiService;
import com.example.appbanhang.service.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements CartItemAdapter.OnDeleteItemClickListener {

    private ListView listViewCartItems;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.menu_cart);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Kiểm tra ID của MenuItem được chọn và thực thi hành động tương ứng
                if (item.getItemId() == R.id.menu_home) {
                    Intent searchIntent = new Intent(CartActivity.this, HomeActivity.class);
                    startActivity(searchIntent);
                    return true;
                } else if (item.getItemId() == R.id.menu_search) {
                    Intent searchIntent = new Intent(CartActivity.this, SearchActivity.class);
                    startActivity(searchIntent);
                    return true;
                } else if (item.getItemId() == R.id.menu_cart) {
                    return true;
                } else if(item.getItemId()==R.id.menu_logout){
                    Intent cartIntent = new Intent(CartActivity.this, LoginActivity.class);
                    startActivity(cartIntent);
                }
                return false;
            }
        });
        listViewCartItems = findViewById(R.id.list_cart_items);

        fetchCartItems();
    }

    private void fetchCartItems() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<List<CartItem>> call = apiService.getCartItems();
        call.enqueue(new Callback<List<CartItem>>() {
            @Override
            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                if (response.isSuccessful()) {
                    List<CartItem> cartItems = response.body();
                    CartItemAdapter adapter = new CartItemAdapter(CartActivity.this, cartItems, CartActivity.this);
                    listViewCartItems.setAdapter(adapter);
                } else {
                    Toast.makeText(CartActivity.this, "Lỗi khi lấy danh sách các mục trong giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CartItem>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDeleteItemClick(int cartItemId) {
        // Call your backend API to delete the item
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<Void> call = apiService.removeCartItem(cartItemId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Item deleted successfully, refresh cart items
                    fetchCartItems();
                    Toast.makeText(CartActivity.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CartActivity.this, "Failed to delete item", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
