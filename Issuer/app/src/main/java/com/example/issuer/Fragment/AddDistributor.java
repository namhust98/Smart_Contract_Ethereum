package com.example.issuer.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.issuer.Activity.ConfirmActivity;
import com.example.issuer.R;
import com.google.android.material.textfield.TextInputLayout;

public class AddDistributor extends Fragment {

    private TextInputLayout[] dstrAdr = new TextInputLayout[5];
    private EditText campaignAddress;
    private EditText[] distributor = new EditText[5];
    private String strcampaignAddress;
    private LinearLayout addnewdistributor;
    private Button addDistributor;
    private int i = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adddistributor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findView(view);
        initEvent();
    }

    private void initEvent() {
        addnewdistributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (i) {
                    case 1: {
                        dstrAdr[1].setVisibility(View.VISIBLE);
                        i = 2;
                        break;
                    }
                    case 2: {
                        dstrAdr[2].setVisibility(View.VISIBLE);
                        i = 3;
                        break;
                    }
                    case 3: {
                        dstrAdr[3].setVisibility(View.VISIBLE);
                        i = 4;
                        break;
                    }
                    case 4: {
                        dstrAdr[4].setVisibility(View.VISIBLE);
                        i = 5;
                        addnewdistributor.setVisibility(View.GONE);
                        break;
                    }

                }
            }
        });

        addDistributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strcampaignAddress = campaignAddress.getText().toString();
                String[] strAddress = new String[i];
                for (int j = 0; j < i; j++) {
                    strAddress[j] = distributor[j].getText().toString();
                }

                if (strcampaignAddress.equals("")) {
                    campaignAddress.setError("Campaign Address is null");
                } else if (strAddress[0].equals("")) {
                    distributor[0].setError("Distributor Address is null");
                } else {
                    Intent intent = new Intent(getContext(), ConfirmActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void findView(View view) {
        campaignAddress = view.findViewById(R.id.addDistributor_campaign_address);
        addnewdistributor = view.findViewById(R.id.addDistributor_addMore);
        addDistributor = view.findViewById(R.id.addDistributor_add_button);

        dstrAdr[0] = view.findViewById(R.id.addDistributor_1);
        dstrAdr[1] = view.findViewById(R.id.addDistributor_2);
        dstrAdr[2] = view.findViewById(R.id.addDistributor_3);
        dstrAdr[3] = view.findViewById(R.id.addDistributor_4);
        dstrAdr[4] = view.findViewById(R.id.addDistributor_5);

        distributor[0] = view.findViewById(R.id.addDistributor_distributor_address1);
        distributor[1] = view.findViewById(R.id.addDistributor_distributor_address2);
        distributor[2] = view.findViewById(R.id.addDistributor_distributor_address3);
        distributor[3] = view.findViewById(R.id.addDistributor_distributor_address4);
        distributor[4] = view.findViewById(R.id.addDistributor_distributor_address5);
    }
}
