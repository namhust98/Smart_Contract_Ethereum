package com.example.bearer.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bearer.Model.Coupon;
import com.example.bearer.R;
import com.example.bearer.Web3jEthereum.Roles.Bearer;
import com.example.bearer.Web3jEthereum.Roles.General;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class GetCouponActivity extends AppCompatActivity {

    private Button getCoupon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getcoupon);

        getCoupon = findViewById(R.id.getCoupon_button);

        getCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScanning();
            }
        });
    }

    private void startScanning() {
        IntentIntegrator integrator = new IntentIntegrator(GetCouponActivity.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setCameraId(0);
        integrator.setPrompt("");
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(GetCouponActivity.this, "Stopped scanning!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(GetCouponActivity.this, BearerActivity.class);
                startActivity(intent);
            } else {
                String qr_text = result.getContents();
                Log.d("aaaaaaaaaaaaa", qr_text);
                Intent intent = new Intent(getApplicationContext(), BearerActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Please wait a few second", Toast.LENGTH_SHORT).show();
                new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        Bearer br = new Bearer("fdd865dc00505e217a4327c1f9a7ec94dc705a85da85a702ea4af93ac0acdcad");
                        try {
                            br.aquire(qr_text);
                        } catch (Exception e) {
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        Toast.makeText(getApplicationContext(), "Get Coupon Success!", Toast.LENGTH_SHORT).show();
                    }
                }.execute();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
