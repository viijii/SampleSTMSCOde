package com.stms.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.stms.Admin.AdminApprovedIdeasFragment;
import com.stms.CommonScreens.FieldActivity;
import com.stms.CommonScreens.GettingVedioPath;
import com.stms.Customers.GettingImage;
import com.stms.Customers.ImageViewPurpose;
import com.stms.R;
import com.stms.Responses.AdminRequestedResponse;
import com.stms.Responses.FieldActivityResponse;

import java.util.ArrayList;


public class FieldActivityAdapter extends RecyclerView.Adapter<FieldActivityAdapter.EMSHolder>  {
    ArrayList<FieldActivityResponse.data> data;
   Context context;
    int selectedIndex = -1;
    int FADE_DURATION = 100;
    int i=0;
    FieldActivity fieldActivity;




    public FieldActivityAdapter(Context context, ArrayList<FieldActivityResponse.data> data, FieldActivity fieldActivity) {

        this.context = context;
        this.data=data;
        this.fieldActivity = fieldActivity;
    }


    public EMSHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_field,
                parent, false);

        return new EMSHolder(itemView);
    }


    public void onBindViewHolder(final EMSHolder holder, final int position)
    {

        setScaleAnimation(holder.itemView);

        holder.title.setText( data.get( position ).getTitle());
        holder.location.setText( data.get( position ).getLocation());
      final String imageview=data.get(position).getPath();
        Log.d("TAG", "onBindViewHolder:989899 "+imageview);

        if(imageview.endsWith(".png")) {
            holder.image.setVisibility(View.VISIBLE);
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GettingImage.class);
                    Log.d("TAG", "onBindViewHolder:1234 " + imageview);
                    intent.putExtra("imageview", imageview);
                    context.startActivity(intent);

                }
            });
        }else {
            holder.video.setVisibility(View.VISIBLE);
            holder.video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GettingVedioPath.class);
                    Log.d("TAG", "onBindViewHolder:12345 " + imageview);
                    intent.putExtra("imageview", imageview);
                    context.startActivity(intent);
                }
            });
        }
    }

    private void setScaleAnimation(View view) {

        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }



    public static class EMSHolder extends RecyclerView.ViewHolder
    {
        Button video,image;
      //  ImageView image;
        TextView title,location;

        public EMSHolder(View v)
        {
            super(v);

            title = v.findViewById(R.id.title);
            location=v.findViewById(R.id.location);
            image=v.findViewById(R.id.image);
            video=v.findViewById(R.id.video);

        }
    }
    @Override
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
