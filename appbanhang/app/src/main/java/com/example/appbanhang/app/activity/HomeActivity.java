package com.example.appbanhang.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.MainActivity;
import com.example.appbanhang.R;
import com.example.appbanhang.app.adapter.ProductAdapter;
import com.example.appbanhang.app.models.Product;
import com.example.appbanhang.service.ApiService;
import com.example.appbanhang.service.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements ProductAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ViewFlipper viewFlipper;

    private BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewFlipper = findViewById(R.id.viewFlipper);
        startViewFlipperAnimation();

        bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.menu_home);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Kiểm tra ID của MenuItem được chọn và thực thi hành động tương ứng
                if (item.getItemId() == R.id.menu_home) {
                    return true;
                } else if (item.getItemId() == R.id.menu_search) {
                    Intent searchIntent = new Intent(HomeActivity.this, SearchActivity.class);
                    startActivity(searchIntent);
                    return true;
                } else if (item.getItemId() == R.id.menu_cart) {
                    Intent cartIntent = new Intent(HomeActivity.this, CartActivity.class);
                    startActivity(cartIntent);
                    return true;
                } else if(item.getItemId()==R.id.menu_logout){
                    Intent cartIntent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(cartIntent);
                }
                return false;
            }
        });



        getProductList();
    }

    private void startViewFlipperAnimation() {
        List<String> ads = new ArrayList<>();
        ads.add("https://nhaccudantoc.com.vn/wp-content/uploads/2021/05/nhac-cu-hien-dai-dep-1.jpeg");
        ads.add("https://nhaccudantoc.com.vn/wp-content/uploads/2021/05/banner-nhac-cu-hien-dai.jpeg");

        for (String imageUrl : ads) {
            flipperImages(imageUrl);
        }

        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

        viewFlipper.setInAnimation(in);
        viewFlipper.setOutAnimation(out);

        // Set interval
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
    }


    // Chọn mặc định mục Home khi mở ứng dụng


    private void flipperImages(String imageUrl) {
        ImageView imageView = new ImageView(this);
        Glide.with(this).load(imageUrl).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        viewFlipper.addView(imageView);
    }

    private void getProductList() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Product>> call = apiService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> productList = response.body();
                    if (productList != null) {
                        displayProductList(productList);
                    } else {
                        Toast.makeText(HomeActivity.this, "No products found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Failed to fetch products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                String errorMessage = "Error: " + t.getMessage();
                System.out.println(errorMessage);
                Toast.makeText(HomeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void displayProductList(List<Product> productList) {
        productAdapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(productAdapter);
        productAdapter.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(Product product) {
        // Khi người dùng chọn một sản phẩm, chuyển sang ProductDetailActivity
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("productId", product.getId());
        startActivity(intent);
    }
}
