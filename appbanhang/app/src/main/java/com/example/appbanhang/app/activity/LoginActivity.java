package com.example.appbanhang.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhang.MainActivity;
import com.example.appbanhang.R;
import com.example.appbanhang.app.models.User;
import com.example.appbanhang.response.LoginResponse;
import com.example.appbanhang.service.ApiService;
import com.example.appbanhang.service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.login_user);
        editTextPassword = findViewById(R.id.login_pass);
        buttonLogin = findViewById(R.id.btn_login);
        TextView textRegister = findViewById(R.id.btn_register);
        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                User user = new User(username, password, "");

                performLogin(user);
            }
        });
    }

    private void performLogin(User user) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<LoginResponse> call = apiService.loginUser(user);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null && loginResponse.getRole().equals("admin")) {
                        startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
                        Toast.makeText(LoginActivity.this, "Welcome, Admin!", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
