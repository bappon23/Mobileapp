package com.example.appbanhang.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhang.R;
import com.example.appbanhang.app.adapter.CartItemRequest;
import com.example.appbanhang.app.models.CartItem;
import com.example.appbanhang.app.models.Product;
import com.example.appbanhang.service.ApiService;
import com.example.appbanhang.service.RetrofitClient;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    private TextView tvProductName, tvProductDescription, tvProductPrice;
    private ImageView ivProductImage;
    private Spinner spinnerQuantity;
    private ImageButton btnBack;
    private Button btnAddToCart;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        tvProductName = findViewById(R.id.txt_tensanpham);
        tvProductDescription = findViewById(R.id.txtmotachitiet);
        tvProductPrice = findViewById(R.id.txt_giasanpham);
        ivProductImage = findViewById(R.id.img_chitiet);
        spinnerQuantity = findViewById(R.id.spiner);
        btnBack = findViewById(R.id.btn_back);
        btnAddToCart = findViewById(R.id.btn_themgiohang);

        int productId = getIntent().getIntExtra("productId", -1);
        username = getIntent().getStringExtra("username");

        setupQuantitySpinner();

        getProductDetails(productId);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(spinnerQuantity.getSelectedItem().toString());
                addToCart(productId, quantity);
            }
        });
    }

    private void setupQuantitySpinner() {
        String[] quantityArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, quantityArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerQuantity.setAdapter(adapter);
    }

    private void getProductDetails(int productId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<Product> call = apiService.getProductById(productId);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Product product = response.body();
                    if (product != null) {
                        displayProductDetails(product);
                    } else {
                        Toast.makeText(ProductDetailActivity.this, "Product not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProductDetailActivity.this, "Failed to fetch product details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayProductDetails(Product product) {
        tvProductName.setText(product.getName());
        tvProductDescription.setText(product.getDescription());
        tvProductPrice.setText(String.format("Giá: %s VND", product.getPrice()));
        Picasso.get().load(product.getImage()).into(ivProductImage);
    }

    private void addToCart(int productId, int quantity) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        CartItemRequest cartItemRequest = new CartItemRequest();
        cartItemRequest.setUsername(username);
        cartItemRequest.setProductId(productId);
        cartItemRequest.setQuantity(quantity);

        Call<Void> call = apiService.addToCart(cartItemRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProductDetailActivity.this, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ProductDetailActivity.this, "Lỗi khi thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
