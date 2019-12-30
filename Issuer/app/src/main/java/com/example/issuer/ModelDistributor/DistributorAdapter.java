package com.example.issuer.ModelDistributor;

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

public class DistributorAdapter extends ArrayAdapter<DistributorModel> {

    private Context context;
    private List<DistributorModel> arrDistributor;

    public DistributorAdapter(@NonNull Context context, int resource, List<DistributorModel> arrDistributor) {
        super(context, resource, arrDistributor);
        this.context = context;
        this.arrDistributor = arrDistributor;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.distributor_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.address = convertView.findViewById(R.id.distributor_item_address);
            viewHolder.redeemed = convertView.findViewById(R.id.distributor_item_redeemed);
            viewHolder.acquired = convertView.findViewById(R.id.distributor_item_acquired);

            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder)convertView.getTag();

        // Lay ra mot Transaction va dat vao ListView
        DistributorModel distributorModel = arrDistributor.get(position);

        viewHolder.address.setText(distributorModel.getAddress());
        viewHolder.redeemed.setText("Redeemed: " + distributorModel.getRedeemed());
        viewHolder.acquired.setText("Acquired: " + distributorModel.getAcquired());

        //do something
        return convertView;
    }

    public class ViewHolder
    {
        TextView address, redeemed, acquired;
    }
}
