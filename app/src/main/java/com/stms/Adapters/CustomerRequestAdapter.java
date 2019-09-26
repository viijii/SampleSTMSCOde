package com.stms.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.stms.Customers.CustomerRequestedIdeasFragment;
import com.stms.R;
import com.stms.Responses.AdminRequestedResponse;

import java.util.ArrayList;

public class CustomerRequestAdapter extends RecyclerView.Adapter<CustomerRequestAdapter.EMSHolder>{
    ArrayList<AdminRequestedResponse.data> data;
    Activity con;
    int selectedIndex = -1;
    int FADE_DURATION = 100;
    int i=0;
    CustomerRequestedIdeasFragment customerRequestedIdeasFragment;


    public CustomerRequestAdapter(Activity con, ArrayList<AdminRequestedResponse.data> data,
                                      CustomerRequestedIdeasFragment customerRequestedIdeasFragment)
    {
        this.con = con;
        this.data = data;
        this.customerRequestedIdeasFragment = customerRequestedIdeasFragment;
    }

    @Override
    public void onBindViewHolder(@NonNull EMSHolder holder, int position) {
        setScaleAnimation(holder.itemView);

        holder.complaint_no.setText( data.get( position ).getComplaintId());
        holder.title.setText( data.get( position ).getTitle());
        holder.vehicletype.setText( data.get( position ).getVehicleType());

    }
    public EMSHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_request_adapter,
                parent, false);

        return new EMSHolder(itemView);
    }

    private void setScaleAnimation(View view)
    {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    public static class EMSHolder extends RecyclerView.ViewHolder
    {
        TextView complaint_no,title,vehicletype;

        public EMSHolder(View v)
        {
            super(v);

            complaint_no = v.findViewById(R.id.complaint_no);
            title = v.findViewById(R.id.title);
            vehicletype = v.findViewById(R.id.vehicletype);


        }
    }

    public int getItemCount() {
        return data.size();
    }

    public Object getItem(int i) {
        return i;
    }

    @NonNull


    public long getItemId(int i) {
        return i;
    }

    public void setSelectedIndex(int index) {
        selectedIndex = index;
    }


}

