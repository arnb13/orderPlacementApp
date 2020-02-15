package com.example.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceOrder extends AppCompatActivity {

    public String email;
    public String product_name;
    public String photo;
    public String amount;

    public TextView textView;
    public ImageView imageView;

    public JsonPlaceHolderApi jsonPlaceHolderApi;
    public Toast t;

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        email = sharedPreferences.getString("email", "");
        product_name = getIntent().getStringExtra("product_name");
        photo = getIntent().getStringExtra("photo");

        textView = (TextView) findViewById(R.id.text_product_name);
        imageView = (ImageView) findViewById(R.id.image);

        Glide.with(this)
                .asBitmap()
                .load(photo)
                .into(imageView);

        textView.setText(product_name);
    }

    public void placeOrder(View v) {
        amount = (((EditText)findViewById(R.id.text_amount)).getText()).toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Order order = new Order(email, product_name, amount);

        Call<Order> call = jsonPlaceHolderApi.createOrder(order);

        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    t = Toast.makeText(getApplicationContext(), "New order placed.", Toast.LENGTH_SHORT);
                    t.show();
                    gotoAllProduct();

                } else {
                    t = Toast.makeText(getApplicationContext(), "Order placement failed", Toast.LENGTH_SHORT);
                    t.show();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable th) {
                t = Toast.makeText(getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT);
                t.show();

            }
        });

    }

    public void gotoAllProduct(){
        Intent intent = new Intent(this, AllProduct.class);
        startActivityForResult(intent, 0);
    }


}
