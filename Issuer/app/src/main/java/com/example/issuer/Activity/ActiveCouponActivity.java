package com.example.issuer.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.issuer.R;
import com.example.issuer.Web3jEthereum.Roles.Issuer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class ActiveCouponActivity extends AppCompatActivity {

    private String qr_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activecoupon);

        startScanning();
    }

    private void startScanning() {
        IntentIntegrator integrator = new IntentIntegrator(ActiveCouponActivity.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setCameraId(0);
        integrator.setPrompt("");
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Stopped scanning!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ActiveCouponActivity.this, IssuerActivity.class);
                startActivity(intent);
            } else {
                qr_text = result.getContents();
                Intent intent = new Intent(ActiveCouponActivity.this, ActiveCoupon.class);
                intent.putExtra("qr", qr_text);
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
