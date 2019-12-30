package com.example.issuer.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.issuer.R;
import com.example.issuer.Web3jEthereum.Roles.Issuer;

public class ActiveCoupon extends AppCompatActivity {

    private Button continues;
    private String qr_text;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_coupon);

        context = getApplicationContext();
        continues = findViewById(R.id.continues_button);

        qr_text = getIntent().getStringExtra("qr");

        continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActiveCoupon.this, IssuerActivity.class);
                startActivity(intent);
                Toast.makeText(context, "Please wait a few minutes", Toast.LENGTH_LONG).show();
                new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        Issuer iss = new Issuer("227955d7ff410da1c6f0e50c8f8280d7ee37ca75590302b69b7b66797d34f968");
                        try {
                            iss.confirmUsingCoupon(qr_text);
                        } catch (Exception e) {
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        Toast.makeText(context, "Active Coupon is Success!", Toast.LENGTH_SHORT).show();
                    }
                }.execute();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActiveCoupon.this, IssuerActivity.class);
        startActivity(intent);
    }
}
