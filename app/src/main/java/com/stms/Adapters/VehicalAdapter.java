package com.stms.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.stms.Admin.VehicleDetails;
import com.stms.Admin.View_Vehicals;
import com.stms.R;
import com.stms.Responses.VehicalResponse;
import java.util.ArrayList;

public class VehicalAdapter extends RecyclerView.Adapter<VehicalAdapter.ViewHolder>implements Filterable {

    Context context;
    LayoutInflater layoutInflater;
    int j;

    ArrayList <VehicalResponse.data> data;
    ArrayList<VehicalResponse.data> dataFilter;
    View_Vehicals view_vehicals;



    public VehicalAdapter(Context context, ArrayList<VehicalResponse.data> data,View_Vehicals view_vehicals) {

        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        this.data=data;
        this.dataFilter=new ArrayList <VehicalResponse.data>();
        this.dataFilter.addAll(data);
        this.view_vehicals=view_vehicals;
    }

    @NonNull
    @Override
    public VehicalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View itemView=layoutInflater.inflate( R.layout.vehicle_card,parent,false);
        ViewHolder viewHolder=new ViewHolder(itemView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VehicalAdapter.ViewHolder holder, final int position) {

        holder.state.setVisibility(View.GONE);

        holder.customer.setText( data.get( position ).getCustomerName() );
        holder.mobile.setText( data.get( position ).getMobile() );
        holder.vId.setText( data.get( position ).getVehicleId() );
        holder.vType.setText( data.get( position ).getVehicleType() );
        holder.date.setText( data.get( position ).getPurchaseDate() );
        holder.service.setText( data.get( position ).getNoOfService() );
        holder.due.setText( data.get( position ).getNextServiceDue() );

        holder.modify.setId(position);
        holder.modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view_vehicals.modify(data.get(position).getVehicleId());
            }
        });
       holder.view.setId(position);
       holder.view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i=new Intent( context, VehicleDetails.class );
               i.putExtra( "action","view" );
               i.putExtra( "eId",data.get( position ).getVehicleId());
               i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity( i );
           }
       });
        holder.delete.setId(position);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_vehicals.VehicledeleteAlert(data.get(position).getVehicleId(),data.get(position).getVehicleType());
            }
        });
        holder.track.setId(position);
        holder.track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent i=context.getPackageManager().getLaunchIntentForPackage("com.jimi.tuqiang.tracksolid");
                context.startActivity(i);*/

                String apppackagename="com.jimi.tuqiang.tracksolid";
                PackageManager packageManager =context.getPackageManager() ;
                ApplicationInfo applicationInfo = null;
                try {
                    applicationInfo = packageManager.getApplicationInfo(apppackagename, 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if (applicationInfo == null) {
                    // not installed it will open your app directly on playstore
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" +apppackagename)));
                } else {
                    // Installed
                    Intent LaunchIntent = packageManager.getLaunchIntentForPackage(apppackagename);
                    context.startActivity( LaunchIntent );
                }

            }
        });




    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout state,row;
        TextView customer,mobile,vId,vType,date,service,due,track,modify,view,delete;

        public ViewHolder(View itemView) {
            super( itemView );

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(j==0)
                    {
                        state.setVisibility(View.VISIBLE);
                        j=1;
                    }

                    else
                    { state.setVisibility(View.GONE);
                        j=0;
                    }
                }
            });

            state = itemView.findViewById(R.id.state);
            row=itemView.findViewById(R.id.row);
            customer=itemView.findViewById( R.id.customer );
            mobile=itemView.findViewById( R.id.mobile );
            vId=itemView.findViewById( R.id.vId);
            vType=itemView.findViewById( R.id.vtype );
            date=itemView.findViewById( R.id.date );
            service=itemView.findViewById( R.id.services );
            due=itemView.findViewById( R.id.due );
            track=itemView.findViewById( R.id.track );
            modify=itemView.findViewById(R.id.modify);
            view=itemView.findViewById(R.id.view);
            delete=itemView.findViewById(R.id.delete);



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
                    ArrayList<VehicalResponse.data> filteredList = new ArrayList<>();
                    for (VehicalResponse.data row : dataFilter) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        try {
                            if (row.getMobile().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getCustomerName().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getPurchaseDate().toLowerCase().contains(charString.toLowerCase()) ||
                                /*row.getNoOfService().toLowerCase().contains(charString.toLowerCase())||
                                row.getNextServiceDue().toLowerCase().contains(charString.toLowerCase())||*/
                                    row.getVehicleId().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getVehicleType().toLowerCase().contains(charString.toLowerCase()))/*.contains(charSequence)*/ {
                                filteredList.add(row);
                            }
                        } catch (Exception e) {
                        }
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
                data = (ArrayList<VehicalResponse.data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
