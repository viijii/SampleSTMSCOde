package com.stms.Adapters;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.stms.CommonScreens.TaskChat;
import com.stms.Customers.ImageViewPurpose;
import com.stms.Employess.PendingTaskListFragment;
import com.stms.Otp.ValidateOTP;
import com.stms.R;

import com.stms.Responses.MyTaskResponse;
import com.stms.Responses.otpRes;
import com.stms.utils.Config;
import com.stms.webservices.API;
import com.stms.webservices.RestClient;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MyTaskPendingAdapter extends RecyclerView.Adapter<MyTaskPendingAdapter.EMSHolder>implements Filterable{
    ArrayList<MyTaskResponse.data> data;
    ArrayList<MyTaskResponse.data>dataFilter;
    Activity con;
    int selectedIndex = -1,i=0;
    int FADE_DURATION = 100;
    PendingTaskListFragment pendingTaskListFragment;
    Context context;
    otpRes res;
    String valOtp="";
    int place=0;



    public MyTaskPendingAdapter(Activity con,ArrayList <MyTaskResponse.data> data,PendingTaskListFragment pendingTaskListFragment,Context context)
    {
        this.con = con;
        this.context = context;
        this.data = data;
        this.pendingTaskListFragment = pendingTaskListFragment;
        this.dataFilter=new ArrayList<MyTaskResponse.data>();
        this.dataFilter.addAll(data);
    }

    public EMSHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_my_task_pending_adapter,
                parent, false);

        return new EMSHolder(itemView);
    }

    public void onBindViewHolder(final EMSHolder holder, final int position)
    {
        setScaleAnimation(holder.itemView);

        holder.taskId.setText(data.get(position).getTaskId());
        holder.title.setText(data.get(position).getTaskTitle());
        holder.vehicleId.setText(data.get(position).getVehicleId());
        holder.customerId.setText(data.get(position).getCustomerId());
        holder.trackingId.setText(data.get(position).getTrackerId());
        holder.trackingPassword.setText(data.get(position).getTrackerPassword());
        holder.assignDate.setText(data.get(position).getAssignedTime());
        holder.targetTime.setText(data.get(position).getTargetTime());
        holder.status.setText(data.get(position).getStatus());
        holder.timeLeft.setText(data.get(position).getTimeLeft());


            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(place==0) {
                        holder.state.setVisibility(View.VISIBLE);
                        place=1;
                    }else{
                        holder.state.setVisibility(View.GONE);
                        place=0;
                    }
                }
            });


        Log.d("TAG", "onBindViewHolder:serviceStatus "+data.get(position).getServiceStatus());

