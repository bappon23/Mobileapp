package com.example.appbanhang.service;

import com.example.appbanhang.app.models.User;
import com.example.appbanhang.response.LoginResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitService {

    @FormUrlEncoded
    @POST("/login")
    Call<LoginResponse> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("/get_all_users")
    Call<List<User>> getAllUsers();

    @DELETE("/delete_user/{username}")
    Call<Void> deleteUserByUsername(@Path("username") String username);

    @PUT("/update_password/{username}")
    Call<Void> updateUserPassword(@Path("username") String username, @Body String newPassword);
}

