package com.example.appbanhang.service;

import com.example.appbanhang.app.adapter.CartItemRequest;
import com.example.appbanhang.app.models.CartItem;
import com.example.appbanhang.app.models.Product;
import com.example.appbanhang.app.models.User;
import com.example.appbanhang.response.LoginResponse;
import com.example.appbanhang.response.RegisterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("register")
    Call<RegisterResponse> registerUser(@Body User user);

    @POST("login")
    Call<LoginResponse> loginUser(@Body User user);

    @GET("products")
    Call<List<Product>> getProducts();

    @GET("products/{id}")
    Call<Product> getProductById(@Path("id") int productId);

    @POST("products")
    Call<Void> createProduct(@Body Product product);

    @PUT("products/{id}")
    Call<Void> updateProduct(@Path("id") int productId, @Body Product product);

    @DELETE("products/{id}")
    Call<Void> deleteProductById(@Path("id") int productId);

    @GET("products")
    Call<List<Product>> getAllProducts();

    @GET("products/search")
    Call<List<Product>> searchProductsByName(@Query("q") String query);

    @POST("cart/add")
    Call<Void> addToCart(@Body CartItemRequest cartItemRequest);

    @GET("cart")
    Call<List<CartItem>> getCartItems();

    @GET("cart/{productId}")
    Call<CartItem> getCartItem(@Path("productId") int productId);

    @DELETE("cart/remove/{cartItemId}")
    Call<Void> removeCartItem(@Path("cartItemId") int cartItemId);

}

