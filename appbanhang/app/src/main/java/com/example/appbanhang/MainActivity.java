package com.example.appbanhang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appbanhang.app.activity.HomeActivity;
import com.example.appbanhang.app.activity.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNav);

        // Chọn mặc định mục Home khi mở ứng dụng
        bottomNav.setSelectedItemId(R.id.menu_home);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Kiểm tra ID của MenuItem được chọn và thực thi hành động tương ứng
                if (item.getItemId() == R.id.menu_home) {
                    Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                    return true;
                } else if (item.getItemId() == R.id.menu_search) {
                    Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(searchIntent);
                    return true;
                } else if (item.getItemId() == R.id.menu_cart) {
                    Intent cartIntent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(cartIntent);
                    return true;
                }
                return false;
            }
        });


    }


}
