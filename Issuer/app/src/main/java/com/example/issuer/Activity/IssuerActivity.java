package com.example.issuer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.issuer.R;

public class IssuerActivity extends AppCompatActivity {

    private RelativeLayout campaign, distributor, bearer, releasedCoupon, activeCoupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issuer);

        campaign = findViewById(R.id.campaign_manager);
        distributor = findViewById(R.id.distributor_manager);
        bearer = findViewById(R.id.bearer_manager);
        releasedCoupon = findViewById(R.id.released_coupon);
        activeCoupon = findViewById(R.id.active_coupon);

        campaign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IssuerActivity.this, CampaignManager.class);
                startActivity(intent);
            }
        });

        distributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IssuerActivity.this, DistributorManager.class);
                startActivity(intent);
            }
        });

        bearer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IssuerActivity.this, AddBearerActivity.class);
                startActivity(intent);
            }
        });

        releasedCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IssuerActivity.this, ReleaseCouponActivity.class);
                startActivity(intent);
            }
        });

        activeCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IssuerActivity.this, ActiveCouponActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(IssuerActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.issuer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.issuer_information) {
            Intent intent = new Intent(IssuerActivity.this, InformationIssuer.class);
            startActivity(intent);
        } else if (id == R.id.transaction) {
            Toast.makeText(IssuerActivity.this, "Chức năng này sắp có", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.log_out) {
            Intent intent = new Intent(IssuerActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
