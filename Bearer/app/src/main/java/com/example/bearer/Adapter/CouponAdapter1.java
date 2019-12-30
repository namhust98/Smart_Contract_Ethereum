package com.example.bearer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bearer.Model.Coupon;
import com.example.bearer.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

public class CouponAdapter1 extends ArrayAdapter<Coupon> {

    private Context context;
    private List<Coupon> arrCoupon;

    public CouponAdapter1(@NonNull Context context, int resource, List<Coupon> arrCoupon) {
        super(context, resource, arrCoupon);
        this.context = context;
        this.arrCoupon = arrCoupon;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.coupon_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.coupon_item_name);
            viewHolder.categoryAndremain = convertView.findViewById(R.id.coupon_item_category_and_remain);
            viewHolder.description = convertView.findViewById(R.id.coupon_item_description);
            viewHolder.endtime = convertView.findViewById(R.id.coupon_item_endtime);

            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder)convertView.getTag();

        // Lay ra mot Transaction va dat vao ListView
        Coupon coupon = arrCoupon.get(position);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy ");

        long time = Long.parseLong(coupon.getEndTime());
        time = time * 1000;

        viewHolder.name.setText(coupon.getName());
        viewHolder.categoryAndremain.setText(coupon.getCategory() + " (Total: " + coupon.getRemain() + ")");
        viewHolder.description.setText(coupon.getDescription());
        viewHolder.endtime.setText("Exp: " + sdf.format(time));

        //do something
        return convertView;
    }

    public class ViewHolder
    {
        TextView name, categoryAndremain, description, endtime;
    }
}
