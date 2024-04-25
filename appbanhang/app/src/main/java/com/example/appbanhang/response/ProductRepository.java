package com.example.appbanhang.response;

import com.example.appbanhang.app.models.Product;
import com.example.appbanhang.service.ApiService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {

    private ApiService apiService;

    public ProductRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void searchProductsByName(String query, final OnSearchCompleteListener listener) {
        Call<List<Product>> call = apiService.searchProductsByName(query);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> productList = response.body();
                    listener.onSearchSuccess(productList);
                } else {
                    listener.onSearchFailure("Failed to search products");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                listener.onSearchFailure(t.getMessage());
            }
        });
    }

    public interface OnSearchCompleteListener {
        void onSearchSuccess(List<Product> productList);
        void onSearchFailure(String errorMessage);
    }
}
