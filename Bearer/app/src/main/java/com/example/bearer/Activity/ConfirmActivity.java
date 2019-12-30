package com.example.bearer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bearer.R;


public class ConfirmActivity extends AppCompatActivity {

    private EditText pass;
    private Button continues;
    private String strpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        pass = findViewById(R.id.edt_confirm_password);
        continues = findViewById(R.id.btn_confirm_password);

        continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strpass = pass.getText().toString();
                if (strpass.equals("123")){
                    Toast.makeText(ConfirmActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ConfirmActivity.this, BearerActivity.class);
                    startActivity(intent);
                } else {
                    pass.setError("Your password are wrong, please check again");
                }
            }
        });
    }
}
