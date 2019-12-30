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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.issuer.R;
import com.example.issuer.Web3jEthereum.Roles.Issuer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CampaignManager extends AppCompatActivity {

    private EditText campaignname, number, categories, description, weiPerRedemption;
    private TextView timeLimit;
    private Button createCampaign;
    private Spinner isfree;
    private String strcampaignname, strcategories, strdescription;
    private long longnumber, longWeiPerRedeemtion, longTimeLimit;
    private boolean free;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaignmanager);

        this.context = getApplicationContext();

        findView();
        setEvent();
    }

    private void setEvent() {
        final String[] kind = {"false", "true"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(CampaignManager.this, android.R.layout.simple_spinner_item, kind);
        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        isfree.setAdapter(adapter1);

        isfree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isfree.getSelectedItem().toString().equals("false")) {
                    free = false;
                } else {
                    free = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                free = false;
            }
        });

        timeLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.dialog_datepicker, null);
                final DatePicker datePicker = alertLayout.findViewById(R.id.founding_picker);

                AlertDialog.Builder alert = new AlertDialog.Builder(CampaignManager.this);
                alert.setView(alertLayout);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            String dt = datePicker.getDayOfMonth()
                                    + "/" + (datePicker.getMonth() + 1)
                                    + "/" + datePicker.getYear();
                            Date date = dateFormat.parse(dt);
                            longTimeLimit = date.getTime()/1000;
                            timeLimit.setText(dt);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });

        createCampaign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strcampaignname = campaignname.getText().toString();
                strcategories = categories.getText().toString();
                strdescription = description.getText().toString();
                if (!number.getText().toString().equals("")) {
                    longnumber = Long.parseLong(number.getText().toString());
                }
                if (!weiPerRedemption.getText().toString().equals("")) {
                    longWeiPerRedeemtion = Long.parseLong(weiPerRedemption.getText().toString());
                }

                if (strcampaignname.equals("")) {
                    campaignname.setError("Campaign is null");
                } else if (number.getText().toString().equals("")) {
                    number.setError("Number is null");
                } else if (strcategories.equals("")) {
                    categories.setError("Tokens Deposit is null");
                } else if (strdescription.equals("")) {
                    description.setError("Description is null");
                } else if (weiPerRedemption.getText().toString().equals("")) {
                    weiPerRedemption.setError("Wei per Redemption is null");
                } else if (timeLimit.getText().toString().equals("")) {
                    timeLimit.setError("Time limit is null");
                } else {
                    Toast.makeText(CampaignManager.this, "Wait a few minutes, Ethereum's Server need time to confirm your campaign ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CampaignManager.this, IssuerActivity.class);
                    startActivity(intent);
                    new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            try {
                                Issuer iss = new Issuer("227955d7ff410da1c6f0e50c8f8280d7ee37ca75590302b69b7b66797d34f968");
                                iss.createCampain(longnumber, free, longWeiPerRedeemtion, strcampaignname, strcategories, strdescription, longTimeLimit);
                            } catch (Exception e) {
                            }
                            return null;
                        }

                        protected void onPostExecute(Object o) {
                            Toast.makeText(context, "Your Campaign has been created! You can check it now on Ethereum's Server", Toast.LENGTH_LONG).show();
                        }
                    }.execute();
                }
            }
        });
    }

    private void findView() {
        campaignname = findViewById(R.id.createcampaign_campaign_name);
        number = findViewById(R.id.createcampaign_number_of_coupon);
        categories = findViewById(R.id.createcampaign_categories);
        description = findViewById(R.id.createcampaign_description);
        createCampaign = findViewById(R.id.createcampaign_create_campaign);
        weiPerRedemption = findViewById(R.id.createcampaign_weiPerRedemption);
        timeLimit = findViewById(R.id.createcampaign_timelimit);
        isfree = findViewById(R.id.createcampaign_isfree);
    }
}
