package com.example.bearer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bearer.Fragment.AvailableCouponFragment;
import com.example.bearer.Fragment.UseCouponFragment;
import com.example.bearer.R;
import com.google.android.material.tabs.TabLayout;

public class BearerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bearer);

        viewPager = findViewById(R.id.bearer_ViewPager);
        tabLayout = findViewById(R.id.bearer_TabLayout);
        initViewPagerandTabLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bearer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.bearer_information) {
            Intent intent = new Intent(BearerActivity.this, InformationBearer.class);
            startActivity(intent);
        } else if (id == R.id.bearer_transaction) {
            Toast.makeText(BearerActivity.this, "Chức năng này sắp có", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.bearer_get_coupon) {
            Intent intent = new Intent(BearerActivity.this, GetCouponActivity.class);
            startActivity(intent);
        } else if (id == R.id.bearer_logout) {
            Intent intent = new Intent(BearerActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViewPagerandTabLayout() {
        UseCouponFragment useCouponFragment = new UseCouponFragment();
        AvailableCouponFragment availableCouponFragment = new AvailableCouponFragment();

        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(availableCouponFragment, "Available Coupon");
        mainViewPagerAdapter.addFragment(useCouponFragment, "Bearer's Coupon");

        viewPager.setAdapter(mainViewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }
}