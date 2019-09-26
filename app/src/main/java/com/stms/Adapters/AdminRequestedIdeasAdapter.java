package com.stms.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stms.Admin.AdminRequestedIdeasFragment;
import com.stms.Customers.ImageViewPurpose;
import com.stms.R;
import com.stms.Responses.AdminRequestedResponse;
import com.stms.utils.Config;

import java.util.ArrayList;

public class AdminRequestedIdeasAdapter extends RecyclerView.Adapter<AdminRequestedIdeasAdapter.EMSHolder>{
    ArrayList<AdminRequestedResponse.data> data;
    Activity con;
    int selectedIndex = -1;
    int FADE_DURATION = 100;
    int i=0;
    boolean code=false;
    String imageview;
    AdminRequestedIdeasFragment adminRequestedIdeasFragment;


    public AdminRequestedIdeasAdapter(Activity con, ArrayList<AdminRequestedResponse.data> data,
                                      AdminRequestedIdeasFragment adminRequestedIdeasFragment)
    {
        this.con = con;
        this.data = data;
        this.adminRequestedIdeasFragment = adminRequestedIdeasFragment;
    }

    public EMSHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_requested_cardview,
                parent, false);

        return new EMSHolder(itemView);
    }

    public void onBindViewHolder(final EMSHolder holder, final int position)
    {

        setScaleAnimation(holder.itemView);

        holder.complaint_no.setText( data.get( position ).getComplaintId());
        holder.title.setText( data.get( position ).getTitle());
        holder.name.setText( data.get( position ).getName());
        holder.vehicleId.setText( data.get( position ).getVehicleId());
        holder.vehicletype.setText( data.get( position ).getVehicleType());
        holder.description.setText( data.get( position ).getDescription());
        holder.phonenumber.setText( data.get( position ).getMobileNumber());
        imageview=data.get(position).getImagePath();
        Log.d("TAG", "onBindViewHolder:imagepath "+data.get(position).getImagePath());
        try {
            Picasso.with(con).load(data.get(position).getImagePath()).into(holder.image);
        }catch(Exception e){

        }
        Log.d("TAG", "onBindViewHolder:989899 "+imageview);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(con, ImageViewPurpose.class);
                Log.d("TAG", "onBindViewHolder:1234 "+imageview);
                i.putExtra("imagepath",data.get(position).getImagePath());
                con.startActivity(i);



            }
        });



        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.comll.setVisibility(View.VISIBLE);
                holder.imagell.setVisibility(View.VISIBLE);
                holder.view.setVisibility(View.GONE);
            }
        });

        holder.approve.setId(position);
        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminRequestedIdeasFragment.approveIdeaAlert(data.get(position).getComplaintId());
            }
        });

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(code==false)
                {
                    holder.comll.setVisibility(View.VISIBLE);
                    holder.imagell.setVisibility(View.VISIBLE);
                    code=true;
                }

                else
                {
                    holder.comll.setVisibility(View.GONE);
                    holder.imagell.setVisibility(View.GONE);
                    holder.view.setVisibility(View.VISIBLE);
                    code=false;
                }
            }
        });

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
        TextView complaint_no,title,name,vehicleId,vehicletype,description,view,approve,reject,phonenumber;
        ImageView image;
        LinearLayout comll,imagell,row;


        public EMSHolder(View v)
        {
            super(v);

            complaint_no = v.findViewById(R.id.complaint_no);
            title = v.findViewById(R.id.title);
            name = v.findViewById(R.id.name);

            vehicleId = v.findViewById(R.id.vehicleId);
            description = v.findViewById(R.id.description);
            vehicletype = v.findViewById(R.id.vehicletype);
            view = v.findViewById(R.id.view);
            approve = v.findViewById(R.id.approve);
            reject = v.findViewById(R.id.reject);
            phonenumber= v.findViewById(R.id.phonenumber);
            image = v.findViewById(R.id.image);

            comll= v.findViewById(R.id.complaintll);
            imagell= v.findViewById(R.id.imagell);
            row=v.findViewById(R.id.row);




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


}
