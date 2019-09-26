package com.stms.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.stms.CommonScreens.FeedBackActivity;
import com.stms.Customers.CustomerCompletedIdeasFragment;
import com.stms.Customers.CustomerRequestedIdeasFragment;
import com.stms.R;
import com.stms.Responses.AdminRequestedResponse;

import java.util.ArrayList;

public class CustomerCompletedAdapter extends RecyclerView.Adapter<CustomerCompletedAdapter.EMSHolder> implements Filterable {

    ArrayList<AdminRequestedResponse.data> data,dataFilter;
    Activity con;
    int selectedIndex = -1;
    int FADE_DURATION = 100;
    int i=0;
    CustomerCompletedIdeasFragment customerCompletedIdeasFragment;


    public CustomerCompletedAdapter(Activity con, ArrayList<AdminRequestedResponse.data> data,
                                  CustomerCompletedIdeasFragment customerCompletedIdeasFragment)
    {
        this.con = con;
        this.data = data;
        this.customerCompletedIdeasFragment = customerCompletedIdeasFragment;
        this.dataFilter = new ArrayList<AdminRequestedResponse.data>();
        this.dataFilter.addAll(data);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerCompletedAdapter.EMSHolder holder, final int position) {
        setScaleAnimation(holder.itemView);

        holder.complaint_no.setText( data.get( position ).getComplaintId());
        holder.title.setText( data.get( position ).getTitle());
        holder.vehicletype.setText( data.get( position ).getVehicleType());

        holder.feedback.setId(position);
        holder.feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(con, FeedBackActivity.class);
                i.putExtra("action","completed");
                i.putExtra("taskId",data.get(position).getTaskId());
                con.startActivity(i);
            }
        });

    }
    public CustomerCompletedAdapter.EMSHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_completed_adapter,
                parent, false);

        return new CustomerCompletedAdapter.EMSHolder(itemView);
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
        TextView complaint_no,title,vehicletype,feedback;

        public EMSHolder(View v)
        {
            super(v);

            complaint_no = v.findViewById(R.id.complaint_no);
            title = v.findViewById(R.id.title);
            vehicletype = v.findViewById(R.id.vehicletype);
            feedback= v.findViewById(R.id.feedback);


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
