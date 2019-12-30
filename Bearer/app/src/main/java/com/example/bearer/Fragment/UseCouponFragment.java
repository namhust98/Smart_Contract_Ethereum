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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bearer.Activity.BearerActivity;
import com.example.bearer.Activity.SplashActivity;
import com.example.bearer.Adapter.CouponAdapter;
import com.example.bearer.Activity.ConfirmActivity;
import com.example.bearer.Activity.GenerateQR;
import com.example.bearer.Model.Coupon;
import com.example.bearer.R;
import com.example.bearer.Web3jEthereum.Roles.Bearer;

import java.util.ArrayList;

public class UseCouponFragment extends Fragment {

    private ListView couponaddress;
    private ArrayList<Coupon> arrCouponBearer;
    private String strcouponaddress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_usecoupon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        couponaddress = view.findViewById(R.id.couponAddress);

        arrCouponBearer = SplashActivity.arrCouponBearer;

        setAdapter();
    }

    private void setAdapter() {
        CouponAdapter adapter1 = new CouponAdapter(getContext(), R.layout.coupon_item, arrCouponBearer);
        couponaddress.setAdapter(adapter1);

        couponaddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Transfer or Use?");
                builder.setMessage("You want to use this Coupon or transfer to your friend?");

                builder.setPositiveButton("BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setNeutralButton("USE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), GenerateQR.class);
                        intent.putExtra("address", arrCouponBearer.get(position).getAddress());
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("TRANSFER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LayoutInflater inflater = getLayoutInflater();
                        View alertLayout = inflater.inflate(R.layout.address_dialog, null);
                        final EditText addr = (EditText) alertLayout.findViewById(R.id.information_address);

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                        builder1.setTitle("Input your friend's address");
                        builder1.setView(alertLayout);

                        builder1.setPositiveButton("BACK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder1.setNeutralButton("TRANSFER", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String straddr = addr.getText().toString();
                                Intent intent = new Intent(getContext(), BearerActivity.class);
                                startActivity(intent);
                                dialog.dismiss();
                                new AsyncTask() {
                                    @Override
                                    protected Object doInBackground(Object[] objects) {
                                        Bearer br = new Bearer("fdd865dc00505e217a4327c1f9a7ec94dc705a85da85a702ea4af93ac0acdcad");
                                        try {
                                            br.transferCoupon(arrCouponBearer.get(position).getAddress(), straddr);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Object o) {
                                        Toast.makeText(getContext(), "Transfer Coupon Success!", Toast.LENGTH_SHORT).show();
                                    }
                                }.execute();
                            }
                        });

                        builder1.create().show();
                    }
                });
                builder.create().show();
            }
        });
    }
}
