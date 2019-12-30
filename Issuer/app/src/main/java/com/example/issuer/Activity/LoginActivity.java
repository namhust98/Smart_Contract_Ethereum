package com.example.issuer.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.issuer.R;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private AutoCompleteTextView email;
    private EditText password;
    private String stremail, strpassword;
    private LinearLayout register;
    private TextView textRegister, error;
    private View lineRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findView();
        buttonSetOnClick();
    }

    private void buttonSetOnClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strpassword = password.getText().toString();
                stremail = email.getText().toString();

                if (stremail.equals("")) {
                    email.setError("Email is null");
                } else if (strpassword.equals("")) {
                    password.setError("Password is null");
                } else if ((!stremail.equals("nam")) || (!strpassword.equals("123"))) {
                    error.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Someone wrong, please check again", Toast.LENGTH_SHORT).show();
                } else {
                    //check conditional

                    SharedPreferences sharedPreferences = getSharedPreferences("issuer_info", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", "Nguyen Hai Nam");
                    editor.putString("address", "0x13b123c1345d12f12ea5345f898b89ca");
                    editor.putString("founding", "20/02/1990");
                    editor.putString("location", "Ha Noi");
                    editor.putString("email", "nam");
                    editor.putString("password", "123");
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
                    startActivity(intent);
                    error.setVisibility(View.GONE);
                    email.setText("");
                    password.setText("");

                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textRegister.setTextColor(Color.parseColor("#990099"));
                lineRegister.setBackgroundColor(Color.parseColor("#990099"));
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findView() {
        btnLogin = findViewById(R.id.signin_button);
        email = findViewById(R.id.signin_email);
        password = findViewById(R.id.signin_password);
        register = findViewById(R.id.signin_register);
        textRegister = findViewById(R.id.text_register);
        lineRegister = findViewById(R.id.line_register);
        error = findViewById(R.id.error_login);
    }
}
