package com.example.appbanhang.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhang.R;
import com.example.appbanhang.app.models.User;
import com.example.appbanhang.service.RetrofitClient;
import com.example.appbanhang.service.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminViewUsersActivity extends AppCompatActivity {
    private ListView listViewUsers;
    private ArrayAdapter<User> adapter;
    private RetrofitService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users);

        listViewUsers = findViewById(R.id.listViewUsers);

        adapter = new UserArrayAdapter(this);
        listViewUsers.setAdapter(adapter);

        apiService = RetrofitClient.getRetrofitInstance().create(RetrofitService.class);

        getUsersAndUpdateListView();
    }

    private void getUsersAndUpdateListView() {
        Call<List<User>> call = apiService.getAllUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> users = response.body();
                    if (users != null && users.size() > 0) {
                        adapter.clear();
                        adapter.addAll(users);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUsersAndUpdateListView();
    }

    private void handleDeleteUser(String username) {
        Call<Void> call = apiService.deleteUserByUsername(username);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    getUsersAndUpdateListView();
                    Toast.makeText(AdminViewUsersActivity.this, "Xoá người dùng thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminViewUsersActivity.this, "Xoá người dùng không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AdminViewUsersActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleUpdatePassword(String username) {

    }

    private class UserArrayAdapter extends ArrayAdapter<User> {
        public UserArrayAdapter(Context context) {
            super(context, R.layout.user_list_item);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                view = inflater.inflate(R.layout.user_list_item, parent, false);
            }

            final User user = getItem(position);

            TextView textViewUserName = view.findViewById(R.id.textViewUserName);
            ImageView imageViewEdit = view.findViewById(R.id.imageViewEdit);
            ImageView imageViewDelete = view.findViewById(R.id.imageViewDelete);

            if (user != null) {
                textViewUserName.setText(user.getUsername());
                imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleDeleteUser(user.getUsername());
                    }
                });

                imageViewEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleUpdatePassword(user.getUsername());
                    }
                });
            }

            return view;
        }
    }
}
