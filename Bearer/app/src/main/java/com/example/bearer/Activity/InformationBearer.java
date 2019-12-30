package com.example.bearer.Activity;

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

import com.example.bearer.R;

public class InformationBearer extends AppCompatActivity {

    private EditText name, dateofbirth, email, password, address;
    private Button edit;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informationbearer);

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

                    Toast.makeText(InformationBearer.this, "Save!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(InformationBearer.this, BearerActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void setEnableOrDisable(boolean boo){
        name.setEnabled(boo);
        dateofbirth.setEnabled(boo);
        email.setEnabled(boo);
        password.setEnabled(boo);
    }

    private void getData() {
        SharedPreferences sharedPreferences = getSharedPreferences("bearer_info", Context.MODE_PRIVATE);
        name.setText(sharedPreferences.getString("name", ""));
        address.setText(sharedPreferences.getString("address", ""));
        dateofbirth.setText(sharedPreferences.getString("date of birth", ""));
        email.setText(sharedPreferences.getString("email", ""));
        password.setText(sharedPreferences.getString("password", ""));
    }

    private void findView() {
        name = findViewById(R.id.information_name);
        dateofbirth = findViewById(R.id.information_dateofbirth);
        edit = findViewById(R.id.information_edit_company);
        email = findViewById(R.id.information_email);
        password = findViewById(R.id.information_password);
        address = findViewById(R.id.information_address);
    }
}
