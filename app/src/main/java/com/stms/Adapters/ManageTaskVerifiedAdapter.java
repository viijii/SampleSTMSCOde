package com.stms.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stms.Admin.ManageTaskCompletedFragment;
import com.stms.Admin.ManageTaskVerifiedFragment;
import com.stms.CommonScreens.FeedBackActivity;
import com.stms.R;
import com.stms.Responses.ManageTaskPendingResponse;

import java.util.ArrayList;

public class ManageTaskVerifiedAdapter extends RecyclerView.Adapter<ManageTaskVerifiedAdapter.EMSHolder>{

    ArrayList<ManageTaskPendingResponse.data> data;
    ArrayList<ManageTaskPendingResponse.data>dataFilter;
    Activity con;
    int selectedIndex = -1,i=0;
    int FADE_DURATION = 100;
    Context context;
    ManageTaskVerifiedFragment manageTaskVerifiedFragment;




    public ManageTaskVerifiedAdapter(Activity con, ArrayList<ManageTaskPendingResponse.data> data, ManageTaskVerifiedFragment
            manageTaskVerifiedFragment, Context context)
    {
        this.context=context;
        this.con = con;
        this.data=data;
        this.manageTaskVerifiedFragment = manageTaskVerifiedFragment;
        this.dataFilter = new ArrayList<ManageTaskPendingResponse.data>();
        this.dataFilter.addAll(data);

    }
    public ManageTaskVerifiedAdapter.EMSHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.manageverifiedadapter,
                parent, false);
        return new ManageTaskVerifiedAdapter.EMSHolder(itemView);
    }


    @SuppressLint("ResourceAsColor")
    public void onBindViewHolder(final ManageTaskVerifiedAdapter.EMSHolder holder, final int position)

    {
        setScaleAnimation(holder.itemView);

        if(position %2 == 0)
        {
            holder.cardView.setBackgroundColor(Color.parseColor("#f2f2f2"));

        }
        else
        {

        }

        Log.d("TAG", "onBindViewHolder:staus "+data.get(position).getTaskStatus());

        holder.assignby.setText(data.get(position).getAssignedByName());
        holder.title.setText(data.get(position).getTaskTitle());
        holder.targetTime.setText(data.get(position).getTargetTime());

        holder.vehicleType.setText(data.get(position).getVehicleType());
        holder.serviceboyId.setText(data.get(position).getAssignedTo()+"["+data.get(position).getAssignedToName()+"]");
        holder.vehicleId.setText(data.get(position).getVehicleId());
        holder.taskStatus.setText(data.get(position).getTaskStatus()+"% completed");
        holder.taskId.setText(data.get(position).getTaskId());
        holder.customerName.setText(data.get(position).getName());
        holder.assignedDate.setText(data.get(position).getAssignedTime());
        holder.updateTime.setText(data.get(position).getCompletedTime());
        holder.status.setText(data.get(position).getStatus());
        holder.status.setTextColor(Color.GREEN);





        holder.feebbackres.setId(position);
        holder.feebbackres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(con, FeedBackActivity.class);
                i.putExtra("action","mangeTask");
                i.putExtra("taskId",data.get(position).getTaskId());
                con.startActivity(i);

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
        TextView title,serviceboyId,customerName,customerCall,vehicleId,vehicleType,targetTime,
                assignedDate,taskStatus,updatedon,chat,feedback,assignby,taskId,status,updateTime,feebbackres;
        LinearLayout state;
        CardView cardView;

        public EMSHolder(View v)
        {
            super(v);

            cardView = v.findViewById(R.id.cardView);
            title=v.findViewById(R.id.title);
            serviceboyId=v.findViewById(R.id.serviceboyId);
            customerName=v.findViewById(R.id.customerName);
            customerCall=v.findViewById(R.id.customerCall);
            vehicleId=v.findViewById(R.id.vehicleId);
            vehicleType=v.findViewById(R.id.vehicleType);
            targetTime=v.findViewById(R.id.targetTime);
            taskStatus=v.findViewById(R.id.taskStatus);
            updateTime=v.findViewById(R.id.completed_on);
            chat=v.findViewById(R.id.chat);
            feedback=v.findViewById(R.id.feebbackres);
            assignby=v.findViewById(R.id.assignby);
            taskId=v.findViewById(R.id.taskId);
            assignedDate=v.findViewById(R.id.assignDate);

            status=v.findViewById(R.id.status);
            feebbackres=v.findViewById(R.id.feebbackres);

        }
    }
    public int getItemCount() {
        return data.size();
    }

    public long getItemId(int i) {
        return i;
    }


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
                    ArrayList<ManageTaskPendingResponse.data> filteredList = new ArrayList<>();
                    for (ManageTaskPendingResponse.data row : dataFilter) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        try {
                            if ("#Task-".concat(row.getTaskId()).toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getTaskTitle().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getName().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getVehicleId().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getVehicleType().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getTaskStatus().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getAssignedTo().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getAssignedTime().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getTargetTime().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getTimeLeft().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getCompletedTime().toLowerCase().contains(charString.toLowerCase()))/*.contains(charSequence)*/ {
                                filteredList.add(row);
                            }
                        } catch (Exception e) {}
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

                data = (ArrayList<ManageTaskPendingResponse.data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
