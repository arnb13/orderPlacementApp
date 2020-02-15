package com.example.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public String email= "default";
    public TextView textView;
    public Button buttonLogin;
    public Button buttonsignUp;
    public Button buttonlogOut;
    public Button buttonProduct;

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        buttonLogin = (Button) findViewById(R.id.button_login);
        buttonlogOut = (Button) findViewById(R.id.button_logout);
        buttonsignUp = (Button) findViewById(R.id.button_signup);
        buttonProduct = (Button) findViewById(R.id.button_product);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        email = sharedPreferences.getString("email", "");
        textView.setText("Please Login or Create new ID!");
        buttonsignUp.setVisibility(View.VISIBLE);
        buttonLogin.setVisibility(View.VISIBLE);
        buttonProduct.setVisibility(View.GONE);
        buttonlogOut.setVisibility(View.GONE);

        if(email.length() > 0) {
            textView.setText("Welcome " + email + "!");
            buttonsignUp.setVisibility(View.GONE);
            buttonLogin.setVisibility(View.GONE);
            buttonProduct.setVisibility(View.VISIBLE);
            buttonlogOut.setVisibility(View.VISIBLE);

        }

    }

    public void signUp(View v) {
        Intent intent = new Intent(this, signIn.class);
        startActivityForResult(intent, 0);
    }

    public void logIn(View v) {
        Intent intent = new Intent(this, login.class);
        startActivityForResult(intent, 0);
    }

    public void product(View v) {
        Intent intent = new Intent(this, AllProduct.class);
        startActivityForResult(intent, 0);
    }

    public void logout(View v) {
        editor.remove("email");
        editor.commit();
        finish();
        startActivity(getIntent());
    }
}
