package com.example.appbanhang.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhang.R;
import com.example.appbanhang.app.models.Product;
import com.example.appbanhang.service.ApiService;
import com.example.appbanhang.service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {
    private EditText editTextProductName, editTextDescription, editTextImage, editTextPrice, editTextType;
    private Button btnAdd;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        editTextProductName = findViewById(R.id.editTextProductName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextImage = findViewById(R.id.editTextImage);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextType = findViewById(R.id.editTextType);
        btnAdd = findViewById(R.id.btnAdd);

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });
    }

    private void addProduct() {
        String productName = editTextProductName.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String image = editTextImage.getText().toString().trim();
        String priceText = editTextPrice.getText().toString().trim();
        String typeText = editTextType.getText().toString().trim(); // Nhận dữ liệu dạng chuỗi từ EditText

        if (TextUtils.isEmpty(productName) || TextUtils.isEmpty(description) || TextUtils.isEmpty(image) || TextUtils.isEmpty(priceText) || TextUtils.isEmpty(typeText)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceText);
        int type = Integer.parseInt(typeText); // Chuyển đổi chuỗi thành số nguyên

        Product newProduct = new Product(0, productName, description, image, price, type);

        Call<Void> call = apiService.createProduct(newProduct);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddProductActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddProductActivity.this, "Thêm sản phẩm không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddProductActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
