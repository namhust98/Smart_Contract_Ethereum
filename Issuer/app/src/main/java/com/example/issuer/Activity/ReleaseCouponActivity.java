package com.example.issuer.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.issuer.ModelCampaign.CampaignAdapter;
import com.example.issuer.ModelCampaign.CampaignModel;
import com.example.issuer.R;
import com.example.issuer.Web3jEthereum.ContractWrapper.CampainFactory;
import com.example.issuer.Web3jEthereum.Roles.Distributor;
import com.example.issuer.Web3jEthereum.Roles.Issuer;

import java.util.ArrayList;

public class ReleaseCouponActivity extends AppCompatActivity {

    private ListView campaignAddress;
    private ArrayList<CampaignModel> arrCampaign = new ArrayList<>();
    private LinearLayout view, progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_releasecoupon);

        findView();

        if (SplashActivity.arrCampaign != null) {
            arrCampaign = SplashActivity.arrCampaign;
            view.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
        } else {
            getData();
        }
        initListView();
    }

    private void getData() {
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {
                Issuer iss = new Issuer("227955d7ff410da1c6f0e50c8f8280d7ee37ca75590302b69b7b66797d34f968");
                try {
                    for (CampainFactory.NewCampainEventResponse e : iss.getOwnedCampains()) {
                        CampaignModel campaignModel = new CampaignModel(e._address, e._name, e._category,
                                e._description, e._num_coupon + "", e._end_time + "");
                        arrCampaign.add(campaignModel);
                    }
                } catch (Exception e) {
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                view.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
            }
        }.execute();
    }

    private void initListView() {
        CampaignAdapter adapter1 = new CampaignAdapter(ReleaseCouponActivity.this, R.layout.campaign_item, arrCampaign);
        campaignAddress.setAdapter(adapter1);

        campaignAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CampaignModel campaignModel = arrCampaign.get(position);
                String strcampaignAddress = campaignModel.getAddress();

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.dialog_addnew, null);
                final EditText edt = alertLayout.findViewById(R.id.addnew_address);

                AlertDialog.Builder alert = new AlertDialog.Builder(ReleaseCouponActivity.this);
                alert.setView(alertLayout);
                alert.setTitle("Bearer's Address");
                alert.setMessage("Write bearer's address you want to released this coupon");
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String beareraddress = edt.getText().toString();
                        Distributor dis = new Distributor("227955d7ff410da1c6f0e50c8f8280d7ee37ca75590302b69b7b66797d34f968");
                        String qr_text = dis.generateQRForBearer(strcampaignAddress, beareraddress);
                        Intent intent = new Intent(ReleaseCouponActivity.this, GenerateQR.class);
                        intent.putExtra("qr", qr_text);
                        startActivity(intent);
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
    }

    private void findView() {
        campaignAddress = findViewById(R.id.releaseCoupon_campaignAddress);
        view = findViewById(R.id.activity_released_view);
        progress = findViewById(R.id.activity_released_progress);
    }
}
