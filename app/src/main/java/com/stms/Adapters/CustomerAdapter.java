package com.stms.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stms.Admin.AddCustomers;
import com.stms.Admin.CustomerView;
import com.stms.Admin.View_customers;
import com.stms.R;
import com.stms.Responses.customerResponse;

import java.util.ArrayList;

/**
 * Created by mugdha on 8/19/18.
 */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.EMSHolder> implements Filterable
{
    ArrayList<customerResponse.data> data;
    ArrayList<customerResponse.data> dataFilter;
    Activity con;
    int selectedIndex = -1;
    int FADE_DURATION = 100;
   // ViewEmployees viewEmployees;
    View_customers view_customers;
    Context context;
    boolean code=false;
    String loginid;



    public CustomerAdapter(Activity con, ArrayList<customerResponse.data> data, View_customers view_customers, Context context)
    {
        this.context = context;
        this.con = con;
        this.data = data;
        //this.dataFilter = data;
        this.dataFilter = new ArrayList<customerResponse.data>();
        this.dataFilter.addAll(data);
        this.view_customers = view_customers;
    }

    public EMSHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customerlist,
                parent, false);

        return new EMSHolder(itemView);
    }

    public void onBindViewHolder(final EMSHolder holder, final int position) {
        setScaleAnimation( holder.itemView );




        holder.mobile.setText( data.get( position ).getMobile() );

        holder.customerName.setText( data.get( position ).getCustomerName() );
        holder.noOfveh.setText( data.get( position ).getNoOfVeh() );
        holder.address.setText( data.get( position ).getAddress() );

        holder.state1.setVisibility(View.GONE);
        holder.state2.setVisibility(View.GONE);

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(code==false)
                {
                   holder.state1.setVisibility(View.VISIBLE);
                    holder.state2.setVisibility(View.VISIBLE);
                    code=true;
                }

                else
                {
                    holder.state1.setVisibility(View.GONE);
                    holder.state2.setVisibility(View.GONE);
                    code=false;
                }
            }
        });


//holder.empstatus.setText( data.get( position ).getestatus() );


        holder.call.setId( position );


        holder.call.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String call = data.get( position ).getMobile();
                Intent callIntent = new Intent( Intent.ACTION_DIAL );
                //  callIntent.setData(Uri.parse("tel:"+number));
                callIntent.setData( Uri.parse( "tel:" + call ) );

                // startActivity(callIntent);


                if (ActivityCompat.checkSelfPermission( context,
                        Manifest.permission.CALL_PHONE ) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                context.startActivity( callIntent );

                // viewEmployees.callUser(v.getId());

            }
        } );
        holder.view.setId(position);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, CustomerView.class);
                i.putExtra("cId",data.get(position).getCustomerId());
                context.startActivity(i);
            }
        });

        holder.update.setId(position);
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, AddCustomers.class);
                i.putExtra("cId",data.get(position).getCustomerId());
                i.putExtra("action","edit");
                context.startActivity(i);
            }
        });
        holder.delete.setId(position);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("TAG", "onClick:deletename "+data.get(position).getCustomerName());
                view_customers.customerDeleteAlert(data.get(position).getCustomerId(),data.get(position).getCustomerName());
            }
        });


        holder.mobile.setId( position );
        holder.mobile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String call = data.get( position ).getMobile();
                Intent callIntent = new Intent( Intent.ACTION_CALL );
                //  callIntent.setData(Uri.parse("tel:"+number));
                callIntent.setData( Uri.parse( "tel:" + call ) );

                // startActivity(callIntent);


                if (ActivityCompat.checkSelfPermission( context,
                        Manifest.permission.CALL_PHONE ) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                context.startActivity( callIntent );

            }
        } );


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
        TextView mobile,call,address,customerName,noOfveh,update,delete,view;


        LinearLayout row,state1,state2;



        public EMSHolder(View v)
        {
            super(v);

            mobile = v.findViewById(R.id.mobile);
            address=v.findViewById( R.id.address );
            customerName=v.findViewById( R.id.customerName );
            noOfveh=v.findViewById( R.id.noOfveh );
            row = v.findViewById(R.id.row);
            call= v.findViewById(R.id.call);
            state1=v.findViewById( R.id.state1 );
            state2=v.findViewById( R.id.state2 );
            view=v.findViewById( R.id.view );
            delete=v.findViewById( R.id.deleteButton );
            update=v.findViewById( R.id.update );

            //     ar=v.findViewById( R.id.arrow );

        }
    }

    public int getItemCount() {
        return data.size();
    }

    public Object getItem(int i) {
        return i;
    }

    public long getItemId(int i) {
        return i;
    }

    public void setSelectedIndex(int index) {
        selectedIndex = index;
    }

    @Override
    public Filter getFilter()
    {
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
                    ArrayList<customerResponse.data> filteredList = new ArrayList<>();
                    for (customerResponse.data row : dataFilter)
                    {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match

                        if (row.getMobile().toLowerCase().contains(charString.toLowerCase())||
                                row.getAddress().toLowerCase().contains(charString.toLowerCase())||
                                row.getCustomerName().toLowerCase().contains(charString.toLowerCase())||
                                row.getNoOfVeh().toLowerCase().contains(charString.toLowerCase()))/*.contains(charSequence)*/
                        {
                            filteredList.add(row);
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
                data = (ArrayList<customerResponse.data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


}
