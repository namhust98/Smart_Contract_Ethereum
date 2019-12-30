package com.example.bearer.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bearer.Model.Coupon;
import com.example.bearer.R;
import com.example.bearer.Web3jEthereum.ContractWrapper.CampainFactory;
import com.example.bearer.Web3jEthereum.Roles.Bearer;
import com.example.bearer.Web3jEthereum.Roles.General;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    public static ArrayList<Coupon> arrCouponBearer;
    public static ArrayList<Coupon> arrCouponAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        arrCouponBearer = new ArrayList<>();
        arrCouponAvailable = new ArrayList<>();

        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {
                Bearer br = new Bearer("fdd865dc00505e217a4327c1f9a7ec94dc705a85da85a702ea4af93ac0acdcad");
                try {
                    for(General.CampainInfo e: br.getAllAcquiredCampains()){
                        arrCouponBearer.add(new Coupon(e.name, e.category, e.description, e.endtime + "", e.num_remain + "", e.address));
                    }
                    for(CampainFactory.NewCampainEventResponse e: br.getAllFreeCoupons()){
                        arrCouponAvailable.add(new Coupon(e._name, e._category, e._description, e._end_time + "", e._num_coupon + "", e._address));
                    }
                } catch (Exception e) {
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                Intent intent = new Intent(SplashActivity.this, BearerActivity.class);
                startActivity(intent);
            }
        }.execute();
    }
}
