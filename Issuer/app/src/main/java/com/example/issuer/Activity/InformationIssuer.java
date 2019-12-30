package com.example.issuer.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.issuer.R;

public class InformationIssuer extends AppCompatActivity {

    private EditText name, founding, location, email, password, address;
    private Button edit;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informationissuer);

        findView();
        getData();
        setEvent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void setEvent() {
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEdit) {
                    isEdit = true;
                    edit.setText("Save");
                    setEnableOrDisable(true);
                } else {
                    isEdit = false;
                    edit.setText("Edit");
                    setEnableOrDisable(false);

                    Toast.makeText(InformationIssuer.this, "Save!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(InformationIssuer.this, IssuerActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void setEnableOrDisable(boolean boo){
        name.setEnabled(boo);
        founding.setEnabled(boo);
        location.setEnabled(boo);
        email.setEnabled(boo);
        password.setEnabled(boo);
    }

    private void getData() {
        SharedPreferences sharedPreferences = getSharedPreferences("issuer_info", Context.MODE_PRIVATE);
        name.setText(sharedPreferences.getString("name", ""));
        address.setText(sharedPreferences.getString("address", ""));
        founding.setText(sharedPreferences.getString("founding", ""));
        location.setText(sharedPreferences.getString("location", ""));
        email.setText(sharedPreferences.getString("email", ""));
        password.setText(sharedPreferences.getString("password", ""));
    }

    private void findView() {
        name = findViewById(R.id.information_name);
        founding = findViewById(R.id.information_founding);
        location = findViewById(R.id.information_location);
        edit = findViewById(R.id.information_edit_company);
        email = findViewById(R.id.information_email);
        password = findViewById(R.id.information_password);
        address = findViewById(R.id.information_address);
    }
}
