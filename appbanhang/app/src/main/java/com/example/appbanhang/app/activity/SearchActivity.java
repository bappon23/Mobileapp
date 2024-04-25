package com.example.appbanhang.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.MainActivity;
import com.example.appbanhang.R;
import com.example.appbanhang.app.adapter.ProductAdapter;
import com.example.appbanhang.app.models.Product;
import com.example.appbanhang.service.ApiService;
import com.example.appbanhang.service.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ProgressBar progressBar;
    private ApiService apiService;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bottomNav = findViewById(R.id.bottomNav);

        // Chọn mặc định mục Home khi mở ứng dụng
        bottomNav.setSelectedItemId(R.id.menu_search);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Kiểm tra ID của MenuItem được chọn và thực thi hành động tương ứng
                if (item.getItemId() == R.id.menu_home) {
                    Intent homeIntent = new Intent(SearchActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                    return true;
                } else if (item.getItemId() == R.id.menu_search) {

                    return true;
                } else if (item.getItemId() == R.id.menu_cart) {
                    Intent cartIntent = new Intent(SearchActivity.this, CartActivity.class);
                    startActivity(cartIntent);
                    return true;
                }
                else if(item.getItemId()==R.id.menu_logout){
                    Intent cartIntent = new Intent(SearchActivity.this, LoginActivity.class);
                    startActivity(cartIntent);
                }
                return false;
            }
        });


        // Initialize ApiService
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerViewSearch);
        SearchView searchView = findViewById(R.id.searchView);
        progressBar = findViewById(R.id.progressBar);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(this, null); // Initially pass null
        recyclerView.setAdapter(productAdapter);

        // Handle SearchView events
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform search when user submits query
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Perform search when user changes text (optional)
                performSearch(newText);
                return true;
            }
        });
    }

    // Method to perform search
    private void performSearch(String query) {
        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);

        // Send request to search products by name to the API
        Call<List<Product>> call = apiService.searchProductsByName(query);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                // Hide progress bar
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    // Display search results
                    List<Product> productList = response.body();
                    displaySearchResults(productList);
                } else {
                    Toast.makeText(SearchActivity.this, "Failed to search products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // Hide progress bar
                progressBar.setVisibility(View.GONE);

                Toast.makeText(SearchActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to display search results on RecyclerView
    private void displaySearchResults(List<Product> productList) {
        productAdapter = new ProductAdapter(SearchActivity.this, productList);
        recyclerView.setAdapter(productAdapter);
    }
}
