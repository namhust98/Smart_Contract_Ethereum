package com.example.issuer.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.issuer.ModelDistributor.DistributorAdapter;
import com.example.issuer.ModelDistributor.DistributorModel;
import com.example.issuer.R;
import com.example.issuer.Web3jEthereum.Roles.Issuer;

import org.web3j.utils.Async;

import java.util.ArrayList;

public class ListDistributor extends AppCompatActivity {

    private ListView listDistributor;
    private ImageView addnew;
    private String campaignAddress;
    private ArrayList<DistributorModel> arrDistributor = new ArrayList<>();
    private LinearLayout view, progress;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdistributor);

        listDistributor = findViewById(R.id.distributorManager_listDistributor);
        addnew = findViewById(R.id.distributorManager_addnew);
        view = findViewById(R.id.activity_lisdistributor_view);
        progress = findViewById(R.id.activity_lisdistributor_progress);

        campaignAddress = getIntent().getStringExtra("campaignAddress");
        context = getApplicationContext();

        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {
                getData();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                view.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
            }
        }.execute();
        setEvent();
    }

    private void getData() {
        Issuer iss = new Issuer("227955d7ff410da1c6f0e50c8f8280d7ee37ca75590302b69b7b66797d34f968");
        try {
            for (Issuer.Distributor dis : iss.getOwnedDistributors(campaignAddress)) {
                arrDistributor.add(new DistributorModel(dis.address, dis.num_redeemed + "", dis.num_acquired + ""));
            }
        } catch (Exception e) {
        }
    }

    private void setEvent() {
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.dialog_addnew, null);
                final EditText addr = (EditText) alertLayout.findViewById(R.id.addnew_address);

                AlertDialog.Builder alert = new AlertDialog.Builder(ListDistributor.this);
                alert.setTitle("Distributor Address");
                alert.setView(alertLayout);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // code for matching password
                        String straddr = addr.getText().toString();
                        if (straddr.equals("")){
                            addr.setError("Address is null!");
                        } else {
                            Toast.makeText(context, "Please wait a few minute", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ListDistributor.this, IssuerActivity.class);
                            startActivity(intent);
                            new AsyncTask() {
                                @Override
                                protected Object doInBackground(Object[] objects) {
                                    try {
                                        Issuer iss = new Issuer("227955d7ff410da1c6f0e50c8f8280d7ee37ca75590302b69b7b66797d34f968");
                                        iss.addDistributor(campaignAddress,straddr);
                                    } catch (Exception e) {
                                    }
                                    return null;
                                }
                                protected void onPostExecute(Object o) {
                                    Toast.makeText(context, "Add Distributor is Success!", Toast.LENGTH_LONG).show();
                                }
                            }.execute();
                            dialog.dismiss();
                        }
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });

        DistributorAdapter distributorAdapter = new DistributorAdapter(ListDistributor.this, R.layout.distributor_item, arrDistributor);
        listDistributor.setAdapter(distributorAdapter);

        listDistributor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DistributorModel distributorModel = arrDistributor.get(position);
                String distributorAddress = distributorModel.getAddress();
                AlertDialog.Builder builder = new AlertDialog.Builder(ListDistributor.this);
                builder.setTitle("Remove this Distributor");
                builder.setMessage("Are you sure you want to remove this distributor from your campaign?");

                builder.setPositiveButton("BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setNeutralButton("REMOVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Please wait a few minute", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ListDistributor.this, IssuerActivity.class);
                        startActivity(intent);
                        new AsyncTask() {
                            @Override
                            protected Object doInBackground(Object[] objects) {
                                try {
                                    Issuer iss = new Issuer("227955d7ff410da1c6f0e50c8f8280d7ee37ca75590302b69b7b66797d34f968");
                                    iss.removeDistributor(campaignAddress,distributorAddress);
                                } catch (Exception e) {
                                }
                                return null;
                            }
                            protected void onPostExecute(Object o) {
                                Toast.makeText(context, "Remove Distributor is Success!", Toast.LENGTH_LONG).show();
                            }
                        }.execute();
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }
}
