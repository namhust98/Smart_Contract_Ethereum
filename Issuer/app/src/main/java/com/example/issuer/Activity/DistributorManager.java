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
import android.widget.Toast;

import com.example.issuer.ModelCampaign.CampaignAdapter;
import com.example.issuer.ModelCampaign.CampaignModel;
import com.example.issuer.R;
import com.example.issuer.Web3jEthereum.ContractWrapper.CampainFactory;
import com.example.issuer.Web3jEthereum.Roles.Issuer;

import java.util.ArrayList;

import jnr.ffi.annotations.In;

public class DistributorManager extends AppCompatActivity {

    private ListView campaignAddress;
    private ArrayList<CampaignModel> arrCampaign = new ArrayList<>();
    private LinearLayout view, progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributormanager);

        findView();

//        if (SplashActivity.arrCampaign != null) {
//            arrCampaign = SplashActivity.arrCampaign;
//            view.setVisibility(View.VISIBLE);
//            progress.setVisibility(View.GONE);
//        } else {
            getData();
//        }
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
        CampaignAdapter adapter1 = new CampaignAdapter(DistributorManager.this, R.layout.campaign_item, arrCampaign);
        campaignAddress.setAdapter(adapter1);

        campaignAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CampaignModel campaignModel = arrCampaign.get(position);
                String campaignAddress = campaignModel.getAddress();

                Intent intent = new Intent(DistributorManager.this, ListDistributor.class);
                intent.putExtra("campaignAddress", campaignAddress);
                startActivity(intent);
            }
        });
    }

    private void findView() {
        campaignAddress = findViewById(R.id.distributorManager_campaignAddress);
        view = findViewById(R.id.activity_distributormanager_view);
        progress = findViewById(R.id.activity_distributormanager_progress);
    }
}
