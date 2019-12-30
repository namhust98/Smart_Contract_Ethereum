package com.example.bearer.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bearer.Activity.BearerActivity;
import com.example.bearer.Activity.SplashActivity;
import com.example.bearer.Adapter.CouponAdapter;
import com.example.bearer.Adapter.CouponAdapter1;
import com.example.bearer.Model.Coupon;
import com.example.bearer.R;
import com.example.bearer.Web3jEthereum.Roles.Bearer;

import java.util.ArrayList;

public class AvailableCouponFragment extends Fragment {

    private ListView couponaddress;
    private ArrayList<Coupon> arrCouponAvailable;
    private String strcouponaddress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_availablecoupon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        couponaddress = view.findViewById(R.id.availableCoupon_couponAddress);
        arrCouponAvailable = SplashActivity.arrCouponAvailable;
        setAdapter();
    }

    private void setAdapter() {
        CouponAdapter1 adapter1 = new CouponAdapter1(getContext(), R.layout.coupon_item, arrCouponAvailable);
        couponaddress.setAdapter(adapter1);

        couponaddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Take this Coupon?");
                builder.setMessage("Are you sure you want to take this Coupon?");

                builder.setPositiveButton("BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setNeutralButton("TAKE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Please wait a few minutes", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), BearerActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                        new AsyncTask() {
                            @Override
                            protected Object doInBackground(Object[] objects) {
                                Bearer br = new Bearer("fdd865dc00505e217a4327c1f9a7ec94dc705a85da85a702ea4af93ac0acdcad");
                                try {
                                    br.getFreeCoupon(arrCouponAvailable.get(position).getAddress());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Object o) {
                                Toast.makeText(getContext(), "Take Coupon Success!", Toast.LENGTH_SHORT).show();
                            }
                        }.execute();
                    }
                });
                builder.create().show();
            }
        });
    }
}
