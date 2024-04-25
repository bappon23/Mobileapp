package com.example.appbanhang.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhang.R;

public class AdminHomeActivity extends AppCompatActivity {
    private Button btnProductList;
    private Button btnUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        // Ánh xạ các thành phần UI
        btnProductList = findViewById(R.id.btnProductList);
        btnUserList = findViewById(R.id.btnUserList);

        // Sự kiện khi nhấn vào nút danh sách sản phẩm
        btnProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang trang danh sách sản phẩm
                startActivity(new Intent(AdminHomeActivity.this, AdminViewProductActivity.class));
            }
        });

        // Sự kiện khi nhấn vào nút danh sách người dùng
        btnUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang trang danh sách người dùng
                startActivity(new Intent(AdminHomeActivity.this, AdminViewUsersActivity.class));
            }
        });
    }
}
