package com.example.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class signIn extends AppCompatActivity {
    public String name;
    public String email;
    public String phone;
    public String password;
    public String address;
    public String postal_code;


    public JsonPlaceHolderApi jsonPlaceHolderApi;
    public Toast t;

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

    }


    public void create_user(View v) {
        name =  (((EditText)findViewById(R.id.text_name)).getText()).toString();
        email = (((EditText)findViewById(R.id.text_email)).getText()).toString();
        phone = (((EditText)findViewById(R.id.text_phone)).getText()).toString();
        password = (((EditText)findViewById(R.id.text_password)).getText()).toString();
        address = (((EditText)findViewById(R.id.text_address)).getText()).toString();
        postal_code = (((EditText)findViewById(R.id.text_pcode)).getText()).toString();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        User user = new User(name= name, email= email, phone= phone, password= password, address= address, postal_code= postal_code);

        Call<User> call = jsonPlaceHolderApi.createUser(user);


        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    editor.putString("email", email);
                    editor.commit();
                    t = Toast.makeText(getApplicationContext(), "New user created successfully!", Toast.LENGTH_SHORT);
                    t.show();
                    gotoMainActivity();
                } else {
                    t = Toast.makeText(getApplicationContext(), "New user NOT created!", Toast.LENGTH_SHORT);
                    t.show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable th) {
                t = Toast.makeText(getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT);
                t.show();

            }
        });

    }

    public void gotoMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 0);
    }
}
