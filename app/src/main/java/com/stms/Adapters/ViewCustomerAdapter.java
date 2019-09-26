package com.stms.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stms.Admin.CustomerView;
import com.stms.R;
import com.stms.Responses.ViewCustomerResponse;

import java.util.ArrayList;

public class ViewCustomerAdapter extends RecyclerView.Adapter<ViewCustomerAdapter.ViewHolder> {

    CustomerView customerView;
    LayoutInflater layoutInflater;
    int j;
    Context context;

    ArrayList <ViewCustomerResponse.data> data;
    ArrayList<ViewCustomerResponse.data> dataFilter;



    public ViewCustomerAdapter( ArrayList<ViewCustomerResponse.data> data,CustomerView customerView,Context context) {

        this.customerView=customerView;
        this.data=data;
        this.context=context;
        this.dataFilter=new ArrayList <ViewCustomerResponse.data>();
        this.dataFilter.addAll(data);
    }

    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.customeradapterlayout,
                parent,false);
        return new ViewCustomerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
      holder.vehicletype1.setText(data.get(position).getVehicleType());
      holder.vehicleid1.setText(data.get(position).getVehicleId());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView vehicleid1,vehicletype1;

        public ViewHolder(View itemView) {
            super( itemView );

           vehicleid1=itemView.findViewById(R.id.vehicleid1);
           vehicletype1=itemView.findViewById(R.id.vehicletye1);
        }
    }
}