try {
    if (data.get(position).getServiceStatus().equalsIgnoreCase("completed")) {

        holder.reachedCumstomer.setVisibility(View.GONE);
        holder.completed.setVisibility(View.VISIBLE);

    } else {
        holder.reachedCumstomer.setVisibility(View.VISIBLE);
        holder.completed.setVisibility(View.GONE);
    }
}catch (Exception e){

}



        holder.reachedCumstomer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idea= data.get( position ).getComplaintId();
                String task= data.get( position ).getTaskId();
                getOtp(idea,task);
                Log.d( "TAG", "onClick: taskids"+task );
                Log.d( "TAG", "onClick:userid "+Config.getUserId( context ) );
                Log.d( "TAG", "onClick:ideaid "+data.get( position ).getComplaintId() );
                Intent i=new Intent( context, ValidateOTP.class );
                i.putExtra( "idea",idea );
                i.putExtra( "task",task );
                i.putExtra( "position",holder.getPosition() );
                i.putExtra( "completed","reached" );
                Log.d( "TAG", "onClick: pos"+holder.getPosition() );
                con.startActivity(i );


            }
        } );

        holder.completed.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idea= data.get( position ).getComplaintId();
                String task= data.get( position ).getTaskId();
                getOtp(idea,task);
                Log.d( "TAG", "onClick: taskids"+task );
                Log.d( "TAG", "onClick:userid "+Config.getUserId( context ) );
                Log.d( "TAG", "onClick:ideaid "+data.get( position ).getComplaintId() );
                Intent i=new Intent( context, ValidateOTP.class );
                i.putExtra( "idea",idea );
                i.putExtra( "task",task );
                i.putExtra( "position",holder.getPosition() );
                i.putExtra( "completed","completed" );
                Log.d( "TAG", "onClick: pos"+holder.getPosition() );
                con.startActivity(i );


            }
        } );


        holder.chat.setId(position);
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, TaskChat.class);
                i.putExtra("task_id",data.get(position).getTaskId());
                i.putExtra("assign_to",data.get( position ).getAssignedTo());
                i.putExtra("assign_by",data.get(position).getName());
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(i);
            }
        });

        holder.trackNow.setOnClickListener(new View.OnClickListener() {
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

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(con, ImageViewPurpose.class);
                Log.d("TAG", "onBindViewHolder:1234 "+data.get(position).getImagePath());
                i.putExtra("imagepath",data.get(position).getImagePath());
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

   public void getOtp(String idea,String task) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl( RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory( GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);


            //   Config.saveNumber(getApplication(),mobileNum.getText().toString());
            Log.d("TAG", "onClick: " + idea);
            Call<otpRes> call = api.generateOTP(idea,task,Config.getUserId( con ),Config.getCorp_code( con ));
            call.enqueue(new Callback<otpRes>() {
                @Override
                public void onResponse(Call<otpRes> call,
                                       Response<otpRes> response) {

                    res = new otpRes();
                    res = response.body();

                    Log.d( "TAG", "onResponse: response1"+res.getStatus() );
                    Log.d( "TAG", "onResponse:hdahdgahgayd"+res.getResponse() );
                    if(res.getStatus().equalsIgnoreCase("true")){
                        Toast.makeText( context, "OTP has been sent", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Failed rey", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<otpRes> call, Throwable t) {


                    Log.d(" TAG", "onFailure:fail" + t);
                    //   Log.d(" TAG", "onFailure:fail1" + res.getStatus());
                }


            });


        } catch (Exception e) {
            System.out.print("Exception e" + e);

        }


    }


    public static class EMSHolder extends RecyclerView.ViewHolder
    {
       TextView taskId, title, vehicleId, customerId, trackingId, trackingPassword, assignDate, targetTime, chat,
               reachedCumstomer, trackNow, status, timeLeft,completed;

       Button image;
       LinearLayout state;
       CardView cardView;

        public EMSHolder(View v)
        {
            super(v);

            taskId =  v.findViewById(R.id.taskId);
            title = v.findViewById(R.id.title);
            vehicleId = v.findViewById(R.id.vehicleId);
            customerId = v.findViewById(R.id.customerId);
            trackingId = v.findViewById(R.id.trackingId);
            trackingPassword = v.findViewById(R.id.trackingPassword);
            assignDate = v.findViewById(R.id.assignDate);
            targetTime = v.findViewById(R.id.targetTime);
            chat = v.findViewById(R.id.chat);
            reachedCumstomer = v.findViewById(R.id.reachedCustomer);
            trackNow = v.findViewById(R.id.trackNow);
            status = v.findViewById(R.id.status);
            timeLeft = v.findViewById(R.id.timeLeft);
            completed=v.findViewById(R.id.completed);

            state = v.findViewById(R.id.state);
            cardView = v.findViewById(R.id.cardView);

            image=v.findViewById(R.id.image);

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
                    ArrayList<MyTaskResponse.data> filteredList = new ArrayList<>();
                    for (MyTaskResponse.data row : dataFilter)
                    {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match

                        if (row.getTaskId().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getTaskTitle().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getVehicleId().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getCustomerId().toLowerCase().contains(charString.toLowerCase()) ||
                               /* row.getTrackerId().toLowerCase().contains(charString.toLowerCase())||
                                row.getTrackerPassword().toLowerCase().contains(charString.toLowerCase())||*/
                                row.getAssignedTime().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getTargetTime().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getTaskStatus().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getTimeLeft().toLowerCase().contains(charString.toLowerCase())
                        )
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
                data = (ArrayList<MyTaskResponse.data>) filterResults.values;
                notifyDataSetChanged();
                Config.closeLoader();
            }
        };
    }

}
