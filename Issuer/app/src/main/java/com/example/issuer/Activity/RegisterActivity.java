package com.example.issuer.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.issuer.R;

public class RegisterActivity extends AppCompatActivity {

    private TextView founding;
    private EditText email, password, confirmpassword, name, location;
    private String stremail, strpassword, strconfirmpassword, strname, strlocation;
    private int date, month, year;
    private Button register;

    private String strfounding = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findView();
        setIssuerClickListener();
    }

    private void setIssuerClickListener() {
        founding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.dialog_datepicker, null);
                final DatePicker datePicker = alertLayout.findViewById(R.id.founding_picker);

                AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                alert.setView(alertLayout);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        date = datePicker.getDayOfMonth();
                        month = datePicker.getMonth() + 1;
                        year = datePicker.getYear();
                        strfounding = date + "/" + month + "/" + year;
                        founding.setText(strfounding);
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stremail = email.getText().toString();
                strpassword = password.getText().toString();
                strconfirmpassword = confirmpassword.getText().toString();
                strname = name.getText().toString();
                strlocation = location.getText().toString();

                if (stremail.equals("")) {
                    email.setError("Email is null");
                } else if (strpassword.equals("")) {
                    password.setError("Password is null");
                } else if (!strconfirmpassword.equals(strpassword)) {
                    confirmpassword.setError("Confirm Password is not same as password");
                } else if (strname.equals("")) {
                    name.setError("Name is null");
                } else if (strlocation.equals("")) {
                    location.setError("Location is null");
                } else if (strfounding.equals("")) {
                    founding.setError("Set founding to continues");
                } else {
                    Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void findView() {

        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        confirmpassword = findViewById(R.id.register_confirm_password);
        name = findViewById(R.id.register_name);
        founding = findViewById(R.id.register_founding);
        location = findViewById(R.id.register_location);
        register = findViewById(R.id.register);
    }
}
