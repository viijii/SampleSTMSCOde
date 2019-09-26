package com.stms.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stms.Admin.AddEditEmployee;
import com.stms.Admin.ViewEmployees;
import com.stms.Admin.employeeView;
import com.stms.R;
import com.stms.Responses.UserResponse;

import com.stms.utils.Config;

import java.util.ArrayList;

/**
 * Created by mugdha on 8/19/18.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.EMSHolder> implements Filterable
{
    ArrayList<UserResponse.data> data;
    ArrayList<UserResponse.data> dataFilter;
    Activity con;
    int selectedIndex = -1;
    int FADE_DURATION = 100;
    ViewEmployees viewEmployees;
    Context context;
    static int j;





    public UserAdapter(Activity con, ArrayList<UserResponse.data> data, ViewEmployees viewEmployees, Context context)
    {
        this.context = context;
        this.con = con;
        this.data = data;
        //this.dataFilter = data;
        this.dataFilter = new ArrayList<UserResponse.data>();
        this.dataFilter.addAll(data);
        this.viewEmployees = viewEmployees;
    }

    public EMSHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_row,
                parent, false);

        return new EMSHolder(itemView);
    }

    public void onBindViewHolder(final EMSHolder holder, final int position)
    {
        setScaleAnimation(holder.itemView);
        holder.ll1.setVisibility(View.GONE);
        holder.ll2.setVisibility(View.GONE);

        final String luser= Config.getUser_name(con);

        Log.d("TAG", "onBindViewHolder:luser "+luser);
        System.out.println(luser);

        holder.fullname.setText(data.get(position).getUsername());
        holder.email.setText(data.get(position).getEmail());
        holder.mobile.setText(data.get(position).getMobile());
        holder.address.setText(data.get(position).getCity());




        System.out.println("usernames"+data.get(position).getUsername());
        System.out.println("loginn"+luser);





        if(data.get(position).getUser_type().equalsIgnoreCase("Manager"))
        {
            holder.user_type.setBackgroundColor(con.getResources().getColor(R.color.purple));
        }
        else if (data.get(position).getUser_type().equalsIgnoreCase("Director")){
            holder.user_type.setBackgroundColor(con.getResources().getColor(R.color.colorPrimaryDark));
        }
        else if (data.get(position).getUser_type().equalsIgnoreCase("admin")){
            holder.user_type.setBackgroundColor(con.getResources().getColor(R.color.green));
        }
        else
        {
            holder.user_type.setBackgroundColor(con.getResources().getColor(R.color.colorPrimary));
        }
        holder.user_type.setText(data.get(position).getUser_type());

        holder.delete.setId(position);
        holder.delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                viewEmployees.deleteAlert(data.get(position).getId(),data.get(position).getUser_type());
            }
        });



        holder.call.setId(position);
        holder.call.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String call = data.get(position).getMobile();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + call));

                if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                context.startActivity(callIntent);



            }
        });

        holder.email1.setId(position);
        holder.email1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String email1  = data.get(position).getEmail();
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email1});
                intent.putExtra(Intent.EXTRA_TEXT, "");
                context.startActivity(intent);



            }
        });

        holder.mail2.setId(position);
        holder.mail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email  = data.get(position).getEmail();
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                intent.putExtra(Intent.EXTRA_TEXT, "");
                context.startActivity(intent);


            }
        });

        holder.mobile.setId(position);
        holder.mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String call = data.get(position).getMobile();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + call));

                if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                context.startActivity(callIntent);

            }
        });

        holder.update.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent( context, AddEditEmployee.class );
                i.putExtra( "action","edit" );
                Log.d("TAG", "onClick:empId "+data.get( position ).getId());
                i.putExtra( "eId",data.get( position ).getId());
                context.startActivity( i );

            }
        } );
        holder.view.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent( context, employeeView.class );
                i.putExtra( "action","view" );
                i.putExtra( "eId",data.get( position ).getId());
                context.startActivity( i );

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
        TextView fullname,user_type,email,mobile,delete,call,email1,mail2,address,update,view;

        LinearLayout ll1,ll2;




        public EMSHolder(View v)
        {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    if(j==0)
                    {
                        ll1.setVisibility(View.VISIBLE);
                        ll2.setVisibility( View.VISIBLE );
                        j=1;
                    }

                    else
                    {
                        ll1.setVisibility(View.GONE);
                        ll2.setVisibility( View.GONE );
                        j=0;
                    }
                }
            });


            fullname = v.findViewById(R.id.userName);
            user_type = v.findViewById(R.id.user_type);
            email = v.findViewById(R.id.email);
            mobile = v.findViewById(R.id.mobile);
            delete = v.findViewById(R.id.delete);

            address= v.findViewById(R.id.address);
            email1= v.findViewById(R.id.email1);
            call= v.findViewById(R.id.call);
            update= v.findViewById(R.id.update);
            view= v.findViewById(R.id.view);

            mail2=v.findViewById(R.id.email);
            ll1=v.findViewById( R.id.ll1 );
            ll2=v.findViewById( R.id.ll2 );
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
                    ArrayList<UserResponse.data> filteredList = new ArrayList<>();
                    for (UserResponse.data row : dataFilter)
                    {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match

                        if (row.getMobile().toLowerCase().contains(charString.toLowerCase())||
                                 row.getCity().toLowerCase().contains(charString.toLowerCase())||
                                row.getUser_type().toLowerCase().contains(charString.toLowerCase())||
                                row.getUsername().toLowerCase().contains(charString.toLowerCase())
                                ||  row.getUsername().toLowerCase().contains(charString.toLowerCase())
                                || row.getEmail().toLowerCase().contains(charString.toLowerCase()))/*.contains(charSequence)*/
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
                data = (ArrayList<UserResponse.data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
