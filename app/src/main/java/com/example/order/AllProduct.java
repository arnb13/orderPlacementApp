package com.example.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllProduct extends AppCompatActivity implements RecyclerAdapter.OnOrderListener {

    public ArrayList<String> arrayList_name = new ArrayList<>();
    public ArrayList<String> arrayList_photo = new ArrayList<>();


    public JsonPlaceHolderApi jsonPlaceHolderApi;
    public Toast t;

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Product>> call = jsonPlaceHolderApi.getProduct();


        call.enqueue(new Callback<List<Product>>() {

            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> products = response.body();
                    for (Product product : products) {
                        arrayList_name.add(product.getProduct_name());
                        arrayList_photo.add("http://10.0.2.2:8000" + product.getPhoto());
                    }

                    imitRecyclerView();
                }

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

    }

    private void imitRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.view_product);
        RecyclerAdapter adapter = new RecyclerAdapter(arrayList_name, arrayList_photo, this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    public void OnOrderClick(int position) {
        Intent intent = new Intent(this, PlaceOrder.class);
        intent.putExtra("product_name", arrayList_name.get(position));
        intent.putExtra("photo", arrayList_photo.get(position));
        startActivityForResult(intent, 0);
    }
}
