package com.example.issuer.ModelCampaign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.issuer.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class CampaignAdapter extends ArrayAdapter<CampaignModel> {

    private Context context;
    private List<CampaignModel> arrCampaign;

    public CampaignAdapter(@NonNull Context context, int resource, List<CampaignModel> arrCampaign) {
        super(context, resource, arrCampaign);
        this.context = context;
        this.arrCampaign = arrCampaign;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.campaign_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.campaign_item_name);
            viewHolder.categoryAndnum = convertView.findViewById(R.id.campaign_item_category_and_numCoupon);
            viewHolder.description = convertView.findViewById(R.id.campaign_item_description);
            viewHolder.endtime = convertView.findViewById(R.id.campaign_item_endtime);
            viewHolder.address = convertView.findViewById(R.id.campaign_item_address);

            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        // Lay ra mot Transaction va dat vao ListView
        CampaignModel campaignModel = arrCampaign.get(position);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy ");

        long time = Long.parseLong(campaignModel.getEndTime());
        time = time * 1000;

        viewHolder.name.setText(campaignModel.getName());
        viewHolder.categoryAndnum.setText(campaignModel.getCategory() + " (" + campaignModel.getNumCoupon() + " Coupons)");
        viewHolder.description.setText(campaignModel.getDescription());
        viewHolder.endtime.setText("Exp: " + sdf.format(time));
        viewHolder.address.setText(campaignModel.getAddress());

        //do something
        return convertView;
    }

    public class ViewHolder {
        TextView name, categoryAndnum, description, endtime, address;
    }
}
