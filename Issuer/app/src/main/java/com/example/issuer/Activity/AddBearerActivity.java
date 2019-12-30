package com.example.issuer.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.issuer.R;
import com.example.issuer.Web3jEthereum.Roles.Issuer;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class AddBearerActivity extends AppCompatActivity {

    private Spinner campaignAddress;
    private EditText bearer;
    private String strcampaignAddress;
    private Button addBearer;
    private ArrayList<String> arrAddress = new ArrayList<>();
    private LinearLayout view, progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbearer);

        findView();
        getData();
        initSpinner();
        initEvent();
    }

    private void getData() {
        int i = SplashActivity.arrCampaign.size();
        for (int j = 0; j < i; j++) {
            arrAddress.add(SplashActivity.arrCampaign.get(j).getAddress());
        }
        view.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
    }

    private void initSpinner() {
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(AddBearerActivity.this, android.R.layout.simple_spinner_item, arrAddress);
        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        campaignAddress.setAdapter(adapter1);

        campaignAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strcampaignAddress = campaignAddress.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                strcampaignAddress = campaignAddress.getItemAtPosition(0).toString();
            }
        });
    }

    private void findView() {
        campaignAddress = findViewById(R.id.addBearer_campaignAddress);
        addBearer = findViewById(R.id.addBearer_add_button);
        bearer = findViewById(R.id.addBearer_bearer_address);
        view = findViewById(R.id.activity_addbearer_view);
        progress = findViewById(R.id.activity_addbearer_progress);
    }

    private void initEvent() {
        addBearer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strAddress = bearer.getText().toString();
                if (strAddress.equals("")) {
                    bearer.setError("Coupon Address is null");
                } else {
                    Intent intent = new Intent(AddBearerActivity.this, IssuerActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Please wait a few minutes", Toast.LENGTH_SHORT).show();
                    new AsyncTask() {

                        @Override
                        protected Object doInBackground(Object[] objects) {
                            Issuer iss = new Issuer("227955d7ff410da1c6f0e50c8f8280d7ee37ca75590302b69b7b66797d34f968");
                            try {
                                iss.giveFreeCouponTo(strcampaignAddress, strAddress);
                            } catch (Exception e) {
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            Toast.makeText(getApplicationContext(), "Add Bearer is Success!", Toast.LENGTH_SHORT).show();

                        }
                    }.execute();
                }
            }
        });
    }
}
