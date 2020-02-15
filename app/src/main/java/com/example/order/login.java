package com.example.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login extends AppCompatActivity {

    public String email;
    public String password;

    public JsonPlaceHolderApi jsonPlaceHolderApi;
    public Toast t;

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View v) {
        email = (((EditText)findViewById(R.id.text_email)).getText()).toString();
        password = (((EditText)findViewById(R.id.text_password)).getText()).toString();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        UserLogin userLogin = new UserLogin(email = email, password= password);

        Call<UserLogin> call = jsonPlaceHolderApi.loginUser(userLogin);



        call.enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                if (response.isSuccessful()) {
                    t = Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT);
                    editor.putString("email", email);
                    editor.commit();
                    t.show();
                    gotoMainActivity();
                } else {
                    t = Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_SHORT);
                    t.show();
                }
            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable th) {
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
