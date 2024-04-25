package com.example.appbanhang.app.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhang.R;
import com.example.appbanhang.app.models.Product;
import com.example.appbanhang.service.ApiService;
import com.example.appbanhang.service.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminViewProductActivity extends AppCompatActivity {
    private ListView listViewProducts;
    private ProductArrayAdapter adapter;
    private ApiService apiService;
    private Button btnAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_products);

        listViewProducts = findViewById(R.id.listProducts);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        adapter = new ProductArrayAdapter(this);
        listViewProducts.setAdapter(adapter);

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        getProductsAndUpdateListView();

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductDialog();
            }
        });
    }

    private void addProductDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm sản phẩm");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_product, null);
        builder.setView(dialogView);

        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
        final EditText editTextDescription = dialogView.findViewById(R.id.editTextDescription);
        final EditText editTextImage = dialogView.findViewById(R.id.editTextImage);
        final EditText editTextPrice = dialogView.findViewById(R.id.editTextPrice);
        final EditText editTextType = dialogView.findViewById(R.id.editTextType);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String productName = editTextName.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();
                String image = editTextImage.getText().toString().trim();
                String priceText = editTextPrice.getText().toString().trim();
                String typeText = editTextType.getText().toString().trim();

                if (TextUtils.isEmpty(productName) || TextUtils.isEmpty(description) || TextUtils.isEmpty(image) || TextUtils.isEmpty(priceText) || TextUtils.isEmpty(typeText)) {
                    Toast.makeText(AdminViewProductActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                double price = Double.parseDouble(priceText);
                int type = Integer.parseInt(typeText);

                Product newProduct = new Product(0, productName, description, image, price, type);

                addProduct(newProduct);
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void getProductsAndUpdateListView() {
        Call<List<Product>> call = apiService.getAllProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> products = response.body();
                    if (products != null && products.size() > 0) {
                        adapter.clear();
                        adapter.addAll(products);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(AdminViewProductActivity.this, "Không thể lấy dữ liệu sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(AdminViewProductActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addProduct(Product newProduct) {
        Call<Void> call = apiService.createProduct(newProduct);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    getProductsAndUpdateListView();
                    Toast.makeText(AdminViewProductActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminViewProductActivity.this, "Thêm sản phẩm không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AdminViewProductActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleDeleteProduct(int productId) {
        Call<Void> call = apiService.deleteProductById(productId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    getProductsAndUpdateListView();
                    Toast.makeText(AdminViewProductActivity.this, "Xoá sản phẩm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminViewProductActivity.this, "Xoá sản phẩm không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AdminViewProductActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleUpdateProduct(final int productId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cập nhật sản phẩm");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_update_product, null);
        builder.setView(dialogView);

        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
        final EditText editTextDescription = dialogView.findViewById(R.id.editTextDescription);
        final EditText editTextImage = dialogView.findViewById(R.id.editTextImage);
        final EditText editTextPrice = dialogView.findViewById(R.id.editTextPrice);
        final EditText editTextType = dialogView.findViewById(R.id.editTextType);

        builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String updatedName = editTextName.getText().toString().trim();
                String updatedDescription = editTextDescription.getText().toString().trim();
                String updatedImage = editTextImage.getText().toString().trim();
                String priceText = editTextPrice.getText().toString().trim();
                double updatedPrice = 0.0; // Default value if the text is empty

                if (!priceText.isEmpty()) {
                    updatedPrice = Double.parseDouble(priceText);
                }

                String updatedType = editTextType.getText().toString().trim();

                Product updatedProduct = new Product(productId, updatedName, updatedDescription, updatedImage, updatedPrice, 0);

                updateProduct(productId, updatedProduct);
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void updateProduct(int productId, Product updatedProduct) {
        Call<Void> call = apiService.updateProduct(productId, updatedProduct);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    getProductsAndUpdateListView();
                    Toast.makeText(AdminViewProductActivity.this, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminViewProductActivity.this, "Cập nhật sản phẩm không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AdminViewProductActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class ProductArrayAdapter extends ArrayAdapter<Product> {
        public ProductArrayAdapter(Context context) {
            super(context, R.layout.product_list_item);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                view = inflater.inflate(R.layout.product_list_item, parent, false);
            }

            final Product product = getItem(position);

            TextView textViewProductName = view.findViewById(R.id.textViewProductName);
            ImageView imageViewEdit = view.findViewById(R.id.imageViewEdit);
            ImageView imageViewDelete = view.findViewById(R.id.imageViewDelete);

            if (product != null) {
                textViewProductName.setText(product.getName());
                imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleDeleteProduct(product.getId());
                    }
                });

                imageViewEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleUpdateProduct(product.getId());
                    }
                });
            }

            return view;
        }
    }
}
