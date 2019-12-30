package com.example.issuer.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.issuer.ModelCampaign.CampaignModel;
import com.example.issuer.R;
import com.example.issuer.Web3jEthereum.ContractWrapper.CampainFactory;
import com.example.issuer.Web3jEthereum.Roles.Issuer;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    public static ArrayList<CampaignModel> arrCampaign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        arrCampaign = new ArrayList<>();

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
                Intent intent = new Intent(SplashActivity.this, IssuerActivity.class);
                startActivity(intent);
            }
        }.execute();
    }
}
