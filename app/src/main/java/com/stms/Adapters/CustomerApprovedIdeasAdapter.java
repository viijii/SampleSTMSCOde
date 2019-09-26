package com.stms.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stms.Admin.AdminApprovedIdeasFragment;
import com.stms.Customers.CustomerApprovedIdeasFragment;
import com.stms.R;
import com.stms.Responses.AdminRequestedResponse;

import java.util.ArrayList;
import java.util.TreeSet;

public class CustomerApprovedIdeasAdapter extends RecyclerView.Adapter<CustomerApprovedIdeasAdapter.EMSHolder> implements Filterable {
    ArrayList<AdminRequestedResponse.data> data,dataFilter;
    Activity con;
    int selectedIndex = -1;
    int FADE_DURATION = 100;
    int i = 0;
    CustomerApprovedIdeasFragment customerApprovedIdeasFragment;

    public CustomerApprovedIdeasAdapter(FragmentActivity con, ArrayList<AdminRequestedResponse.data> data, CustomerApprovedIdeasFragment customerApprovedIdeasFragment) {

        this.con = con;
        this.data = data;
        this.customerApprovedIdeasFragment = customerApprovedIdeasFragment;
        this.dataFilter = new ArrayList<AdminRequestedResponse.data>();
        this.dataFilter.addAll(data);
    }

    public EMSHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_approved_cardview,
                parent, false);

        return new EMSHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final EMSHolder holder, final int position) {

        setScaleAnimation(holder.itemView);

        holder.complaint_no.setText( data.get( position ).getComplaintId());
        holder.title.setText( data.get( position ).getTitle());
        holder.name.setText( data.get( position ).getName());
        holder.vehicleId.setText( data.get( position ).getVehicleId());
        holder.vehicletype.setText( data.get( position ).getComplaintId());
        holder.description.setText( data.get( position ).getDescription());
        holder.phonenumber.setText(data.get(position).getMobileNumber());
        String imageview=data.get(position).getImagePath();
        //  Picasso.with(con).load(imageview).into(holder.image);


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.comll.setVisibility(View.VISIBLE);
                holder.imagell.setVisibility(View.VISIBLE);
                holder.view.setVisibility(View.GONE);

            }
        });
    }
    private void setScaleAnimation(View view) {

        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class EMSHolder extends RecyclerView.ViewHolder {
        TextView complaint_no, title, name, vehicleId, vehicletype, phonenumber, description, view, assign;
        ImageView image;
        LinearLayout comll, imagell;
        private TreeSet data;


        public EMSHolder(View v) {
            super(v);

            complaint_no = v.findViewById(R.id.complaint_no);
            title = v.findViewById(R.id.title);
            name = v.findViewById(R.id.name);
            vehicleId = v.findViewById(R.id.vehicleId);
            description = v.findViewById(R.id.description);
            vehicletype = v.findViewById(R.id.vehicletype);
            phonenumber = v.findViewById(R.id.phonenumber);

            view = v.findViewById(R.id.view);

            assign = v.findViewById(R.id.assign);

            image = v.findViewById(R.id.image);
            comll = v.findViewById(R.id.comll);
            imagell = v.findViewById(R.id.imagell);


        }

        public int getItemCount() {
            return data.size();
        }

    }

    @Override
    public Filter getFilter() {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {

                data.clear();

                String charString = charSequence.toString();

                if (charString.isEmpty())
                {
                    data.addAll(dataFilter);
                }
                else
                {
                    //dataFilter.clear();
                    ArrayList<AdminRequestedResponse.data> filteredList = new ArrayList<>();
                    for (AdminRequestedResponse.data row : dataFilter) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        try {
                            if ("#Complaint ".concat(row.getComplaintId()).toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getTitle().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getName().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getVehicleId().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getDescription().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getMobileNumber().toLowerCase().contains(charString.toLowerCase()))/*.contains(charSequence)*/ {
                                filteredList.add(row);
                            }
                        }catch(Exception e){}
                    }

                    data = filteredList;
                }

                //System.out.println("Data Filter Size--"+data.size());
                FilterResults filterResults = new FilterResults();
                filterResults.values = data;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {

                data = (ArrayList<AdminRequestedResponse.data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}





